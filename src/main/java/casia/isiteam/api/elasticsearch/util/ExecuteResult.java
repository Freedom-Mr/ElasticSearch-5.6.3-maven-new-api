package casia.isiteam.api.elasticsearch.util;

import casia.isiteam.api.elasticsearch.common.enums.AggsLevel;
import casia.isiteam.api.elasticsearch.common.staitcParms.ShareParms;
import casia.isiteam.api.elasticsearch.common.vo.result.AggsInfo;
import casia.isiteam.api.elasticsearch.common.vo.result.LonLatInfo;
import casia.isiteam.api.elasticsearch.common.vo.result.QueryInfo;
import casia.isiteam.api.elasticsearch.common.vo.result.SearchResult;
import casia.isiteam.api.elasticsearch.common.vo.security.RoleInfomation;
import casia.isiteam.api.elasticsearch.common.vo.security.UserInfomation;
import casia.isiteam.api.toolutil.Validator;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.regexp.internal.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * ClassName: ExecuteResult
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/12
 * Email: zhiyou_wang@foxmail.com
 */
public class ExecuteResult extends ShareParms {
    private static Logger logger = LoggerFactory.getLogger( JSONCompare.class);

    public static SearchResult executeQueryResult(JSONObject results){
        SearchResult searchResult = new SearchResult();
        try {
            searchResult.setScrollId(results.containsKey(_SCROLL_ID) ? results.getString(_SCROLL_ID) : null);

            JSONObject hitsJsons = results.getJSONObject(HITS);
            JSONObject profileJsons =results.containsKey(PROFILE) ? results.getJSONObject(PROFILE) : null;
            searchResult.setTotal_Doc(hitsJsons.getLong(TOTAL)).setProfile(profileJsons);


            if( hitsJsons.containsKey(HITS) ){
                JSONArray infoJsonArrays = hitsJsons.getJSONArray(HITS);
                parseInfoResult(searchResult.getQueryInfos(),infoJsonArrays);
            }

            if( results.containsKey(AGGREGATIONS) ){
                JSONObject aggregationsJsons = results.getJSONObject(AGGREGATIONS);
                parseAggesResult(searchResult.getAggsInfos(),aggregationsJsons);
            }
        }catch (Exception e){
            searchResult.setStatus(false);
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
            return searchResult;
        }
        return searchResult;
    }
    public static SearchResult executeQueryTotal(JSONObject results){
        SearchResult searchResult = new SearchResult();
        try {
            searchResult.setTotal_Doc(results.getLong(COUNT));
        }catch (Exception e){
            logger.error(e.getMessage());
            return searchResult;
        }
        return searchResult;
    }

    private static void parseAggesResult(List<AggsInfo> aggsInfos, JSONObject json){
        List<String> OperationLevels = Arrays.asList(AggsLevel.Group.getLevel(),AggsLevel.Sum.getLevel(),AggsLevel.Avg.getLevel(),AggsLevel.Min.getLevel(),AggsLevel.Max.getLevel());
        List<String> levelList = Arrays.asList(AggsLevel.values()).stream().map(s->s.getLevel()).collect(Collectors.toList());
        List<AggsLevel> aggsList = Arrays.asList(AggsLevel.values());

        Set<String> keys = json.keySet();
        for(String key:keys){
            Optional<AggsLevel> aggsLevel = aggsList.stream().filter(s->key.startsWith(QUESTION+ s.getLevel()+QUESTION)).findFirst();
            if( aggsLevel.isPresent()){

                //Term
                 if( key.startsWith(QUESTION+ AggsLevel.Term.getLevel()+QUESTION ) ){
                    JSONObject os = json.getJSONObject(key);
                    AggsInfo info = new AggsInfo(AggsLevel.Term.getName(),key.replaceAll(AGGS_FIELD,NONE),-1,-1);
                    if( os.containsKey(BUCKETS) ){
                        for(Object s : os.getJSONArray(BUCKETS)){
                            JSONObject object = JSONObject.parseObject(s.toString());
                            AggsInfo infoc =  new AggsInfo(AggsLevel.Term.getName(),object.getString(KEY),object.getLong(DOC_COUNT),-1);
                            object.keySet().stream().filter(c->levelList.stream().filter(t->c.startsWith(QUESTION+ t+QUESTION)).findFirst().isPresent() ).forEach(c->{
                                parseAggesResult( infoc.getChildren(), j(c,object.get(c)));
                            });
                            info.getChildren().add(infoc);
                        }
                    }
                    aggsInfos.add(info);
                }
                //Operation
                else if( OperationLevels.stream().filter(s->key.startsWith(QUESTION+ s+QUESTION)).findFirst().isPresent()  ){
                    JSONObject os = json.getJSONObject(key);
                    AggsInfo info = new AggsInfo( aggsList.stream().filter(s->key.startsWith(QUESTION+  s.getLevel()+QUESTION)).findFirst().get().getName()
                            ,key.replaceAll(AGGS_FIELD,NONE),-1,Validator.check(os.get(VALUE))?os.getFloat(VALUE):-1);
                    os.keySet().stream().filter(c->levelList.stream().filter(t->c.startsWith(QUESTION+ t+QUESTION)).findFirst().isPresent() ).forEach(c->{
                        parseAggesResult( info.getChildren(), j(c,os.get(c)));
                    });
                    aggsInfos.add(info);
                }
                // date
                else if( key.startsWith(QUESTION+ AggsLevel.Date.getLevel()+QUESTION) ){
                     JSONObject os = json.getJSONObject(key);
                     AggsInfo info = new AggsInfo( AggsLevel.Date.getName(),key.replaceAll(AGGS_FIELD,NONE));
                     if( os.containsKey(BUCKETS) ){
                         for(Object s : os.getJSONArray(BUCKETS)){
                             JSONObject object = JSONObject.parseObject(s.toString());
                             AggsInfo infoc =  new AggsInfo(AggsLevel.Date.getName(),object.getString(KEY_AS_STRING),object.getLong(DOC_COUNT),-1);
                             object.keySet().stream().filter(c->levelList.stream().filter(t->c.startsWith(QUESTION+ t+QUESTION)).findFirst().isPresent() ).forEach(c->{
                                 parseAggesResult( infoc.getChildren(), j(c,object.get(c)));
                             });
                             info.getChildren().add(infoc);
                         }
                     }
                     aggsInfos.add(info);
                }
                //geo Bounds
                else if( key.startsWith(QUESTION+AggsLevel.Bounds.getLevel()+QUESTION) ){
                     JSONObject os = json.getJSONObject(key);
                     AggsInfo info = new AggsInfo( AggsLevel.Bounds.getName(),key.replaceAll(AGGS_FIELD,NONE));
                     if( os.containsKey(BOUNDS) ){
                         info.setLonLatInfos(
                                 new LonLatInfo(TOP_LEFT,AggsLevel.Bounds.getName(),os.getJSONObject(BOUNDS).getJSONObject(TOP_LEFT).getFloat(LON),
                                         os.getJSONObject(BOUNDS).getJSONObject(TOP_LEFT).getFloat(LAT)),
                                 new LonLatInfo(BOTTOM_RIGHT,AggsLevel.Bounds.getName(),os.getJSONObject(BOUNDS).getJSONObject(BOTTOM_RIGHT).getFloat(LON),
                                         os.getJSONObject(BOUNDS).getJSONObject(BOTTOM_RIGHT).getFloat(LAT))
                         );
                     }
                     aggsInfos.add(info);
                }
                //geo  Centroid
                else if( key.startsWith(QUESTION+AggsLevel.Centroid.getLevel()+QUESTION) ){
                     JSONObject os = json.getJSONObject(key);
                     AggsInfo info = new AggsInfo( AggsLevel.Centroid.getName(),key.replaceAll(AGGS_FIELD,NONE));
                     if( os.containsKey(LOCATION) ){
                         info.setLonLatInfos(
                                 new LonLatInfo(LOCATION,AggsLevel.Centroid.getName(),os.getJSONObject(LOCATION).getFloat(LON),
                                         os.getJSONObject(LOCATION).getFloat(LAT))).setType(AggsLevel.Centroid.getName());
                     }
                     aggsInfos.add(info);
                }

                //TOP
                else if( key.startsWith(QUESTION+ AggsLevel.Top.getLevel()+QUESTION ) ){
                    JSONObject os = json.getJSONObject(key);
                    AggsInfo info = new AggsInfo(AggsLevel.Top.getName(),key.replaceAll(AGGS_FIELD,NONE),-1,-1);
                    if( os.containsKey(HITS)){
                        parseAggesResult( os.getJSONObject(HITS) , info.getQueryInfos());
                    }
                    aggsInfos.add(info);
                }
                //Price
                else if(key.startsWith(QUESTION+ AggsLevel.Price.getLevel()+QUESTION)){
                    JSONObject os = json.getJSONObject(key);
                    AggsInfo info = new AggsInfo(AggsLevel.Price.getName(),key.replaceAll(AGGS_FIELD,NONE),-1,-1);
                    if( os.containsKey(BUCKETS) ){
                        os.getJSONObject(BUCKETS).forEach((n,m)->{
                            JSONObject newchildern = JSONObject.parseObject(String.valueOf(m));
                            AggsInfo infoc =  new AggsInfo(AggsLevel.Price.getName(),
                                    (newchildern.containsKey(FROM)?newchildern.getString(FROM):STAR )+CROSS+ (newchildern.containsKey(TO)?newchildern.getString(TO):STAR)
                                    ,newchildern.getLong(DOC_COUNT),-1);
                            newchildern.keySet().stream().filter(c->levelList.stream().filter(t->c.startsWith(QUESTION+ t+QUESTION)).findFirst().isPresent() ).forEach(c->{
                                parseAggesResult( infoc.getChildren(), j(c,newchildern.get(c)));
                            });
                            info.getChildren().add(infoc);
                        });
                    }
                    aggsInfos.add(info);
                }
                 //IPRange
                 else if(key.startsWith(QUESTION+ AggsLevel.IPRange.getLevel()+QUESTION)){
                     JSONObject os = json.getJSONObject(key);
                     AggsInfo info = new AggsInfo(AggsLevel.IPRange.getName(),key.replaceAll(AGGS_FIELD,NONE),-1,-1);
                     if( os.containsKey(BUCKETS) ){
                         os.getJSONObject(BUCKETS).forEach((n,m)->{
                             JSONObject newchildern = JSONObject.parseObject(String.valueOf(m));
                             AggsInfo infoc =  new AggsInfo(AggsLevel.IPRange.getName(),
                                     (newchildern.containsKey(FROM)?newchildern.getString(FROM):STAR )+CROSS+ (newchildern.containsKey(TO)?newchildern.getString(TO):STAR)
                                     ,newchildern.getLong(DOC_COUNT),-1);
                             newchildern.keySet().stream().filter(c->levelList.stream().filter(t->c.startsWith(QUESTION+ t+QUESTION)).findFirst().isPresent() ).forEach(c->{
                                 parseAggesResult( infoc.getChildren(), j(c,newchildern.get(c)));
                             });
                             info.getChildren().add(infoc);
                         });
                     }
                     aggsInfos.add(info);
                 }
                 //Grid
                 else if(key.startsWith(QUESTION+ AggsLevel.Grid.getLevel()+QUESTION)){
                     JSONObject os = json.getJSONObject(key);
                     AggsInfo info = new AggsInfo(AggsLevel.Grid.getName(),key.replaceAll(AGGS_FIELD,NONE),-1,-1);
                     if( os.containsKey(BUCKETS) ){
                         for(Object s : os.getJSONArray(BUCKETS)){
                             JSONObject object = JSONObject.parseObject(s.toString());
                             AggsInfo infoc =  new AggsInfo(AggsLevel.Grid.getName(),object.getString(KEY),object.getLong(DOC_COUNT),-1);
                             object.keySet().stream().filter(c->levelList.stream().filter(t->c.startsWith(QUESTION+ t+QUESTION)).findFirst().isPresent() ).forEach(c->{
                                 parseAggesResult( infoc.getChildren(), j(c,object.get(c)));
                             });
                             info.getChildren().add(infoc);
                         }
                     }
                     aggsInfos.add(info);
                 }
                 //KeyWord
                 else if(key.startsWith(QUESTION+ AggsLevel.KeyWord.getLevel()+QUESTION)){
                     JSONObject os = json.getJSONObject(key);
                     AggsInfo info = new AggsInfo(AggsLevel.KeyWord.getName(),key.replaceAll(AGGS_FIELD,NONE),-1,-1);
                     if( os.containsKey(BUCKETS) ){
                         os.getJSONObject(BUCKETS).forEach((n,m)->{
                             JSONObject newchildern = JSONObject.parseObject(String.valueOf(m));
                             AggsInfo infoc =  new AggsInfo(AggsLevel.KeyWord.getName(), n ,newchildern.getLong(DOC_COUNT),-1);
                             newchildern.keySet().stream().filter(c->levelList.stream().filter(t->c.startsWith(QUESTION+ t+QUESTION)).findFirst().isPresent() ).forEach(c->{
                                 parseAggesResult( infoc.getChildren(), j(c,newchildern.get(c)));
                             });
                             info.getChildren().add(infoc);
                         });
                     }
                     aggsInfos.add(info);
                 }
                 //Matrix
                 else if(key.startsWith(QUESTION+ AggsLevel.Matrix.getLevel()+QUESTION)){
                     JSONObject os = json.getJSONObject(key);
                     AggsInfo info = new AggsInfo(AggsLevel.Matrix.getName(),key.replaceAll(AGGS_FIELD,NONE),os.getLong(DOC_COUNT),-1);
                     if( os.containsKey(FIELDS) ){
                         os.getJSONArray(FIELDS).forEach(s->{
                             JSONObject newchildern = JSONObject.parseObject(String.valueOf(s));
                             AggsInfo infoc =  new AggsInfo(AggsLevel.Matrix.getName(), newchildern.getString(NAME) ,newchildern.getLong(COUNT),-1);
                             newchildern.keySet().forEach(c->{
                                 AggsInfo infod = new AggsInfo();
                                 infod.setField(c).setType(AggsLevel.Matrix.getName());
                                 if( c.equals(COVARIANCE) || c.equals(CORRELATION) ){
                                     newchildern.getJSONObject(infod.getField()).forEach((k,v)->{
                                        AggsInfo infoe = new AggsInfo(AggsLevel.Matrix.getName(),k,-1,Float.parseFloat(v.toString()));
                                        infod.getChildren().add(infoe);
                                     });
                                     infoc.getChildren().add(infod);
                                 }else if( !c.equals(NAME)){
                                     infod.setTotal_Operation(newchildern.getFloat(c));
                                     infoc.getChildren().add(infod);
                                 }
                             });
                             info.getChildren().add(infoc);
                         });
                     }
                     aggsInfos.add(info);
                 }
            }
        }
    }
    private static void parseAggesResult( JSONObject hitsJsons,List<QueryInfo> queryInfos){
        hitsJsons.getJSONArray(HITS).stream().forEach(s->{
            JSONObject jsono = JSONObject.parseObject(s.toString());
            JSONObject sourceFieldDatas = jsono.getJSONObject(_SOURCE);
            QueryInfo dataInfo = new QueryInfo();
            dataInfo.
                    setId(jsono.getString(_ID)).
                    setIndexName(jsono.getString(_INDEX)).
                    setIndexType(jsono.getString(_TYPE)).
                    setScore(jsono.getString(_SCORE)).
                    setField( sourceFieldDatas).
                    setTotal_Operation(1);
            queryInfos.add(dataInfo);
        });
    }
    private static JSONObject j(String key,Object value){
        JSONObject jsonObject = new JSONObject();
        if( Validator.check(key) ){
            jsonObject.put(key,value);
        }
        return jsonObject;
    }
/******************************************************************/


    public static SearchResult executeSqlResult(JSONObject results){
        SearchResult searchResult = new SearchResult();
        try {
            searchResult.setScrollId(results.containsKey(_SCROLL_ID) ? results.getString(_SCROLL_ID) : null);
            JSONObject profileJsons =results.containsKey(PROFILE) ? results.getJSONObject(PROFILE) : null;
            JSONObject hitsJsons = results.getJSONObject(HITS);
            searchResult.setTotal_Doc(hitsJsons.getLong(TOTAL)).setProfile(profileJsons);

            if( hitsJsons.containsKey(HITS) ){
                JSONArray infoJsonArrays = hitsJsons.getJSONArray(HITS);
                parseInfoResult(searchResult.getQueryInfos(),infoJsonArrays);
            }

            if( results.containsKey(AGGREGATIONS) ){
                JSONObject aggregationsJsons = results.getJSONObject(AGGREGATIONS);
                parseSqlAggesResult(searchResult.getAggsInfos(),aggregationsJsons);
            }
        }catch (Exception e){
            searchResult.setStatus(false);
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
            return searchResult;
        }
        return searchResult;
    }
    private static void parseInfoResult(List<QueryInfo> queryInfos, JSONArray jsonArray){
        if( !Validator.check(jsonArray) ){
            return;
        }
        jsonArray.stream().forEach(s->{
            JSONObject json = JSONObject.parseObject(s.toString());
            JSONObject sourceFieldDatas = json.getJSONObject(_SOURCE);
            if( json.containsKey(HIGHLIGHT) ){
                JSONObject highlightFieldDatas = json.getJSONObject(HIGHLIGHT);
                sourceFieldDatas.putAll(highlightFieldDatas);
            }
            QueryInfo dataInfo = new QueryInfo();
            dataInfo.
                    setId(json.getString(_ID)).
                    setIndexName(json.getString(_INDEX)).
                    setIndexType(json.getString(_TYPE)).
                    setScore(json.getString(_SCORE)).
                    setField( sourceFieldDatas).
                    setTotal_Operation(1);
            queryInfos.add(dataInfo);
        });
    }

    private static void parseSqlAggesResult(List<AggsInfo> aggsInfos, JSONObject json){

        Set<String> keys = json.keySet();
        AggsInfo aggsInfo = new AggsInfo();
        for(String key:keys){
            String v = json.getString(key);

            if( key.equals(KEY) ){
                if(!json.containsKey(KEY_AS_STRING)){
                    aggsInfo.setField(v);
                }
                continue;
            }
            if( key.equals(KEY_AS_STRING) ){
                aggsInfo.setField(v);
                continue;
            }
            if( key.equals(DOC_COUNT) ){
                aggsInfo.setTotal_Doc(json.getLong(key));
                continue;
            }

            if( key.equals(VALUE) ){
                aggsInfo.setTotal_Operation(json.getFloat(key));
                continue;
            }

            JSONObject jsonObject = JSONObject.parseObject(v);

            if( json.containsKey(KEY) && jsonObject.containsKey(VALUE)  ){
                aggsInfo.getChildren().add(
                        new AggsInfo().setField(key).setTotal_Operation(jsonObject.getFloat(VALUE))
                );
                continue;
            }

            aggsInfo.setField( Validator.check(aggsInfo.getField()) ? aggsInfo.getField() : key );

            aggsInfo.setTotal_Doc(aggsInfo.getTotal_Doc()!=-1?aggsInfo.getTotal_Doc(): (jsonObject.containsKey(DOC_COUNT) ? jsonObject.getLong(DOC_COUNT) : -1) ).
                    setTotal_Operation(aggsInfo.getTotal_Operation()!=-1?aggsInfo.getTotal_Operation(): (jsonObject.containsKey(VALUE) ? jsonObject.getLong(VALUE) : -1) );

            if( jsonObject.containsKey(BUCKETS) ){
                for(Object s : jsonObject.getJSONArray(BUCKETS)){
                    parseSqlAggesResult( aggsInfo.getChildren(),JSONObject.parseObject(s.toString()));
                }
            }
            if( jsonObject.containsKey(HITS) ){
                parseAggesResult( jsonObject.getJSONObject(HITS) , aggsInfo.getQueryInfos());
            }
        }

        if( Validator.check(aggsInfo.getField()) && !aggsInfos.contains(aggsInfo) ){
            aggsInfos.add(aggsInfo);
        }
    }

    public static List<UserInfomation> parseUserResult(JSONObject json){
        List<UserInfomation> rs = new ArrayList<>();
        if( json.containsKey(USERNAME) ){
            rs.add(coverUser(json));
        }else{
            json.forEach((k,v)->{rs.add(coverUser(JSONObject.parseObject(v.toString())));});
        }
        return rs;
    }
    private static UserInfomation coverUser(JSONObject json){
        UserInfomation userInfomation = new UserInfomation();
        userInfomation.setUsername(json.getString(USERNAME));
        userInfomation.setEmail(json.getString(EMAIL));
        userInfomation.setEnabled(json.getBoolean(ENABLED));
        userInfomation.setFull_name(json.getString(FULL_NAME));
        userInfomation.setMetadata(json.getJSONObject(METADATA));
        userInfomation.setRoles(JSONObject.parseArray(json.getJSONArray(ROLES).toJSONString(), String.class));
        return userInfomation;
    }

    public static List<RoleInfomation> parserRoleResult(JSONObject jsonObject){
        List<RoleInfomation> rs = new ArrayList<>();
        jsonObject.forEach((k,v)->{
            JSONObject json = jsonObject.getJSONObject(k);
            RoleInfomation roleInfomation = new RoleInfomation();
            roleInfomation.setRoleName(k);
            roleInfomation.setCluster(json.getJSONArray(CLUSTER).toJavaList(String.class));
            roleInfomation.setIndices(json.getJSONArray(INDICES).toJavaList(JSONObject.class));
            roleInfomation.setRun_as(json.getJSONArray(RUN_AS).toJavaList(String.class));
            roleInfomation.setMetadata(json.getJSONObject(METADATA));
            roleInfomation.setTransientMetadata(json.getJSONObject(TRANSIENT_METADATA));
            rs.add(roleInfomation);
        });
        return rs;
    }

}
