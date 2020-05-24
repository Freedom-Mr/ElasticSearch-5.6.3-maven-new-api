package casia.isiteam.api.elasticsearch.controller;

import casia.isiteam.api.elasticsearch.operation.interfaces.ElasticSearchApi;
import casia.isiteam.api.elasticsearch.router.ApiRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * ClassName: CasiaEsDelete
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/4/27
 * Email: zhiyou_wang@foxmail.com
 */
public class CasiaEsDelete {
    private Logger logger = LoggerFactory.getLogger( this.getClass());

    private ElasticSearchApi.DelApi delApi;
    public CasiaEsDelete(String driverName){
        delApi = ApiRouter.getDeleteRouter();
        delApi.config(driverName);
    }

    public CasiaEsDelete(List<String> ipPorts){
        delApi = ApiRouter.getDeleteRouter();
        delApi.config(ipPorts);
    }

    public CasiaEsDelete(List<String> ipPorts,String username,String password){
        delApi = ApiRouter.getDeleteRouter();
        delApi.config(ipPorts,username,password);
    }
    /**
     * 设置索引名称及其类型
     * @param indexName
     * @param indexType
     */
    public void setIndexName(String indexName,String indexType) {
        delApi.setIndexName(indexName,indexType);
    }
    /**
     * 根据索引名删除索引信息
     * @return true or false
     */
    public boolean deleteIndexByName (){
        return delApi.deleteIndexByName();
    }
    /**
     * delete data by _id
     * @param _id  _id
     * @return true or false
     */
    public boolean deleteDataById(String _id){
        return delApi.deleteDataById(_id);
    }
    /**
     * delete data by _ids
     * @param _ids  _ids
     * @return true or false
     */
    public boolean deleteDataByIds(List<String> _ids){
        return delApi.deleteDataByIds(_ids);
    }
    /**
     * delete Scroll by Scroll_ids
     * @param scroll_ids  _ids
     * @return true or false
     */
    public boolean deleteScrollByIds(List<String> scroll_ids){
        return delApi.deleteScrollByIds(scroll_ids);
    }
    /**
     * delete all Scroll id
     * @return true or false
     */
    public boolean deleteScrollByAll(){
        return delApi.deleteScrollByAll();
    }
    /**
     * clear Cache by indexName
     * or clear all Cache
     * @return true or false
     */
    public boolean clearCache(){
        return delApi.clearCache();
    }
}
