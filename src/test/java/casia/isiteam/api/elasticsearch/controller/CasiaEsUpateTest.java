package casia.isiteam.api.elasticsearch.controller;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: CasiaEsUpateTest
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/11
 * Email: zhiyou_wang@foxmail.com
 */
public class CasiaEsUpateTest extends TestCase {
    CasiaEsUpate casiaEsQuery = new CasiaEsUpate("data");

    public void testUpdateParameterById() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("lal", "61.12,61.01");

        casiaEsQuery.setIndexName("test","test_data");
        boolean boo = casiaEsQuery.updateParameterById("1",map);
        System.out.println(boo);
    }
}