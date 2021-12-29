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
<<<<<<< Updated upstream
=======
        CasiaEsUpate casiaEsQuery = new CasiaEsUpate("os");

>>>>>>> Stashed changes
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("lal", "61.12,61.01");

<<<<<<< Updated upstream
        casiaEsQuery.setIndexName("test","test_data");
        boolean boo = casiaEsQuery.updateParameterById("1",map);
        System.out.println(boo);
    }
=======

//        map.put("keywords", new String[]{"正月"});
//        map.put("tel_no", new String[]{"13022150240"});
//        map.put("email", new String[]{"zhangping@foxmail.com"});
//        map.put("numbers", new String[]{"165542854"});

//        casiaEsQuery.setIndexName("demo_test","test_data");
        casiaEsQuery.setIndexName("os-headlines-news-2021-10","headlines");
//        casiaEsQuery.setIndexName("event_data_extract_result_v-202001","analysis_data");
        JSONObject json = new JSONObject();
        Set<String> a = new HashSet<>();
        a.add("杭州");
        a.add("上海");
//        map.put("area_info",a);
        map.put("play_number",1);
//        map.put("title",1);
        json.put("play_number",1);
        boolean boo = casiaEsQuery.updateParameterById("23a321101e6178098e2be13d54e2449a",json);
//        boolean boo = casiaEsQuery.updateParameterById("55f4df883637844f637310f429b5d17a",map);
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
        CasiaEsUpate casiaEsUpate = new CasiaEsUpate("jl");
        casiaEsUpate.setIndexName("m-all-forum-2021-09","monitor_all");
        boolean rs = casiaEsUpate.updateNumberOfReplicas(1);
        System.out.println(rs);
    }

    public void testAllocateShardRoute() {
        CasiaEsUpate casiaEsUpate = new CasiaEsUpate("jl");
        casiaEsUpate.setIndexName("m-all-forum-2021-09","monitor_all");

        boolean rs = casiaEsUpate.allocateShardRoute(9,"238-232");
        System.out.println(rs);
    }

>>>>>>> Stashed changes
}