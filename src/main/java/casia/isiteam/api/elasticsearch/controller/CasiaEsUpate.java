package casia.isiteam.api.elasticsearch.controller;

import casia.isiteam.api.elasticsearch.operation.interfaces.ElasticSearchApi;
import casia.isiteam.api.elasticsearch.router.ApiRouter;
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
}
