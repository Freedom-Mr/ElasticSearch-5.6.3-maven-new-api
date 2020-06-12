package casia.isiteam.api.elasticsearch.router;

import casia.isiteam.api.elasticsearch.operation.interfaces.ElasticSearchApi;

/**
 * ClassName: Routers
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/4/28
 * Email: zhiyou_wang@foxmail.com
 */
public class Routers {
    protected static ElasticSearchApi.CreateApi createRouter;
    protected static ElasticSearchApi.QueryApi queryRouter;
    protected static ElasticSearchApi.UpadeApi updateRouter;
    protected static ElasticSearchApi.SearchApi searchRouter;
    protected static ElasticSearchApi.DelApi deleteRouter;
    protected static ElasticSearchApi.SqlApi sqlRouter;
}
