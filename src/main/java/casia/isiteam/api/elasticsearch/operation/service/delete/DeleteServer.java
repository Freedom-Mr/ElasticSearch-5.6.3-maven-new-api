package casia.isiteam.api.elasticsearch.operation.service.delete;

import casia.isiteam.api.elasticsearch.common.enums.FieldOccurs;
import casia.isiteam.api.elasticsearch.common.enums.GeoQueryLevel;
import casia.isiteam.api.elasticsearch.common.vo.field.RangeField;
import casia.isiteam.api.elasticsearch.common.vo.field.search.KeywordsCombine;
import casia.isiteam.api.elasticsearch.operation.interfaces.ElasticSearchApi;
import casia.isiteam.api.elasticsearch.util.JSONCompare;
import casia.isiteam.api.elasticsearch.util.LogUtil;
import casia.isiteam.api.http.controller.CasiaHttpUtil;
import casia.isiteam.api.toolutil.Validator;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * ClassName: DeleteServer
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/4/28
 * Email: zhiyou_wang@foxmail.com
 */
public class DeleteServer extends ElasticSearchApi implements ElasticSearchApi.DelApi {
    private Logger logger = LoggerFactory.getLogger( this.getClass());

    public void setIndexName(String indexName,String indexType) {
        addIndexName(indexName,indexType);
    }
    /**
     * delete index by indexName
     * @return true or false
     */
    @Override
    public boolean deleteIndexByName() {
        if( !Validator.check( indexParmsStatus.getIndexName() ) ){
            logger.warn(LogUtil.compositionLogEmpty("indexName"));
            return false;
        }
        String curl=curl(indexParmsStatus.getUrl(),indexParmsStatus.getIndexName());
        logger.debug(LogUtil.compositionLogCurl(curl,indexParmsStatus.getIndexName()));
        String resultStr = new CasiaHttpUtil().delete(curl,indexParmsStatus.getHeards());
        return JSONCompare.validationResult(resultStr,ACKNOWLEDGED);
    }
    /**
     * delete data by _ids
     * @param id id
     * @return true or false
     */
    @Override
    public boolean deleteDataById(String id){
        if( !Validator.check( id ) ){
            logger.warn(LogUtil.compositionLogEmpty("id"));
            return false;
        }
        String curl= curl(indexParmsStatus.getUrl(),indexParmsStatus.getIndexName(),indexParmsStatus.getIndexType(),id) ;
        logger.debug(LogUtil.compositionLogCurl(curl));
        String resultStr = new CasiaHttpUtil().delete(curl,indexParmsStatus.getHeards());
        return JSONCompare.validationResult(resultStr,RESULT,DELETED,NOT_FOUND);
    }
    /**
     * delete data by _ids
     * @param _ids
     * 			_ids
     * @return true or false
     */
    @Override
    public boolean deleteDataByIds(List<String> _ids){
        if( !Validator.check( _ids ) ){
            logger.warn(LogUtil.compositionLogEmpty("_ids"));
            return false;
        }
        String curl=curlSymbol( curl(indexParmsStatus.getUrl(),indexParmsStatus.getIndexName(),indexParmsStatus.getIndexType(),_QUERY),QUESTION,PRETTY  );
        logger.debug(LogUtil.compositionLogCurl(curl));
        JSONObject body = o(QUERY,o(MATCH,o(ID, _ids)));
        String resultStr = new CasiaHttpUtil().delete(curl,indexParmsStatus.getHeards(),null,body.toString());
        return JSONCompare.validationResult(resultStr,RESULT,DELETED,NOT_FOUND);
    }
    /**
     * delete Scroll by Scroll_id
     * @param scroll_ids
     * 			scroll_ids
     * @return true or false
     */
    @Override
    public boolean deleteScrollByIds(List<String> scroll_ids){
        if( !Validator.check( scroll_ids ) ){
            logger.warn(LogUtil.compositionLogEmpty("scroll_ids"));
            return false;
        }
        String curl=curl(indexParmsStatus.getUrl(),_SEARCH,SCROLL);
        logger.debug(LogUtil.compositionLogCurl(curl));
        JSONObject body = o(SCROLL_ID,a(MATCH,scroll_ids));
        String resultStr = new CasiaHttpUtil().delete(curl,indexParmsStatus.getHeards(),null,body.toString());
        return JSONCompare.validationResult(resultStr,RESULT,DELETED,NOT_FOUND);
    }
    /**
     * delete all Scroll_id
     * @return true or false
     */
    @Override
    public boolean deleteScrollByAll(){
        String curl=curl(indexParmsStatus.getUrl(),_SEARCH,SCROLL,_ALL);
        logger.debug(LogUtil.compositionLogCurl(curl));
        String resultStr = new CasiaHttpUtil().delete(curl,indexParmsStatus.getHeards(),null,null);
        return true;
    }
    /**
     * clear Cache by indexName
     * or clear all Cache
     * @return true or false
     */
    @Override
    public boolean clearCache(){
        String curl=curl(indexParmsStatus.getUrl(),Validator.check(indexParmsStatus.getIndexName()) ? indexParmsStatus.getIndexName() : "",_CACHE,CLEAR );
        logger.debug(LogUtil.compositionLogCurl(curl));
        String resultStr = new CasiaHttpUtil().post(curl,indexParmsStatus.getHeards(),null,null);
        return o(resultStr).containsKey(_SHARDS);
    }




    /**
     * delete data by query String
     * @return
     */
    public int deleteDataByQuery(){
        if( !Validator.check(indexSearchBuilder.getDelSearch()) ){
            logger.warn(LogUtil.compositionLogEmpty("query string ") );
            return 0;
        }
        String curl=curl(indexParmsStatus.getUrl(),indexParmsStatus.getIndexName(),indexParmsStatus.getIndexType(),_DELETE_BY_QUERY);
        logger.debug(LogUtil.compositionLogCurl(curl,indexSearchBuilder.getDelSearch()));
        JSONObject body = o(QUERY,indexSearchBuilder.getDelSearch());
        String resultStr = new CasiaHttpUtil().post(curl,indexParmsStatus.getHeards(),null,body.toString());
        return JSONCompare.getResult(resultStr,DELETED);
    }

    public void setRange(RangeField... rangeFields) {
        for(RangeField filed : rangeFields ){
            JSONObject rangefiled = o(RANGE,o(filed.getField(),o(o(GTE,filed.getGte()),LTE,filed.getLte())));
            if( !indexSearchBuilder.addQueryBigBoolKeyArray(filed.getFieldOccurs().getIsMust()).
                    getQueryBigBool().getJSONArray(filed.getFieldOccurs().getIsMust()).stream().filter(s ->
                    JSONObject.parseObject(s.toString()).containsKey(RANGE) &&  JSONObject.parseObject(s.toString()).getString(RANGE) .equals(
                            rangefiled.getString(RANGE)
                    )
            ).findFirst().isPresent() ){
                indexSearchBuilder.getQueryBigBool().getJSONArray(filed.getFieldOccurs().getIsMust()).add(rangefiled);
            }
        }
    }

    public void setFieldExistsFilter(FieldOccurs fieldOccurs, String ... fileds) {
        for(String filed : fileds ){
            JSONObject existsFiled = o(EXISTS,o(FIELD,filed));
            if( !indexSearchBuilder.addQueryBigBoolKeyArray(fieldOccurs.getIsMust()).getQueryBigBool().getJSONArray(fieldOccurs.getIsMust()).stream().filter(s ->
                    JSONObject.parseObject(s.toString()).containsKey(EXISTS) &&  JSONObject.parseObject(s.toString()).getString(EXISTS) .equals(
                            existsFiled.getString(EXISTS)
                    )
            ).findFirst().isPresent() ){
                indexSearchBuilder.getQueryBigBool().getJSONArray(fieldOccurs.getIsMust()).add(existsFiled);
            }
        }
    }
    public void setQueryKeyWords(KeywordsCombine... keywordsCombines){
        for(KeywordsCombine keywordsCombine: keywordsCombines){
            JSONObject newShould = parsQueryKeyWords(o(),keywordsCombine);
            oAddaKey(indexSearchBuilder.getQueryBigBool(),SHOULD);
            if( !indexSearchBuilder.getQueryBigBool().getJSONArray(SHOULD).stream().filter(s->
                    s.toString().equals( newShould.toString() )
            ).findFirst().isPresent() ){
                indexSearchBuilder.getQueryBigBool().getJSONArray(SHOULD).add(newShould);
                indexSearchBuilder.getQueryBigBool().put(MINIMUM_SHOULD_MATCH,indexSearchBuilder.getQueryBigBool().getJSONArray(SHOULD).size());
            }
        }
    }
    /**
     * pars translate query build keyword
     * @param jsono
     * @param keywordsCombine
     * @return
     */
    private JSONObject parsQueryKeyWords(JSONObject jsono,KeywordsCombine keywordsCombine){
        if( Validator.check(keywordsCombine) && Validator.check(keywordsCombine.getKeyWordsBuiders()) ){
            keywordsCombine.getKeyWordsBuiders().forEach(s->{
                String SM = Validator.check(s.getFieldOccurs()) && s.getFieldOccurs().getIsMust().equals(MUST_NOT) ? MUST_NOT : SHOULD;
                if( !Validator.check(s.getKeywordsCombines()) ){
                    JSONObject matchjson = o();

                    //关键词格式
                    if( Validator.check(s.getQueriesLevel()) ){
                        matchjson.put( s.getQueriesLevel().getLevel(),o(s.getField(),s.getKeyWord())) ;
                    }
                    //地理位置格式
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
                        keywordsCombine.getMinimumMatch() : jsono.getJSONObject(BOOL).getJSONArray(SHOULD).size() );
                jsono.getJSONObject(BOOL).put(MINIMUM_SHOULD_MATCH,keywordsCombine.getMinimumMatch());
            }
            return jsono;
        }else{
            return o();
        }
    }
}
