package casia.isiteam.api.elasticsearch.operation.service.delete;

import casia.isiteam.api.elasticsearch.operation.interfaces.ElasticSearchApi;
import casia.isiteam.api.elasticsearch.util.JSONCompare;
import casia.isiteam.api.elasticsearch.util.LogUtil;
import casia.isiteam.api.http.controller.CasiaHttpUtil;
import casia.isiteam.api.toolutil.Validator;
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

}
