package casia.isiteam.api.elasticsearch.router;

import casia.isiteam.api.elasticsearch.operation.interfaces.ElasticSearchApi;
import casia.isiteam.api.elasticsearch.operation.service.create.CreateServer;
import casia.isiteam.api.elasticsearch.operation.service.delete.DeleteServer;
import casia.isiteam.api.elasticsearch.operation.service.query.QueryServer;
import casia.isiteam.api.elasticsearch.operation.service.search.SearchServer;
import casia.isiteam.api.elasticsearch.operation.service.sql.SqlServer;
import casia.isiteam.api.elasticsearch.operation.service.update.UpdateServer;


/**
 * ClassName: ApiRouter
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/4/27
 * Email: zhiyou_wang@foxmail.com
 */
public class ApiRouter extends Routers{

    public static ElasticSearchApi.CreateApi getCreateRouter() {
        return createRouter = new CreateServer();
    }

    public static void setCreateRouter(ElasticSearchApi.CreateApi createRouter) {
        createRouter = createRouter;
    }

    public static ElasticSearchApi.DelApi getDeleteRouter() {
        return deleteRouter = new DeleteServer();
    }

    public static void setDeleteRouter(ElasticSearchApi.DelApi deleteRouter) {
        deleteRouter = deleteRouter;
    }

    public static ElasticSearchApi.UpadeApi getUpdateRouter() {
        return updateRouter = new UpdateServer();
    }

    public static void setUpdateRouter(ElasticSearchApi.UpadeApi updateRouter) {
        Routers.updateRouter = updateRouter;
    }

    public static ElasticSearchApi.SearchApi getSearchRouter() {
        return searchRouter = new SearchServer();
    }

    public static void setSearchRouter(ElasticSearchApi.SearchApi searchRouter) {
        Routers.searchRouter = searchRouter;
    }
    public static ElasticSearchApi.QueryApi getQueryRouter() {
        return queryRouter = new QueryServer();
    }

    public static void setQueryRouter(ElasticSearchApi.QueryApi queryRouter) {
        Routers.queryRouter = queryRouter;
    }

    public static ElasticSearchApi.SqlApi getSqlRouter() {
        return sqlRouter = new SqlServer();
    }

    public static void setSqlRouter(ElasticSearchApi.SqlApi sqlRouter) {
        ApiRouter.sqlRouter = sqlRouter;
    }
}
