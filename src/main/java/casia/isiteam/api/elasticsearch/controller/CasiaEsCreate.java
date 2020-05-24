package casia.isiteam.api.elasticsearch.controller;

import casia.isiteam.api.elasticsearch.operation.interfaces.ElasticSearchApi;
import casia.isiteam.api.elasticsearch.router.ApiRouter;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * ClassName: CasiaEsCreate
 * Description: create info class, on elasticsearch 5.6.3
 * <p>
 * Created by casia.wzy on 2020/4/27
 * Email: zhiyou_wang@foxmail.com
 */
public class CasiaEsCreate {
    private Logger logger = LoggerFactory.getLogger( this.getClass());

    private ElasticSearchApi.CreateApi createApi;
    public CasiaEsCreate(String driverName){
        createApi = ApiRouter.getCreateRouter();
        createApi.config(driverName);
    }

    public CasiaEsCreate(List<String> ipPorts){
        createApi = ApiRouter.getCreateRouter();
        createApi.config(ipPorts);
    }

    public CasiaEsCreate(List<String> ipPorts,String username,String password){
        createApi = ApiRouter.getCreateRouter();
        createApi.config(ipPorts,username,password);
    }

    /**
     * 设置索引名称及其类型
     * @param indexName
     * @param indexType
     */
    public void setIndexName(String indexName,String indexType) {
        createApi.setIndexName(indexName,indexType);
    }
    /**
     * 创建索引
     * @param indexName 索引名称
     * @param mapping mapping
     * @return
     */
    public Boolean createIndex(String indexName,String mapping){
        return createApi.creatIndex(indexName,mapping);
    }

    /**
     * write data to index
     * @param datas
     * 			数据集
     * @param uniqueKeyName
     * 			数据在索引中的主键名
     */
    public Boolean writeData(List<JSONObject> datas , String uniqueKeyName){
        return createApi.writeData(datas,uniqueKeyName,null);
    }
    /**
     * write data to index
     * @param datas
     * 			数据集
     * @param uniqueKeyName
     * 			数据在索引中的主键名
     * @param bakingName
     * 			类型归属分片,默认为null,指定主键
     */
    public Boolean writeData(List<JSONObject> datas , String uniqueKeyName ,String bakingName){
        return createApi.writeData(datas,uniqueKeyName,bakingName);
    }
    /**
     * insert field to index
     * @param fieldName field name
     * @param map   field parms
     * @return true or false
     */
    public Boolean insertField(String fieldName,Map<String, String> map){
        return createApi.insertField(fieldName,map);
    }
    /**
     * close index
     * @return true or false
     */
    public Boolean closeIndex(){
        return createApi.closeIndex();
    }
    /**
     * open index
     * @return true or false
     */
    public Boolean openIndex(){
        return createApi.openIndex();
    }
    /**
     * flush index
     * @return true or false
     */
    public Boolean flushIndex(){
        return createApi.flushIndex();
    }
    /**
     * refresh index
     * @return true or false
     */
    public Boolean refreshIndex(){
        return createApi.refreshIndex();
    }
}
