package casia.isiteam.api.elasticsearch.controller;

import casia.isiteam.api.elasticsearch.common.enums.FieldOccurs;
import casia.isiteam.api.elasticsearch.common.enums.QueriesLevel;
import casia.isiteam.api.elasticsearch.common.vo.field.search.KeyWordsBuider;
import casia.isiteam.api.elasticsearch.common.vo.field.search.KeywordsCombine;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * ClassName: CasiaEsUpateTest
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/11
 * Email: zhiyou_wang@foxmail.com
 */
public class CasiaEsUpateTest extends TestCase {


    public void testUpdateParameterById() {
        CasiaEsUpate casiaEsQuery = new CasiaEsUpate("os1");

        Map<String, Object> map = new HashMap<String, Object>();


//        map.put("keywords", new String[]{"正月"});
//        map.put("tel_no", new String[]{"13022150240"});
//        map.put("email", new String[]{"zhangping@foxmail.com"});
//        map.put("numbers", new String[]{"165542854"});

//        casiaEsQuery.setIndexName("demo_test","test_data");
        casiaEsQuery.setIndexName("toutiao-news-2021-07","toutiao");
//        casiaEsQuery.setIndexName("event_data_extract_result_v-202001","analysis_data");
        JSONObject json = new JSONObject();
        Set<String> a = new HashSet<>();
        a.add("杭州");
        a.add("上海");
//        map.put("area_info",a);
        map.put("eid",1);
//        map.put("title",1);
        json.put("area_info",a);
//        boolean boo = casiaEsQuery.updateParameterById("9",json);
        boolean boo = casiaEsQuery.updateParameterById("425128",map);
        System.out.println(boo);
    }

    public void testUpdateParameterByQuery() {
        CasiaEsUpate casiaEsQuery = new CasiaEsUpate("data");
        casiaEsQuery.setIndexName("demo_test","test_data");
        casiaEsQuery.setQueryKeyWords(
            new KeywordsCombine( 1,
                new KeyWordsBuider("id","32669280", FieldOccurs.INCLUDES, QueriesLevel.Term)
            )
        );
        casiaEsQuery.setExistsFilter("title");

        Map<String,Object> rs = casiaEsQuery.updateParameterByQuery("eid",14);
        rs.forEach((k,v)->{
            System.out.println(k+"\t"+v);
        });
    }

    public void testMoveShardRoute() {
        CasiaEsUpate casiaEsUpate = new CasiaEsUpate("data");
        casiaEsUpate.setIndexName("demo_test","test_data");
        boolean rs = casiaEsUpate.moveShardRoute(0,"node-1","node-2");
        System.out.println(rs);
    }

    public void testUpdateNumberOfReplicas() {
        CasiaEsUpate casiaEsUpate = new CasiaEsUpate("data");
        casiaEsUpate.setIndexName("demo_test","test_data");
        boolean rs = casiaEsUpate.updateNumberOfReplicas(0);
        System.out.println(rs);
    }
}