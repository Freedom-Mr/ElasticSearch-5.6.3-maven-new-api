package casia.isiteam.api.elasticsearch.operation.security;

import casia.isiteam.api.elasticsearch.common.enums.GeoQueryLevel;
import casia.isiteam.api.elasticsearch.common.enums.QueriesLevel;
import casia.isiteam.api.elasticsearch.common.staitcParms.HttpHeader;
import casia.isiteam.api.elasticsearch.common.staitcParms.ShareParms;
import casia.isiteam.api.elasticsearch.common.status.IndexParmsStatus;
import casia.isiteam.api.elasticsearch.common.status.IndexSearchBuilder;
import casia.isiteam.api.elasticsearch.common.vo._Entity_Query;
import casia.isiteam.api.elasticsearch.common.vo._JSON_Array;
import casia.isiteam.api.elasticsearch.common.vo.field.search.KeywordsCombine;
import casia.isiteam.api.elasticsearch.common.vo.result.LonLatInfo;
import casia.isiteam.api.elasticsearch.dbSource.EsDbUtil;
import casia.isiteam.api.elasticsearch.util.JSONCompare;
import casia.isiteam.api.elasticsearch.util.NumberUtil;
import casia.isiteam.api.elasticsearch.util.StringAppend;
import casia.isiteam.api.toolutil.Validator;
import casia.isiteam.api.toolutil.random.CasiaRandomUtil;
import casia.isiteam.api.toolutil.secretKey.base.CasiaBaseUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ClassName: EncapsulationInfo
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/4/29
 * Email: zhiyou_wang@foxmail.com
 */
public class EncapsulationInfo extends EsDbUtil {
    protected String url(String driverName){
        String [] ipPorts = getDb(driverName).getIpPort().split(COMMA_OR_SEMICOLON);
        Object[] ipPort = CasiaRandomUtil.randomNumber(ipPorts,1);
        return Validator.check(ipPort) ?HTTP+String.valueOf(ipPort[0]) : null;
    }
    protected String url(List<String> ipPorts){
        List<Object> ipPort = CasiaRandomUtil.randomNumber(ipPorts.stream().collect(Collectors.toList()),1);
        return Validator.check(ipPort) ? HTTP+String.valueOf(ipPort.get(0)) : null;
    }
    protected boolean debugInfo(){
        return debugOut;
    }
    protected Map<String, String> heards(String driverName){
        String key = CasiaBaseUtil.encrypt64(new StringBuffer().append(getDb(driverName).getUsername()).append(COLON).append(getDb(driverName).getPassword()).toString());
        Map<String, String> maps = new HashMap<>();
        maps.put(HttpHeader.USER_AGENT_FIREFOX.getName(),HttpHeader.USER_AGENT_FIREFOX.getValue());
        maps.put(HttpHeader.ACCEPT.getName(),HttpHeader.ACCEPT.getValue());
        maps.put(HttpHeader.ACCEPT_LANGUAGE.getName(),HttpHeader.ACCEPT_LANGUAGE.getValue());
        maps.put(HttpHeader.ACCEPT_ENCODING.getName(),HttpHeader.ACCEPT_ENCODING.getValue());
        maps.put(HttpHeader.AUTHORIZATION.getName(),HttpHeader.AUTHORIZATION.getValue()+key.replaceAll(RN,NONE));
        maps.put(HttpHeader.CONNECTION.getName(),HttpHeader.CONNECTION.getValue());
        return maps;
    }
    protected Map<String, String> heards(String username,String password){
        String key = Validator.check(username) && Validator.check(password) ? CasiaBaseUtil.encrypt64(new StringBuffer().append(username).append(COLON).append(password).toString()) :NONE;
        Map<String, String> maps = new HashMap<>();
        maps.put(HttpHeader.USER_AGENT_FIREFOX.getName(),HttpHeader.USER_AGENT_FIREFOX.getValue());
        maps.put(HttpHeader.ACCEPT.getName(),HttpHeader.ACCEPT.getValue());
        maps.put(HttpHeader.ACCEPT_LANGUAGE.getName(),HttpHeader.ACCEPT_LANGUAGE.getValue());
        maps.put(HttpHeader.ACCEPT_ENCODING.getName(),HttpHeader.ACCEPT_ENCODING.getValue());
        maps.put(HttpHeader.AUTHORIZATION.getName(),HttpHeader.AUTHORIZATION.getValue()+key.replaceAll(RN,NONE));
        maps.put(HttpHeader.CONNECTION.getName(),HttpHeader.CONNECTION.getValue());
        return maps;
    }
    protected String curl(String url,String ... routers){
        if( Validator.check(routers) ){
            for(String router:routers){
                url+= Validator.check(router) ? SLASH +router:"";
            }
        }
        return url;
    }
    protected String curlSymbol(String url,String symbol, String ... routers){
        if( Validator.check(routers) ){
            for(String router:routers){
                url+= Validator.check(router) ? symbol+router:"";
            }
        }
        return url;
    }
    protected _JSON_Array arrays = new _JSON_Array();
    protected _Entity_Query _entity_query = new _Entity_Query();
    protected IndexParmsStatus indexParmsStatus = new IndexParmsStatus();
    protected StringAppend stringAppend = new StringAppend();
    protected IndexSearchBuilder indexSearchBuilder = new IndexSearchBuilder();
    public void config(String driverName) {
        indexParmsStatus.setUrl(url(driverName));
        indexParmsStatus.setHeards(heards(driverName));
    }
    public void config(List<String> ipPorts) {
        indexParmsStatus.setUrl(url(ipPorts));
        indexParmsStatus.setHeards(heards(null,null));
    }
    public void config(List<String> ipPorts,String username,String password) {
        indexParmsStatus.setUrl(url(ipPorts));
        indexParmsStatus.setHeards(heards(username,password));
    }
    /**
     * pars translate query build keyword
     * @param jsono
     * @param keywordsCombine
     * @return
     */
    protected JSONObject parsQueryKeyWords(JSONObject jsono, KeywordsCombine keywordsCombine){
        if( Validator.check(keywordsCombine) && Validator.check(keywordsCombine.getKeyWordsBuiders()) ){
            boolean isReverse = keywordsCombine.getKeyWordsBuiders().stream().filter(s->Validator.check(s.getFieldOccurs())).findFirst().isPresent();
            isReverse = isReverse ? keywordsCombine.getKeyWordsBuiders().stream().filter(s->Validator.check(s.getFieldOccurs()) && s.getFieldOccurs().getIsMust().equals(MUST)).findFirst().isPresent() : !isReverse;
            keywordsCombine.getKeyWordsBuiders().forEach(s->{
                String SM = Validator.check(s.getFieldOccurs()) && s.getFieldOccurs().getIsMust().equals(MUST_NOT) ? MUST_NOT : SHOULD;
                if( !Validator.check(s.getKeywordsCombines()) ){
                    JSONObject matchjson = o();
                    //range
                    if( !Validator.check(s.getQueriesLevel()) && !Validator.check(s.getGeoQueryLevel()) && (Validator.check(s.getGte()) || Validator.check(s.getLte()) ) ){
                        matchjson.put( RANGE,o(s.getField(),o(o(GTE,s.getGte()),LTE,s.getLte()))) ;
                    }
                    //keyword格式
                    else if( Validator.check(s.getQueriesLevel()) ){
                        matchjson.put( s.getQueriesLevel().getLevel(),s.getQueriesLevel().getLevel()== QueriesLevel.All.getLevel() ? o() : o(s.getField(),s.getKeyWord())) ;
                    }
                    //lal格式
                    else if( Validator.check(s.getGeoQueryLevel()) && Validator.check(s.getGeoQueryInfo()) ){
                        if( s.getGeoQueryLevel().getLevel() .equals(GeoQueryLevel.Polygon.getLevel()) ){
                            JSONArray jsonArray =a();
                            s.getGeoQueryInfo().getPolygon().forEach(lal->jsonArray.add(o(o(LAT,lal.getLat()),LON,lal.getLon())));
                            matchjson.put(s.getGeoQueryLevel().getLevel(),o(s.getField(),o(POINTS,jsonArray) ));
                        }else if( s.getGeoQueryLevel().getLevel() .equals(GeoQueryLevel.Box.getLevel()) ){
                            matchjson.put(s.getGeoQueryLevel().getLevel(),o(s.getField(),o()));
                            s.getGeoQueryInfo().getBox().forEach((k,v)->{
                                matchjson.getJSONObject(s.getGeoQueryLevel().getLevel()).getJSONObject(s.getField()).put(k,o(o(LAT,v.getLat()),LON,v.getLon()));
                            });
                        }else if( s.getGeoQueryLevel().getLevel() .equals(GeoQueryLevel.Distance.getLevel()) ){
                            matchjson.put(s.getGeoQueryLevel().getLevel(),o());
                            matchjson.getJSONObject(s.getGeoQueryLevel().getLevel()).put(DISTANCE, s.getGeoQueryInfo().getDistance() );
                            matchjson.getJSONObject(s.getGeoQueryLevel().getLevel()).put(s.getField(),o(o(LAT,s.getGeoQueryInfo().getDistanceGeo().getLat()),LON,s.getGeoQueryInfo().getDistanceGeo().getLon()));
                        }else if( s.getGeoQueryLevel().getLevel() .equals(GeoQueryLevel.DistanceRange.getLevel()) ){
                            matchjson.put(s.getGeoQueryLevel().getLevel(),o());
                            matchjson.getJSONObject(s.getGeoQueryLevel().getLevel()).put(FROM, s.getGeoQueryInfo().getFrom() );
                            matchjson.getJSONObject(s.getGeoQueryLevel().getLevel()).put(TO, s.getGeoQueryInfo().getTo() );
                            matchjson.getJSONObject(s.getGeoQueryLevel().getLevel()).put(s.getField(),o(o(LAT,s.getGeoQueryInfo().getDistanceGeo().getLat()),LON,s.getGeoQueryInfo().getDistanceGeo().getLon()));
                        }
                    }
                    //其他
                    else{
                        return;
                    }
                    //组装
                    if( keywordsCombine.getKeyWordsBuiders().size() == 1 && !SM.equals(MUST_NOT) ){
                        jsono.putAll(matchjson);
                        return;
                    }
                    oAddoKey(jsono,BOOL);
                    if( SM.equals(MUST_NOT) ){
                        oAddaKey(jsono.getJSONObject(BOOL),SHOULD);
                        JSONObject js = o(BOOL,o(MUST_NOT,matchjson));
                        if( !jsono.getJSONObject(BOOL).getJSONArray(SHOULD).stream().filter(a->
                                a.toString() .equals( js.toString() ) ).findFirst().isPresent()
                        ){
                            jsono.getJSONObject(BOOL).getJSONArray(SHOULD).add( js );
                        }
                    }else{
                        oAddaKey(jsono.getJSONObject(BOOL),SM);
                        if( !jsono.getJSONObject(BOOL).getJSONArray(SM).stream().filter(a->
                                a.toString() .equals( matchjson.toString() ) ).findFirst().isPresent()
                        ){
                            jsono.getJSONObject(BOOL).getJSONArray(SM).add( matchjson );
                        }
                    }
                }else {
                    s.getKeywordsCombines().forEach(d->{
                        oAddoKey(jsono,BOOL);
                        oAddaKey(jsono.getJSONObject(BOOL),SHOULD);
                        jsono.getJSONObject(BOOL).getJSONArray(SHOULD).add(0,o());
                        parsQueryKeyWords(jsono.getJSONObject(BOOL).getJSONArray(SHOULD).getJSONObject(0),d);
                    });
                }
            });
            if(jsono.containsKey(BOOL)){
                keywordsCombine.setMinimumMatch( keywordsCombine.getMinimumMatch()>0 && keywordsCombine.getMinimumMatch()<=jsono.getJSONObject(BOOL).getJSONArray(SHOULD).size() ?
                        ( isReverse ? keywordsCombine.getMinimumMatch() : NumberUtil.reverseNum( jsono.getJSONObject(BOOL).getJSONArray(SHOULD).size(),keywordsCombine.getMinimumMatch()) )
                        : ( isReverse ? jsono.getJSONObject(BOOL).getJSONArray(SHOULD).size() : 1 ));
//                keywordsCombine.setMinimumMatch( keywordsCombine.getMinimumMatch()>0 && keywordsCombine.getMinimumMatch()<=jsono.getJSONObject(BOOL).getJSONArray(SHOULD).size() ?
//                        keywordsCombine.getMinimumMatch()
//                        : jsono.getJSONObject(BOOL).getJSONArray(SHOULD).size());
                jsono.getJSONObject(BOOL).put(MINIMUM_SHOULD_MATCH,keywordsCombine.getMinimumMatch());
            }
            return jsono;
        }else{
            return o();
        }
    }
}
