package casia.isiteam.api.elasticsearch.operation.service.create;

import casia.isiteam.api.elasticsearch.common.staitcParms.ShareParms;
import casia.isiteam.api.elasticsearch.common.status.IndexParmsStatus;
import casia.isiteam.api.elasticsearch.operation.interfaces.ElasticSearchApi;
import casia.isiteam.api.elasticsearch.util.JSONCompare;
import casia.isiteam.api.elasticsearch.util.LogUtil;
import casia.isiteam.api.http.controller.CasiaHttpUtil;
import casia.isiteam.api.toolutil.Validator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * ClassName: CreateServer
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/4/27
 * Email: zhiyou_wang@foxmail.com
 */
public class CreateServer extends ElasticSearchApi implements ElasticSearchApi.CreateApi {
    private Logger logger = LoggerFactory.getLogger( this.getClass());

    public void setIndexName(String indexName,String indexType) {
        addIndexName(indexName,indexType);
    }
    /**
     * create index
     * @param indexName
     * @param mapping
     * @return true or false
     */
    @Override
    public boolean creatIndex(String indexName ,String mapping) {
        if( !Validator.check( mapping ) ){
            logger.warn(LogUtil.compositionLogEmpty("mapping"));
            return false;
        }
        String curl=curl(indexParmsStatus.getUrl(),indexName);
        logger.debug(LogUtil.compositionLogCurl(curl,mapping));
        String resultStr = new CasiaHttpUtil().put(curl,indexParmsStatus.getHeards(),null,mapping);
        return JSONCompare.validationResult(resultStr,ACKNOWLEDGED);
    }
    /**
     * create index
     * @param mapping
     * @return true or false
     */
    @Override
    public boolean creatIndex(String mapping) {
        if( !Validator.check( mapping ) ){
            logger.warn(LogUtil.compositionLogEmpty("mapping"));
            return false;
        }
        String curl=curl(indexParmsStatus.getUrl(),indexParmsStatus.getIndexName());
        logger.debug(LogUtil.compositionLogCurl(curl,mapping));
        String resultStr = new CasiaHttpUtil().put(curl,indexParmsStatus.getHeards(),null,mapping);
        return JSONCompare.validationResult(resultStr,ACKNOWLEDGED);
    }
    /**
     * write data to index
     * @param datas
     * @param uniqueKeyName
     * @param bakingName
     * @return true or false
     */
    @Override
    public boolean writeData( List<JSONObject> datas , String uniqueKeyName ,String bakingName){
        if( !Validator.check( datas ) ){
            logger.warn(LogUtil.compositionLogEmpty("datas"));
            return true;
        }
        String curl=curl(indexParmsStatus.getUrl(),indexParmsStatus.getIndexName(),indexParmsStatus.getIndexType(),_BULK);
        logger.debug(LogUtil.compositionLogCurl(curl,uniqueKeyName,datas.size()));

        StringBuffer indexStrBuffer = new StringBuffer();
        datas.stream().filter(s->s.containsKey(uniqueKeyName)).forEach(s->{
            indexStrBuffer.append(
                    os(INDEX,
                            o(_ID,s.getString(uniqueKeyName))+  //设置类型数据存储分片
                                    ( Validator.check(bakingName) ?  o(bakingName,BAKING)+"" : NONE)
                    )
            ).append(N)
                    .append(
                        s
                    )
                    .append(N);
        });
        String resultStr = new CasiaHttpUtil().post(curl,indexParmsStatus.getHeards(),null,indexStrBuffer.toString());
        try {
            boolean c = validationResult(resultStr,ERRORS,false);
            if( !c ){
                logger.info("fail：{}",resultStr);
                logger.error(resultStr);
            }
            return c;
        }catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
    }
    /**
     * write data to index
     * @param datas
     * @param uniqueKeyName
     * @param bakingName
     * @return true or false
     */
    public Map<String,Object> writeDatas( List<JSONObject> datas , String uniqueKeyName ,String bakingName){
        Map<String,Object> maps = new HashMap<>();
        maps.put("create",0L);
        maps.put("update",0L);
        maps.put("failed",0L);

        if( !Validator.check( datas ) ){
            logger.warn(LogUtil.compositionLogEmpty("datas"));
            return maps;
        }
        String curl=curl(indexParmsStatus.getUrl(),indexParmsStatus.getIndexName(),indexParmsStatus.getIndexType(),_BULK);
        logger.debug(LogUtil.compositionLogCurl(curl,uniqueKeyName,datas.size()));

        StringBuffer indexStrBuffer = new StringBuffer();
        datas.stream().filter(s->s.containsKey(uniqueKeyName)).forEach(s->{
            indexStrBuffer.append(
                    os(INDEX,
                            o(_ID,s.getString(uniqueKeyName))+  //设置类型数据存储分片
                                    ( Validator.check(bakingName) ?  o(bakingName,BAKING)+"" : NONE)
                    )
            ).append(N)
                    .append(
                            s
                    )
                    .append(N);
        });
        String resultStr = new CasiaHttpUtil().post(curl,indexParmsStatus.getHeards(),null,indexStrBuffer.toString());
        try {
            return validationResultByCreateDate(datas.size(),resultStr);
        }catch (Exception e){
            logger.error(e.getMessage());
            maps.put("failed",datas.size());
            return maps;
        }
    }
    /**
     * insert field to index
     * @param fieldName field name
     * @param map   field parms
     * @return true or false
     */
    @Override
    public boolean insertField ( String fieldName, Map<String, String> map ){
        if( !Validator.check( map ) ){
            logger.warn(LogUtil.compositionLogEmpty("field info"));
            return true;
        }
        String curl=curlSymbol( curl(indexParmsStatus.getUrl(),indexParmsStatus.getIndexName(),indexParmsStatus.getIndexType(),_MAPPING) , QUESTION , PRETTY );

        CasiaHttpUtil casiaHttpUtil = new CasiaHttpUtil();
        JSONObject parms =o(indexParmsStatus.getIndexType(),
                o(PROPERTIES,
                        o(fieldName,
                           JSON.toJSON(map)
                    )
                )
        );
        logger.debug(LogUtil.compositionLogCurl(curl,parms));
        String queryResultStr = casiaHttpUtil.post( curl,indexParmsStatus.getHeards(),null, parms.toString() );
        return JSONCompare.validationResult(queryResultStr,ACKNOWLEDGED,UPDATE,TRUE);
    }

    @Override
    public boolean closeIndex(){
        if( !Validator.check( indexParmsStatus.getIndexName() ) ){
            logger.warn(LogUtil.compositionLogEmpty("indexName"));
            return false;
        }
        String curl=curl(indexParmsStatus.getUrl(),indexParmsStatus.getIndexName(), _CLOSE);
        logger.debug(LogUtil.compositionLogCurl(curl));
        CasiaHttpUtil casiaHttpUtil = new CasiaHttpUtil();
        String queryResultStr = casiaHttpUtil.post( curl,indexParmsStatus.getHeards(),null, null );
        try {
            return o(queryResultStr).getBoolean(ACKNOWLEDGED);
        }catch (Exception e){
            logger.error("result：{}；error：",queryResultStr,e.getMessage());
            return false;
        }
    }
    @Override
    public boolean openIndex(){
        if( !Validator.check( indexParmsStatus.getIndexName() ) ){
            logger.warn(LogUtil.compositionLogEmpty("indexName"));
            return false;
        }
        String curl=curl(indexParmsStatus.getUrl(),indexParmsStatus.getIndexName(),_OPEN);
        logger.debug(LogUtil.compositionLogCurl(curl));
        CasiaHttpUtil casiaHttpUtil = new CasiaHttpUtil();
        String queryResultStr = casiaHttpUtil.post( curl,indexParmsStatus.getHeards(),null, null );
        try {
            return o(queryResultStr).getBoolean(ACKNOWLEDGED);
        }catch (Exception e){
            logger.error("result：{}；error：",queryResultStr,e.getMessage());
            return false;
        }
    }
    @Override
    public boolean flushIndex() {
        String curl=curl(indexParmsStatus.getUrl(),Validator.check(indexParmsStatus.getIndexName())?indexParmsStatus.getIndexName() : "",_FLUSH);
        logger.debug(LogUtil.compositionLogCurl(curl));
        CasiaHttpUtil casiaHttpUtil = new CasiaHttpUtil();
        String queryResultStr = casiaHttpUtil.post( curl,indexParmsStatus.getHeards(),null, null );
        try {
            return o(queryResultStr).containsKey(_SHARDS);
        }catch (Exception e){
            logger.error("result：{}；error：",queryResultStr,e.getMessage());
            return false;
        }
    }
    @Override
    public boolean refreshIndex() {
        String curl=curl(indexParmsStatus.getUrl(),Validator.check(indexParmsStatus.getIndexName())?indexParmsStatus.getIndexName() : "",_REFRESH);
        logger.debug(LogUtil.compositionLogCurl(curl));
        CasiaHttpUtil casiaHttpUtil = new CasiaHttpUtil();
        String queryResultStr = casiaHttpUtil.post( curl,indexParmsStatus.getHeards(),null, null );
        try {
            return o(queryResultStr).containsKey(_SHARDS);
        }catch (Exception e){
            logger.error("result：{}；error：",queryResultStr,e.getMessage());
            return false;
        }
    }
    @Override
    public Map<String,Object> reIndexData(String oldIndexName,String newIndexName) {
        Map<String,Object> maps = new HashMap<>();
        String curl=curl(indexParmsStatus.getUrl(),_REINDEX);
        JSONObject parms =o(o(SOURCE,o(INDEX,oldIndexName)),DEST,o(INDEX,newIndexName));
        logger.info("start reindex {} by {}",newIndexName,oldIndexName);
        System.out.println(parms);
        CasiaHttpUtil casiaHttpUtil = new CasiaHttpUtil();
        String queryResultStr = casiaHttpUtil.post( curl,indexParmsStatus.getHeards(),null, parms.toString() );
        try {
            if( validationResult(queryResultStr,TIMED_OUT,false) ){
                JSONObject t= o(queryResultStr);
                maps.put(TIMED_OUT,t.get(TIMED_OUT));
                maps.put(TOTAL,t.get(TOTAL));
                maps.put(UPDATE,t.get(UPDATED));
                maps.put(CREATE,t.get(CREATED));
                maps.put(DELETE,t.get(DELETED));
                maps.put(BATCHE,t.get(BATCHES));
                logger.info("reindex success {} by {}, total:{}",newIndexName,oldIndexName,t.get(TOTAL));
            };
        }catch (Exception e){
            logger.error("result：{}；error：",queryResultStr,e.getMessage());
            return maps;
        }
        return maps;
    }
    @Override
    public boolean addIndexAlias(String alias) {
        String curl=curl(indexParmsStatus.getUrl(),_ALIASES);
        JSONObject parms =o( ACTIONS, a( o(ADD,o(o(INDEX,indexParmsStatus.getIndexName()),ALIAS,alias) ) ) );
        CasiaHttpUtil casiaHttpUtil = new CasiaHttpUtil();
        String queryResultStr = casiaHttpUtil.post( curl,indexParmsStatus.getHeards(),null, parms.toString() );
        try {
           return validationResult(queryResultStr,ACKNOWLEDGED,true);
        }catch (Exception e){
            logger.error("result：{}；error：",queryResultStr,e.getMessage());
            return false;
        }
    }
}
