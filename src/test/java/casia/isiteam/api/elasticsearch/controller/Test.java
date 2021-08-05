package casia.isiteam.api.elasticsearch.controller;

import casia.isiteam.api.elasticsearch.controller.api.CasiaEsApi;
import casia.isiteam.api.toolutil.file.CasiaFileUtil;
import com.alibaba.fastjson.JSONObject;
import junit.framework.TestCase;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: Test
 * @Description: unknown
 * @Vsersion: 1.0
 * <p>
 * Created by casia.wangzhiyou on 2021/5/18
 * Email: zhiyou_wang@foxmail.com
 */
public class Test extends TestCase {

    public void test() {
        CasiaEsApi casiaEsApi= new CasiaEsApi("os1");
        casiaEsApi.create().setIndexName("toutiao-*","monitor_all");

        Map<String, String> map = new HashMap<>();
//        map.put("type", "integer");
        map.put("type", "keyword");
//                map.put("analyzer", "standard");
        map.put("index", "not_analyzed");
//                map.put("format", "yyyy-MM-dd HH:mm:ss");
//        map.put("store", "true");
        boolean rs = casiaEsApi.create().insertField("user_md5",map);

//        String mapping = CasiaFileUtil.readAllBytes("mapping/all.json");
//        boolean rs = casiaEsApi.create().createIndex("test",mapping);

//        List<String> list = CasiaFileUtil.readAllLines("datas/test_data_list.d", StandardCharsets.UTF_8);
//        List<JSONObject> datas = list.stream().map(x -> JSONObject.parseObject(x)).collect( Collectors.toList());
//        casiaEsApi.create().setIndexName("test","all");
//        List<JSONObject> c= new ArrayList<>();
//        c.add(datas.get(0));
//        Map<String,Object> rss =casiaEsApi.create().writeDatas( c,"id");
//        rss.forEach((k,v)->{
//            System.out.println(k+"\t"+v);
//        });

//        casiaEsApi.delete().setIndexName("test");
//        boolean rs = casiaEsApi.delete().deleteIndexByName();
        System.out.println(rs);
    }
}
