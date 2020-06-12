package casia.isiteam.api.elasticsearch.operation.service.sql;

import casia.isiteam.api.elasticsearch.common.vo.result.SearchResult;
import casia.isiteam.api.elasticsearch.operation.interfaces.ElasticSearchApi;
import casia.isiteam.api.elasticsearch.util.ExecuteResult;
import casia.isiteam.api.elasticsearch.util.LogUtil;
import casia.isiteam.api.http.controller.CasiaHttpUtil;
import casia.isiteam.api.toolutil.Validator;
import casia.isiteam.api.toolutil.escape.CasiaEscapeUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName: SqlServer
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/6/12
 * Email: zhiyou_wang@foxmail.com
 */
public class SqlServer extends ElasticSearchApi implements ElasticSearchApi.SqlApi {
    private Logger logger = LoggerFactory.getLogger( this.getClass());


    /**
     *  set query sql
     * @param sql
     */
    public void setQuerySql(String sql){
        if( !Validator.check(sql) ){
            logger.warn(LogUtil.compositionLogEmpty("parameter sql "));
            return;
        }
        indexSearchBuilder.setThreads(sql);
    }

    /**
     * execute  query by sql
     */
    @Override
    public SearchResult executeQueryInfo() {
        if( !Validator.check( indexSearchBuilder.getThreads() ) ){
            logger.debug(LogUtil.compositionLogEmpty("query sql parms"));
            return null;
        }
        String curl =curlSymbol(curl(indexParmsStatus.getUrl(),indexParmsStatus.getIndexName(),indexParmsStatus.getIndexType(),_SQL),QUESTION,
                SQL+EQUAL+CasiaEscapeUtil.urlEscape(indexSearchBuilder.getThreads().getString(_SQL)));
        logger.debug(LogUtil.compositionLogCurl(indexSearchBuilder.getThreads().getString(_SQL)) );
        String resultStr = new CasiaHttpUtil().get(curl,indexParmsStatus.getHeards() );
        return ExecuteResult.executeSqlAggsResult(o(resultStr));
    }

}
