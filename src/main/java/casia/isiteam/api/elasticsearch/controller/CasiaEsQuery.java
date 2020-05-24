package casia.isiteam.api.elasticsearch.controller;

import casia.isiteam.api.elasticsearch.operation.interfaces.ElasticSearchApi;
import casia.isiteam.api.elasticsearch.router.ApiRouter;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
     * 查询索引信息根据索引名
     * @param indexName 索引名称
     * @return JSONObject 索引JSON结构
     */
    public JSONObject queryIndexByName (String indexName){
        return queryApi.queryIndexByName(indexName);
    }
    /**
     * 查询索引名列表
     * @return  List<String>  索引名集合
     */
    public List<String> queryIndexNames (){
        return queryApi.queryIndexNames();
    }

}
