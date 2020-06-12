package casia.isiteam.api.elasticsearch.controller;

import casia.isiteam.api.elasticsearch.common.vo.result.SearchResult;
import casia.isiteam.api.elasticsearch.operation.interfaces.ElasticSearchApi;
import casia.isiteam.api.elasticsearch.router.ApiRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * ClassName: CasiaEsSql
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/6/12
 * Email: zhiyou_wang@foxmail.com
 */
public class CasiaEsSql {
    private Logger logger = LoggerFactory.getLogger( this.getClass());
    private ElasticSearchApi.SqlApi sqlApi;
    public CasiaEsSql(String driverName){
        sqlApi = ApiRouter.getSqlRouter();
        sqlApi.config(driverName);
    }
    public CasiaEsSql(List<String> ipPorts){
        sqlApi = ApiRouter.getSqlRouter();
        sqlApi.config(ipPorts);
    }
    public CasiaEsSql(List<String> ipPorts,String username,String password){
        sqlApi = ApiRouter.getSqlRouter();
        sqlApi.config(ipPorts,username,password);
    }

    /**
     * set query sql
     * select * from test limit 10
     * @param sql
     */
    public void setQuerySql(String sql) {
        sqlApi.setQuerySql(sql);
    }
    /**
     * execute query sql
     * return SearchResult
     */
    public SearchResult executeQueryInfo() {
        return sqlApi.executeQueryInfo();
    }
}
