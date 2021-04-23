package casia.isiteam.api.elasticsearch.controller;

import casia.isiteam.api.elasticsearch.controller.api.CasiaEsApi;
import com.alibaba.fastjson.JSONObject;
import junit.framework.TestCase;

/**
 * ClassName: CasiaEsQueryTest
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/4/29
 * Email: zhiyou_wang@foxmail.com
 */
public class CasiaEsQueryTest extends TestCase {

    public void testQueryIndexByName() {
        CasiaEsQuery casiaEsQuery = new CasiaEsQuery("data");
        JSONObject info = casiaEsQuery.queryIndexByName("wechat_users");
        System.out.println(info);
    }

    public void testQueryIndexNames() {
        CasiaEsQuery casiaEsQuery = new CasiaEsQuery("cph");
        casiaEsQuery.queryIndexNames().forEach(System.out::println);

        CasiaEsQuery casiaEsQuery2 = new CasiaEsQuery("cph");
        casiaEsQuery2.queryIndexNames().forEach(System.out::println);
    }

    public void testQueryAlias() {
        CasiaEsApi casiaEsApi = new CasiaEsApi("data");

        //无参数
        casiaEsApi.query().queryAlias().forEach((k,v)->{
            System.out.println(k+":"+v);
        });

        //通配符
        casiaEsApi.query().queryAlias("c*").forEach((k,v)->{
            System.out.println(k+":"+v);
        });

        //索引名称
        casiaEsApi.query().setIndexName("demo_test");
        casiaEsApi.query().queryAlias("s*").forEach((k,v)->{
            System.out.println(k+":"+v);
        });
    }

    public void testQueryTask() {
        CasiaEsApi casiaEsApi = new CasiaEsApi("data");

        //无参数
        JSONObject jsonObject =casiaEsApi.query().queryTask("g6sjsH8eT-6VKFxrHzjepQ:1094326");
        System.out.println(jsonObject);
    }
}