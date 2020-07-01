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
    CasiaEsUpate casiaEsQuery = new CasiaEsUpate("liangqun");

    public void testUpdateParameterById() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("content", "投资宝周一兑付两千万呀。那边联系方式电话13022150240，邮箱zhangping@foxmail.com，QQ：165542854");
        map.put("tel_no", new String[]{"13022150240"});
        map.put("email", new String[]{"zhangping@foxmail.com"});
        map.put("numbers", new String[]{"165542854"});

        casiaEsQuery.setIndexName("event_data_extract_result_v-202001","analysis_data");
//        casiaEsQuery.setIndexName("all_data_v-202001","analysis_data");
        boolean boo = casiaEsQuery.updateParameterById("81983e5afae666b3748a280baf9ccb0e",map);
        System.out.println(boo);
    }
}