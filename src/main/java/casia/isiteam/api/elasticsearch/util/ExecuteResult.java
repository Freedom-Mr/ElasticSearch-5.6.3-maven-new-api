package casia.isiteam.api.elasticsearch.util;

import casia.isiteam.api.elasticsearch.common.enums.AggsLevel;
import casia.isiteam.api.elasticsearch.common.staitcParms.ShareParms;
import casia.isiteam.api.elasticsearch.common.vo.result.AggsInfo;
import casia.isiteam.api.elasticsearch.common.vo.result.LonLatInfo;
import casia.isiteam.api.elasticsearch.common.vo.result.QueryInfo;
import casia.isiteam.api.elasticsearch.common.vo.result.SearchResult;
import casia.isiteam.api.toolutil.Validator;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

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

        Set<String> keys = json.keySet();
        AggsInfo aggsInfo = new AggsInfo();
        for(String key:keys){
<<<<<<< Updated upstream
            String v = json.getString(key);
            if( OperationLevels.stream().filter(s->key.startsWith(QUESTION+ s+QUESTION)).findFirst().isPresent() ){
                if( json.containsKey(KEY) ){
                    aggsInfo.getChildren().add(
                            new AggsInfo(Arrays.asList(AggsLevel.values()).stream().filter(s->key.startsWith(QUESTION+  s.getLevel()+QUESTION)).findFirst().get().getName()
                                    ,key.replaceAll(AGGS_FIELD,NONE),-1,json.getJSONObject(key).getFloat(VALUE))
                    );
                }else {
                    AggsInfo cc = new AggsInfo();
                    cc.setField(key.replaceAll(AGGS_FIELD,NONE)).setTotal_Operation(json.getJSONObject(key).getFloat(VALUE)).setType(
                            Arrays.asList(AggsLevel.values()).stream().filter(s->key.startsWith(QUESTION+  s.getLevel()+QUESTION)).findFirst().get().getName()
                    );
                    if( !aggsInfos.contains(cc) ){
                        aggsInfos.add(cc);
=======
            Optional<AggsLevel> aggsLevel = aggsList.stream().filter(s->key.startsWith(QUESTION+ s.getLevel()+QUESTION)).findFirst();
            if( aggsLevel.isPresent()){

                //Term
                 if( key.startsWith(QUESTION+ AggsLevel.Term.getLevel()+QUESTION ) ){
                    JSONObject os = json.getJSONObject(key);
                    AggsInfo info = new AggsInfo(AggsLevel.Term.getName(),key.replaceAll(AGGS_FIELD,NONE),-1,-1);
                    if( os.containsKey(BUCKETS) ){
                        for(Object s : os.getJSONArray(BUCKETS)){
                            JSONObject object = JSONObject.parseObject(s.toString());
                            AggsInfo infoc =  new AggsInfo(AggsLevel.Term.getName(),object.getString(KEY),object.containsKey(KEY_AS_STRING)?object.getString(KEY_AS_STRING):null,object.getLong(DOC_COUNT),-1);
                            object.keySet().stream().filter(c->levelList.stream().filter(t->c.startsWith(QUESTION+ t+QUESTION)).findFirst().isPresent() ).forEach(c->{
                                parseAggesResult( infoc.getChildren(), j(c,object.get(c)));
                            });
                            info.getChildren().add(infoc);
                        }
>>>>>>> Stashed changes
                    }
                }
                continue;
            }

            if( key.startsWith(QUESTION+AggsLevel.Bounds.getLevel()+QUESTION) ){
                JSONObject jsonObject = JSONObject.parseObject(v);
                if( json.containsKey(KEY) ){
                    aggsInfo.getLonLatInfos().add(
                            new LonLatInfo(TOP_LEFT,AggsLevel.Bounds.getName(),jsonObject.getJSONObject(BOUNDS).getJSONObject(TOP_LEFT).getFloat(LON),
                                    jsonObject.getJSONObject(BOUNDS).getJSONObject(TOP_LEFT).getFloat(LAT))
                    );
                    aggsInfo.getLonLatInfos().add(new LonLatInfo(BOTTOM_RIGHT,AggsLevel.Bounds.getName(),jsonObject.getJSONObject(BOUNDS).getJSONObject(BOTTOM_RIGHT).getFloat(LON),
                            jsonObject.getJSONObject(BOUNDS).getJSONObject(BOTTOM_RIGHT).getFloat(LAT))
                    );
                }else {
                    AggsInfo cc = new AggsInfo();
                    cc.setField(key.replaceAll(AGGS_FIELD,NONE)).setLonLatInfos(
                            new LonLatInfo(TOP_LEFT,AggsLevel.Bounds.getName(),jsonObject.getJSONObject(BOUNDS).getJSONObject(TOP_LEFT).getFloat(LON),
                            jsonObject.getJSONObject(BOUNDS).getJSONObject(TOP_LEFT).getFloat(LAT)),
                            new LonLatInfo(BOTTOM_RIGHT,AggsLevel.Bounds.getName(),jsonObject.getJSONObject(BOUNDS).getJSONObject(BOTTOM_RIGHT).getFloat(LON),
                                    jsonObject.getJSONObject(BOUNDS).getJSONObject(BOTTOM_RIGHT).getFloat(LAT))
                    ).setType(BOUNDS);
                    if( !aggsInfos.contains(cc) ){
                        aggsInfos.add(cc);
                    }
                }
                continue;
            }
            if( key.startsWith(QUESTION+AggsLevel.Centroid.getLevel()+QUESTION) ){
                JSONObject jsonObject = JSONObject.parseObject(v);
                if( json.containsKey(KEY) ){
                    aggsInfo.getLonLatInfos().add(
                            new LonLatInfo(LOCATION,AggsLevel.Centroid.getName(),jsonObject.getJSONObject(LOCATION).getFloat(LON),
                                    jsonObject.getJSONObject(LOCATION).getFloat(LAT))
                    );
                }else {
                    AggsInfo cc = new AggsInfo();
                    cc.setField(key.replaceAll(AGGS_FIELD,NONE)).setType(AggsLevel.Centroid.getName()).setTotal_Doc(jsonObject.getLong(COUNT)).setLonLatInfos(
                            new LonLatInfo(LOCATION,AggsLevel.Centroid.getName(),jsonObject.getJSONObject(LOCATION).getFloat(LON),
                            jsonObject.getJSONObject(LOCATION).getFloat(LAT))).setType(AggsLevel.Centroid.getName());
                    if( !aggsInfos.contains(cc) ){
                        aggsInfos.add(cc);
                    }
                }
                continue;
            }


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
            JSONObject jsonObject = JSONObject.parseObject(v);
            aggsInfo.setField(
                    Validator.check(aggsInfo.getField()) ? aggsInfo.getField() : key.replaceAll(AGGS_FIELD,NONE) ).
                    setTotal_Doc(aggsInfo.getTotal_Doc()!=-1?aggsInfo.getTotal_Doc(): (jsonObject.containsKey(DOC_COUNT) ? jsonObject.getLong(DOC_COUNT) : -1) ).
                    setTotal_Operation(aggsInfo.getTotal_Operation()!=-1?aggsInfo.getTotal_Operation(): (jsonObject.containsKey(VALUE) ? jsonObject.getLong(VALUE) : -1) ).
                    setType( json.containsKey(KEY) ? null :
                            Arrays.asList(AggsLevel.values()).stream().filter(s->key.startsWith(QUESTION+ s.getLevel()+QUESTION)).findFirst().get().getName());

            if(key.startsWith(QUESTION+ AggsLevel.Term.getLevel()+QUESTION)){
                if( jsonObject.containsKey(BUCKETS) ){
                    for(Object s : jsonObject.getJSONArray(BUCKETS)){
                        parseAggesResult(aggsInfo.getChildren(),JSONObject.parseObject(s.toString()));
                    }
                }
            }

            if(key.startsWith(QUESTION+ AggsLevel.Date.getLevel()+QUESTION)){
                if( jsonObject.containsKey(BUCKETS) ){
                    for(Object s : jsonObject.getJSONArray(BUCKETS)){
                        parseAggesResult(aggsInfo.getChildren(),JSONObject.parseObject(s.toString()));
                    }
                }
            }
            if(key.startsWith(QUESTION+ AggsLevel.Top.getLevel()+QUESTION)){
                if( jsonObject.containsKey(HITS) ){
                    parseAggesResult( jsonObject.getJSONObject(HITS) , aggsInfo.getQueryInfos());
                }
            }
            if(key.startsWith(QUESTION+ AggsLevel.Price.getLevel()+QUESTION)){
                if( jsonObject.containsKey(BUCKETS) ){
                    jsonObject.getJSONObject(BUCKETS).forEach((n,m)->{
                        JSONObject newchildern = JSONObject.parseObject(String.valueOf(m));
                        newchildern.put(KEY,n);
                        newchildern.remove(TO);
                        newchildern.remove(FROM);
                        parseAggesResult(aggsInfo.getChildren(),newchildern);
                    });
                }
            }
            if(key.startsWith(QUESTION+ AggsLevel.IPRange.getLevel()+QUESTION)){
                if( jsonObject.containsKey(BUCKETS) ){
                    jsonObject.getJSONObject(BUCKETS).forEach((n,m)->{
                        JSONObject newchildern = JSONObject.parseObject(String.valueOf(m));
                        newchildern.put(KEY,n);
                        newchildern.remove(TO);
                        newchildern.remove(FROM);
                        parseAggesResult(aggsInfo.getChildren(),newchildern);
                    });
                }
            }

            if(key.startsWith(QUESTION+ AggsLevel.Grid.getLevel()+QUESTION)){
                if( jsonObject.containsKey(BUCKETS) ){
                    for(Object s : jsonObject.getJSONArray(BUCKETS)){
                        parseAggesResult(aggsInfo.getChildren(),JSONObject.parseObject(s.toString()));
                    }
                }
            }

            Optional<AggsLevel> aggsLevel = Arrays.asList(AggsLevel.values()).stream().filter(s->key.startsWith(QUESTION+ s.getLevel()+QUESTION)).findFirst();
            if( aggsLevel.isPresent()){
                aggsInfo.getChildren().stream().filter(s->!Validator.check(s.getType())).forEach(s->{
                    s.setType(aggsLevel.get().getName());
                });
            }
        }
        if( Validator.check(aggsInfo.getField()) && !aggsInfos.contains(aggsInfo) ){
            aggsInfos.add(aggsInfo);
        }
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
}
