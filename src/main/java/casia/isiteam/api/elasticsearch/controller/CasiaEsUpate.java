package casia.isiteam.api.elasticsearch.controller;

import casia.isiteam.api.elasticsearch.common.enums.FieldOccurs;
import casia.isiteam.api.elasticsearch.common.vo.field.RangeField;
import casia.isiteam.api.elasticsearch.common.vo.field.search.KeywordsCombine;
import casia.isiteam.api.elasticsearch.operation.interfaces.ElasticSearchApi;
import casia.isiteam.api.elasticsearch.router.ApiRouter;
import casia.isiteam.api.toolutil.Validator;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * ClassName: CasiaEsUpate
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/4/27
 * Email: zhiyou_wang@foxmail.com
 */
public class CasiaEsUpate {
    private Logger logger = LoggerFactory.getLogger( this.getClass());
    private ElasticSearchApi.UpadeApi upadeApi;
    public CasiaEsUpate(String driverName){
        upadeApi = ApiRouter.getUpdateRouter();
        upadeApi.config(driverName);
    }

    public CasiaEsUpate(List<String> ipPorts){
        upadeApi = ApiRouter.getUpdateRouter();
        upadeApi.config(ipPorts);
    }

    public CasiaEsUpate(List<String> ipPorts,String username,String password){
        upadeApi = ApiRouter.getUpdateRouter();
        upadeApi.config(ipPorts,username,password);
    }

    /**
     * 设置索引名称及其类型
     * @param indexName
     * @param indexType
     */
    public void setIndexName(String indexName,String indexType) {
        upadeApi.setIndexName(indexName,indexType);
    }
    public void setIndexName(String indexName) {
        upadeApi.setIndexName(indexName,null);
    }
    public void setIndexType(String indexType) {
        upadeApi.setIndexName(null,indexType);
    }
    /**
     * 根据主键修改字段值
     * @param _id 主键
     * @param parameters 参数
     */
    public Boolean updateParameterById(String _id ,Map< String, Object > parameters){
        return upadeApi.updateParameterById(_id,parameters);
    }
    public boolean updateParameterById (String _id , JSONObject parameters){
        return upadeApi.updateParameterById(_id,parameters);
    };
    public boolean upsertParameterById (String _id ,JSONObject update_parameters,JSONObject create_parameters){
        return upadeApi.upsertParameterById(_id,update_parameters,create_parameters);
    };

    public Map<String,Object> updateParameterByQuery(String field, Object newValue){
        return upadeApi.updateParameterByQuery(field,newValue);
    }
    public boolean moveShardRoute(int shard_id ,String from_node_name,String to_node_name){
        return upadeApi.moveShardRoute(shard_id,from_node_name,to_node_name);
    }
    public boolean cancelShardRoute(int shard_id ,String node_name){
        return upadeApi.cancelShardRoute(shard_id,node_name);
    }
    public boolean allocateShardRoute(int shard_id ,String node_name){
        return upadeApi.allocateShardRoute(shard_id,node_name);
    }
    public boolean updateNumberOfReplicas(int replicas_number){
        return upadeApi.updateNumberOfReplicas(replicas_number);
    }

    public void reset() {
        upadeApi.reset();
    }
    /**
     * set filed range
     * @param rangeFields
     * @return
     */
    public CasiaEsUpate setRange(RangeField... rangeFields) {
        if( Validator.check(rangeFields) ){
            upadeApi.setRange(rangeFields);
        }
        return this;
    }
    /**
     * filter missing filed
     * @param fields
     * @return
     */
    public CasiaEsUpate setMissingFilter(String ... fields) {
        if( Validator.check(fields) ){
            upadeApi.setFieldExistsFilter(FieldOccurs.EXCLUDES,fields);
        }
        return this;
    }
    /**
     *  filter exists filed
     * @param fields
     * @return
     */
    public CasiaEsUpate setExistsFilter(String ... fields) {
        if( Validator.check(fields) ){
            upadeApi.setFieldExistsFilter(FieldOccurs.INCLUDES,fields);
        }
        return this;
    }
    /**
     *  set query keyword
     * @param keywordsCombines
     * @return
     */
    public CasiaEsUpate setQueryKeyWords(KeywordsCombine... keywordsCombines) {
        if( Validator.check(keywordsCombines) ){
            upadeApi.setQueryKeyWords(keywordsCombines);
        }
        return this;
    }
}
