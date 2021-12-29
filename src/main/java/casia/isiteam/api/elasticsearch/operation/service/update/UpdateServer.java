package casia.isiteam.api.elasticsearch.operation.service.update;

import casia.isiteam.api.elasticsearch.operation.interfaces.ElasticSearchApi;
import casia.isiteam.api.elasticsearch.util.JSONCompare;
import casia.isiteam.api.elasticsearch.util.LogUtil;
import casia.isiteam.api.http.controller.CasiaHttpUtil;
import casia.isiteam.api.toolutil.Validator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * ClassName: UpdateServer
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/4/29
 * Email: zhiyou_wang@foxmail.com
 */
public class UpdateServer extends ElasticSearchApi implements ElasticSearchApi.UpadeApi {
    private Logger logger = LoggerFactory.getLogger( this.getClass());

    public void setIndexName(String indexName,String indexType) {
        addIndexName(indexName,indexType);
    }
    /**
     * update data info by _id
     * @param _id PRIMARY KEY
     * @param parameters parameters
     */
    public boolean updateParameterById (String _id ,Map< String, Object > parameters){
        if( !Validator.check(parameters) ){
            logger.warn(LogUtil.compositionLogEmpty("updateParameterById parameters "));
            return true;
        }
        String curl=curl(indexParmsStatus.getUrl(),indexParmsStatus.getIndexName(),indexParmsStatus.getIndexType(), _id , _UPDATE );
        logger.debug(LogUtil.compositionLogCurl(curl));
        JSONObject js = o(DOC,o(JSON.toJSONString(parameters)));
        CasiaHttpUtil casiaHttpUtil = new CasiaHttpUtil();
        String resultStr = casiaHttpUtil.post( curl,indexParmsStatus.getHeards(),null, js.toString() );
        return JSONCompare.validationResult(resultStr,RESULT,UPDATE,NOOP);
    }
    /**
     * update data info by query
     * @param _id PRIMARY KEY
     * @param parameters parameters
     */
    public boolean updateParameterByQuery (String _id ,Map< String, Object > parameters){
        return false;
    }
<<<<<<< Updated upstream
=======
    /**
     * update OR create data info by _id
     * @param _id PRIMARY KEY
     * @param update_parameters update_parameters
     * @param create_parameters create_parameters
     */
    public boolean upsertParameterById (String _id ,JSONObject update_parameters,JSONObject create_parameters){
        if( !Validator.check(update_parameters) || !Validator.check(_id) || !Validator.check(create_parameters) ){
            logger.warn(LogUtil.compositionLogEmpty(" parameters "));
            return true;
        }

        String curl=curl(indexParmsStatus.getUrl(),indexParmsStatus.getIndexName(),indexParmsStatus.getIndexType(), _id , _UPDATE );
        logger.debug(LogUtil.compositionLogCurl(curl));
        JSONObject js = o(o(DOC,update_parameters),UPSERT,create_parameters);
        CasiaHttpUtil casiaHttpUtil = new CasiaHttpUtil();
        String resultStr = casiaHttpUtil.post( curl,indexParmsStatus.getHeards(),null, js.toString() );
        boolean rs= JSONCompare.validationResult(resultStr,RESULT,UPDATE,UPDATED,NOOP,CREATED);
        if(!rs){
            logger.warn(resultStr);
        }
        return rs;
    }
    /**
     * move shard to node
     * @param shard_id
     * @param from_node_name
     * @param to_node_name
     * @return
     */
    public boolean moveShardRoute (int shard_id ,String from_node_name,String to_node_name){
        if( !Validator.check(from_node_name) || !Validator.check(to_node_name) ){
            logger.warn(LogUtil.compositionLogEmpty("node_name "));
            return true;
        }
        String curl=curl(indexParmsStatus.getUrl(), _CLUSTER , REROUTE );
        JSONObject js = o(COMMANDS,a(MOVE,o(o(o(o(INDEX,indexParmsStatus.getIndexName()),SHARD,shard_id),FROM_NODE,from_node_name),TO_NODE,to_node_name)));
        if( debugInfo() ){
            logger.info(LogUtil.compositionLogCurl(curl,js));
        }
        CasiaHttpUtil casiaHttpUtil = new CasiaHttpUtil();
        String resultStr = casiaHttpUtil.post( curl,indexParmsStatus.getHeards(),null, js.toString() );
        boolean rs= JSONCompare.validationResult(resultStr,ACKNOWLEDGED);
        if(!rs){
            logger.warn(resultStr);
        }
        return rs;
    }
    /**
     * cancel shard to node
     * @param shard_id
     * @param node_name
     * @return
     */
    public boolean cancelShardRoute (int shard_id ,String node_name){
        if( !Validator.check(node_name) ){
            logger.warn(LogUtil.compositionLogEmpty("node_name "));
            return true;
        }
        String curl=curl(indexParmsStatus.getUrl(), _CLUSTER , REROUTE );
        logger.debug(LogUtil.compositionLogCurl(curl));
        JSONObject js = o(COMMANDS,a(CANCEL,o(o(o(INDEX,indexParmsStatus.getIndexName()),SHARD,shard_id),NODE,node_name)));
        CasiaHttpUtil casiaHttpUtil = new CasiaHttpUtil();
        String resultStr = casiaHttpUtil.post( curl,indexParmsStatus.getHeards(),null, js.toString() );
        boolean rs= JSONCompare.validationResult(resultStr,ACKNOWLEDGED);
        if(!rs){
            logger.warn(resultStr);
        }
        return rs;
    }
    /**
     * ALLOCATE shard to node
     * @param shard_id
     * @param node_name
     * @return
     */
    public boolean allocateShardRoute (int shard_id ,String node_name){
        if( !Validator.check(node_name) ){
            logger.warn(LogUtil.compositionLogEmpty("node_name "));
            return true;
        }
        String curl=curl(indexParmsStatus.getUrl(), _CLUSTER , REROUTE );

        logger.debug(LogUtil.compositionLogCurl(curl));
        JSONObject js = o(COMMANDS,a(ALLOCATE,o(o(o(INDEX,indexParmsStatus.getIndexName()),SHARD,shard_id),NODE,node_name)));
        CasiaHttpUtil casiaHttpUtil = new CasiaHttpUtil();
        logger.info("{} {}",curl,js);
        String resultStr = casiaHttpUtil.post( curl,indexParmsStatus.getHeards(),null, js.toString() );
        boolean rs= JSONCompare.validationResult(resultStr,ACKNOWLEDGED);
        if(!rs){
            logger.warn(resultStr);
        }
        return rs;
    }
    /**
     * set number of replicas
     * @param replicas_number
     * @return
     */
    public boolean updateNumberOfReplicas (int replicas_number ){
        String curl=curl(indexParmsStatus.getUrl(), indexParmsStatus.getIndexName() , _SETTINGS );
        logger.debug(LogUtil.compositionLogCurl(curl));
        JSONObject js = o(NUMBER_OF_REPLICAS,replicas_number);
        CasiaHttpUtil casiaHttpUtil = new CasiaHttpUtil();
        String resultStr = casiaHttpUtil.put( curl,indexParmsStatus.getHeards(),null, js.toString() );
        boolean rs= JSONCompare.validationResult(resultStr,ACKNOWLEDGED);
        if(!rs){
            logger.warn(resultStr);
        }
        return rs;
    }
    /**
     * update data info by query
     * @param field field
     * @param newValue parameter
     */
    public Map<String,Object> updateParameterByQuery (String field, Object newValue){
        Map<String,Object> maps = new HashMap<>();
        if( !Validator.check(field) ){
            logger.warn(LogUtil.compositionLogEmpty("updateParameterByQuery field "));
            return maps;
        }
        String curl=curl(indexParmsStatus.getUrl(),indexParmsStatus.getIndexName(),indexParmsStatus.getIndexType(), _UPDATE_BY_QUERY);
        logger.debug(LogUtil.compositionLogCurl(curl));
        JSONObject js = o(o(QUERY,indexSearchBuilder.getDelSearch()),SCRIPT,o(INLINE,String.format(Scripting.inline,field,newValue)));
        CasiaHttpUtil casiaHttpUtil = new CasiaHttpUtil();
        String resultStr = casiaHttpUtil.post( curl,indexParmsStatus.getHeards(),null, js.toString() );
        try {
            if( validationResult(resultStr,TIMED_OUT,false) ){
                JSONObject t= o(resultStr);
                maps.put(TIMED_OUT,t.get(TIMED_OUT));
                maps.put(TOTAL,t.get(TOTAL));
                maps.put(UPDATE,t.get(UPDATED));
                maps.put(DELETE,t.get(DELETED));
                maps.put(BATCHE,t.get(BATCHES));
//                logger.info("update success by {}, update:{}, total:{}",indexParmsStatus.getIndexName(),t.get(UPDATED),t.get(TOTAL));
            };
        }catch (Exception e){
            logger.error("result：{}；error：",resultStr,e.getMessage());
            return maps;
        }
        return maps;
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

>>>>>>> Stashed changes
}
