package casia.isiteam.api.elasticsearch.operation.service.delete;

import casia.isiteam.api.elasticsearch.common.enums.FieldOccurs;
import casia.isiteam.api.elasticsearch.common.enums.GeoQueryLevel;
import casia.isiteam.api.elasticsearch.common.status.IndexSearchBuilder;
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
    @Override
    public boolean delIndexAlias(String alias) {
        String curl=curl(indexParmsStatus.getUrl(),_ALIASES);
        JSONObject parms =o( ACTIONS, a( o(REMOVE,o(o(INDEX,indexParmsStatus.getIndexName()),ALIAS,alias) ) ) );
        CasiaHttpUtil casiaHttpUtil = new CasiaHttpUtil();
        String queryResultStr = casiaHttpUtil.post( curl,indexParmsStatus.getHeards(),null, parms.toString() );
        try {
            return validationResult(queryResultStr,ACKNOWLEDGED,true);
        }catch (Exception e){
            logger.error("result：{}；error：",queryResultStr,e.getMessage());
            return false;
        }
    }


    /***************Query******************/
    /**
     * clearn query parms
     */
    @Override
    public void reset(){
        indexSearchBuilder = new IndexSearchBuilder();
    };
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



}
