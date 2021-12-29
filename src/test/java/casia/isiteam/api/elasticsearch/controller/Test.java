package casia.isiteam.api.elasticsearch.controller;

import casia.isiteam.api.elasticsearch.common.enums.FieldOccurs;
import casia.isiteam.api.elasticsearch.common.enums.SortOrder;
import casia.isiteam.api.elasticsearch.common.vo.field.RangeField;
import casia.isiteam.api.elasticsearch.common.vo.field.SortField;
import casia.isiteam.api.elasticsearch.common.vo.field.aggs.AggsFieldBuider;
import casia.isiteam.api.elasticsearch.common.vo.field.aggs.TermInfo;
import casia.isiteam.api.elasticsearch.common.vo.field.aggs.TopData;
import casia.isiteam.api.elasticsearch.common.vo.result.SearchResult;
import casia.isiteam.api.elasticsearch.controller.api.CasiaEsApi;
import casia.isiteam.api.elasticsearch.util.OutInfo;
import casia.isiteam.api.toolutil.file.CasiaFileUtil;
import com.alibaba.fastjson.JSONObject;
import junit.framework.TestCase;

import java.nio.charset.StandardCharsets;
import java.util.*;
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
//        boolean rs = casiaEsApi.create().insertField("user_md5",map);

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
//        System.out.println(rs);



        CasiaEsSearch casiaEsSearch = new CasiaEsSearch("all");
        casiaEsSearch.reset();
        casiaEsSearch.setIndexName("os-headlines-news-2021-12*","headlines");
//        casiaEsSearch.addSort(new SortField("pubtime",SortOrder.DESC));
//        casiaEsSearch.setRange(new RangeField(FieldOccurs.INCLUDES,"tendency_level",1,3).setIncludeLower(false).setIncludeUpper(true)).setReturnField("tendency_level");
        SortField sortField = new SortField("pubtime",SortOrder.DESC);
        casiaEsSearch.setAggregations(
                new AggsFieldBuider(
                        new TermInfo("site_channel_model_name",500, 1L , SortOrder.DESC ,
                                new AggsFieldBuider(
                                        new TopData(1, Arrays.asList(sortField)).setReturnField(FieldOccurs.INCLUDES,"pubtime")
                                )
                        )
                )
        ).setSize(0);
        SearchResult searchResult = casiaEsSearch.executeQueryInfo();

        OutInfo.out(searchResult);
    }
}
