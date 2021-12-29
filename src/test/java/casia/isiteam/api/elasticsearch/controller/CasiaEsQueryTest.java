package casia.isiteam.api.elasticsearch.controller;

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
    CasiaEsQuery casiaEsQuery = new CasiaEsQuery("data");
    public void testQueryIndexByName() {
<<<<<<< Updated upstream
        JSONObject info = casiaEsQuery.queryIndexByName("test");
=======
        CasiaEsQuery casiaEsQuery = new CasiaEsQuery("all");
        JSONObject info = casiaEsQuery.queryIndexByName("os-headlines-news-2021-11");
>>>>>>> Stashed changes
        System.out.println(info);
    }

    public void testQueryIndexNames() {
<<<<<<< Updated upstream
        casiaEsQuery.queryIndexNames().forEach(System.out::println);
=======
        CasiaEsQuery casiaEsQuery = new CasiaEsQuery("all");
        casiaEsQuery.queryIndexNames().forEach(System.out::println);

//        CasiaEsQuery casiaEsQuery2 = new CasiaEsQuery("cph");
//        casiaEsQuery2.queryIndexNames().forEach(System.out::println);
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
>>>>>>> Stashed changes
    }
}