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
        JSONObject info = casiaEsQuery.queryIndexByName("test");
        System.out.println(info);
    }

    public void testQueryIndexNames() {
        casiaEsQuery.queryIndexNames().forEach(System.out::println);
    }
}