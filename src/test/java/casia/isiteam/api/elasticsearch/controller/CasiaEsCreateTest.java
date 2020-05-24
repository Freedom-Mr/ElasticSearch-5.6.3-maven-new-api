package casia.isiteam.api.elasticsearch.controller;

import casia.isiteam.api.toolutil.file.CasiaFileUtil;
import com.alibaba.fastjson.JSONObject;
import junit.framework.TestCase;

import java.nio.charset.StandardCharsets;
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
    CasiaEsCreate casiaEsCreate = new CasiaEsCreate("data");
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
        boolean rs = casiaEsCreate.writeData(datas,"id",null);
        System.out.println(rs);
    }

    public void testInsertField() {
        Map<String, String> map = new HashMap<>();
        map.put("type", "keyword");
        map.put("index", "not_analyzed");
        map.put("store", "true");
        map.put("normalizer", "my_normalizer");

        casiaEsCreate.setIndexName("test","test_data");
        boolean rs = casiaEsCreate.insertField("uname",map);

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