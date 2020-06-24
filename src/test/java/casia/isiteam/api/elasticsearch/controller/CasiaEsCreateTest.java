package casia.isiteam.api.elasticsearch.controller;

import casia.isiteam.api.toolutil.file.CasiaFileUtil;
import casia.isiteam.api.toolutil.time.CasiaTimeFormat;
import casia.isiteam.api.toolutil.time.CasiaTimeUtil;
import com.alibaba.fastjson.JSONObject;
import junit.framework.TestCase;

import java.nio.charset.StandardCharsets;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ClassName: CasiaEsCreateTest
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/4/28
 * Email: zhiyou_wang@foxmail.com
 */
public class CasiaEsCreateTest extends TestCase {
    CasiaEsCreate casiaEsCreate = new CasiaEsCreate("all");
    String mapping = CasiaFileUtil.readAllBytes("mapping/test_mapping.txt");

    /**
     * 创建索引
     */
    public void testCreateIndex() {
        System.out.println(casiaEsCreate.createIndex("test",mapping));
    }

    /**
     * 创建索引
     */
    public void testCreateIndex2() {
        CasiaEsCreate casiaEsCreates = new CasiaEsCreate(Arrays.asList("192.168.5.103:9200"));
        System.out.println(casiaEsCreates.createIndex("test",mapping));
    }

    /**
     * 写入数据
     */
    public void testWriteData() {
        List<String> list = CasiaFileUtil.readAllLines("datas/test_data_list.d", StandardCharsets.UTF_8);
        List<JSONObject> datas = list.stream().map(x -> JSONObject.parseObject(x)).collect( Collectors.toList());

        casiaEsCreate.setIndexName("test","test_data");

//        boolean rs =  casiaEsCreate.writeData(datas,"id",null);
//        System.out.println(rs);

        Map<String,Object> rss =casiaEsCreate.writeDatas(datas,"id");
        rss.forEach((k,v)->{
            System.out.println(k+"\t"+v);
        });
    }

    public void testInsertField() {
        Map<String, String> map = new HashMap<>();
        map.put("type", "geo_point");
        map.put("index", "not_analyzed");
        map.put("store", "true");

        casiaEsCreate.setIndexName("statellite_info","graph");
        boolean rs = casiaEsCreate.insertField("location_point",map);

        System.out.println(rs);
    }

    public void testCloseIndex() {
        casiaEsCreate.setIndexName("test","test_data");
        boolean rs = casiaEsCreate.closeIndex();
        System.out.println(rs);
    }

    public void testOpenIndex() {
        casiaEsCreate.setIndexName("test","test_data");
        boolean rs = casiaEsCreate.openIndex();
        System.out.println(rs);
    }

    public void testFlushIndex() {
        casiaEsCreate.setIndexName("test","test_data");
        boolean rs = casiaEsCreate.flushIndex();
        System.out.println(rs);
    }

    public void testRefreshIndex() {
        casiaEsCreate.setIndexName("test","test_data");
        boolean rs = casiaEsCreate.refreshIndex();
        System.out.println(rs);
    }
}