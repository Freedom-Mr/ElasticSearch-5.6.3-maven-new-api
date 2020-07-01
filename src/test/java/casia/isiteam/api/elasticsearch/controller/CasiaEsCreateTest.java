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
        CasiaEsCreate casiaEsCreate = new CasiaEsCreate("aliyun");
        // user_youtube_info_ref_zdr user_wechat_info_ref_zdr user_twitter_info_ref_zdr user_mblog_info_ref_zdr user_linkedin_thread_ref_zdr
        // user_instagram_thread_ref_zdr user_forum_threads_ref_zdr user_facebook_info_ref_zdr user_blog_ref_zdr
        // zdr_data

        //event_appdata_ref_monitor  event_blog_ref_monitor  event_facebook_info_ref_monitor  event_forum_threads_ref_monitor
        //event_instagram_thread_ref_monitor  event_mblog_info_ref_monitor event_news_ref_monitor  event_newspaper_ref_monitor
        //event_platform_info_ref_monitor  event_twitter_info_ref_monitor  event_video_info_ref_monitor  event_wechat_info_ref_monitor
        //event_youtube_info_ref_monitor
        //monitor_data

        //event_appdata_ref_event  event_blog_ref_event  event_facebook_info_ref_event  event_forum_threads_ref_event  event_instagram_thread_ref_event
        //event_mblog_info_ref_event  event_news_ref_event  event_newspaper_ref_event  event_platform_info_ref_event  event_twitter_info_ref_event
        //event_video_info_ref_event  event_wechat_info_ref_event  event_youtube_info_ref_event
        //event_data

        String[] indexName = new String[]{"event_appdata_ref_monitor,event_blog_ref_monitor,event_facebook_info_ref_monitor,event_forum_threads_ref_monitor,event_instagram_thread_ref_monitor,event_mblog_info_ref_monitor,event_news_ref_monitor,event_newspaper_ref_monitor,event_platform_info_ref_monitor,event_twitter_info_ref_monitor,event_video_info_ref_monitor,event_wechat_info_ref_monitor,event_youtube_info_ref_monitor"};
        String indexType = "monitor_data";
        String[] filenames =new String[]{"topic_list","topic_size","at_list","at_size","communication_list","communication_size"};
        String[] types =new String[]{"keyword","integer","keyword","integer","keyword","integer"};
        for(String index:indexName){
            for(int i=0;i<filenames.length;i++){
                Map<String, String> map = new HashMap<>();
                map.put("type", types[i]);
                map.put("index", "not_analyzed");
                map.put("store", "true");
                casiaEsCreate.setIndexName(index,indexType);
                boolean rs = casiaEsCreate.insertField(filenames[i],map);
                System.out.println(index+"\t"+indexType+"\t"+rs);
            }
        }
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