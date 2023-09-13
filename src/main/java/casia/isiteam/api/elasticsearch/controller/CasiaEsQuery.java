package casia.isiteam.api.elasticsearch.controller;

import casia.isiteam.api.elasticsearch.operation.interfaces.ElasticSearchApi;
import casia.isiteam.api.elasticsearch.router.ApiRouter;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * ClassName: CasiaEsQuery
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/4/29
 * Email: zhiyou_wang@foxmail.com
 */
public class CasiaEsQuery {
    private Logger logger = LoggerFactory.getLogger( this.getClass());

    private ElasticSearchApi.QueryApi queryApi;
    public CasiaEsQuery(String driverName){
        queryApi = ApiRouter.getQueryRouter();
        queryApi.config(driverName);
    }

    public CasiaEsQuery(List<String> ipPorts){
        queryApi = ApiRouter.getQueryRouter();
        queryApi.config(ipPorts);
    }

    public CasiaEsQuery(List<String> ipPorts,String username,String password){
        queryApi = ApiRouter.getQueryRouter();
        queryApi.config(ipPorts,username,password);
    }
    /**
     * set indexName and indexType
     * @param indexName
     * @param indexType
     */
    public void setIndexName(String indexName,String indexType) {
        queryApi.setIndexName(indexName,indexType);
    }
    public void setIndexName(String indexName) {
        queryApi.setIndexName(indexName,null);
    }
    public void setIndexType(String indexType) {
        queryApi.setIndexName(null,indexType);
    }
    /**
     * 查询索引信息根据索引名
     * @param indexName 索引名称
     * @return JSONObject 索引JSON结构
     */
    public JSONObject queryIndexByName (String indexName){
        return queryApi.queryIndexByName(indexName);
    }
    public JSONObject queryIndexByName (){
        return queryApi.queryIndexByName(null);
    }
    /**
     * 查询索引名列表
     * @return  List<String>  索引名集合
     */
    public List<String> queryIndexNames (){
        return queryApi.queryIndexNames();
    }
    /**
     * query all Alias
     * or
     * query all Alias by indexName
     * or
     * query all Alias by wildcard
     * @return map
     */
    public Map<String,List<String>> queryAlias(String wildcard){
        return queryApi.queryIndexAlias(wildcard);
    }
    public Map<String,List<String>> queryAlias(){
        return queryApi.queryIndexAlias(null);
    }

    /**
     * query task info by task id
     * @param taskId
     * @return
     */
    public JSONObject queryTask(String taskId){
        return queryApi.queryTask(taskId);
    }
}
