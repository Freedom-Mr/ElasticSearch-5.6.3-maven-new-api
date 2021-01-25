package casia.isiteam.api.elasticsearch.controller;

import casia.isiteam.api.toolutil.file.CasiaFileUtil;
import casia.isiteam.api.toolutil.time.CasiaTimeFormat;
import casia.isiteam.api.toolutil.time.CasiaTimeUtil;
import com.alibaba.fastjson.JSONArray;
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
    public void testCreateIndex22() {
        CasiaEsCreate casiaEsCreatec = new CasiaEsCreate("data");
        String mappings = CasiaFileUtil.readAllBytes("mapping/all.json");
        String aa = "think_tank_all,video_info_all,forum_threads_all,instagram_thread_all,twitter_info_all,youtube_info_all,facebook_info_all,mblog_info_all,wechat_info_all,appdata_all,newspaper_all,news_all,blog_all,platform_info_all,news_channel_all";
        String[] indexNames = aa.split(",");
        for(String indexName:indexNames){
            System.out.println(casiaEsCreatec.createIndex(indexName,mappings));
        }
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

        CasiaEsCreate casiaEsCreate = new CasiaEsCreate("data");
        casiaEsCreate.setIndexName("test","test_data");

        Map<String,Object> rss =casiaEsCreate.writeDatas(datas,"id");
        rss.forEach((k,v)->{
            System.out.println(k+"\t"+v);
        });
    }
    public void testInsertField2() {
        CasiaEsCreate casiaEsCreate = new CasiaEsCreate("data");
        Map<String, String> map = new HashMap<>();
        map.put("type", "integer");
//                map.put("analyzer", "standard");
        map.put("index", "not_analyzed");
//                map.put("format", "yyyy-MM-dd HH:mm:ss");
        map.put("store", "true");
        casiaEsCreate.setIndexName("youtube_info_zdr","zdr_caiji");
        boolean rs = casiaEsCreate.insertField("is_contact",map);
    }
    public void testInsertField() {
        CasiaEsCreate casiaEsCreate = new CasiaEsCreate("liangqun");
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

        //预警索引
//        String[] indexName = new String[]{"event_appdata_ref_monitor,event_blog_ref_monitor,event_facebook_info_ref_monitor,event_forum_threads_ref_monitor,event_instagram_thread_ref_monitor,event_mblog_info_ref_monitor,event_news_ref_monitor,event_newspaper_ref_monitor,event_platform_info_ref_monitor,event_twitter_info_ref_monitor,event_video_info_ref_monitor,event_wechat_info_ref_monitor,event_youtube_info_ref_monitor"};
//        String indexType = "monitor_data";

        //专题索引
//        String[] indexName = new String[]{"event_appdata_ref_event,event_blog_ref_event,event_facebook_info_ref_event,event_forum_threads_ref_event,event_instagram_thread_ref_event,event_mblog_info_ref_event,event_news_ref_event,event_newspaper_ref_event,event_platform_info_ref_event,event_twitter_info_ref_event,event_video_info_ref_event,event_wechat_info_ref_event,event_youtube_info_ref_event"};
//        String indexType = "event_data";
        //zdr索引
//        String[] indexName = new String[]{"user_blog_ref_zdr,user_facebook_info_ref_zdr,user_forum_threads_ref_zdr,user_instagram_thread_ref_zdr,user_mblog_info_ref_zdr,user_twitter_info_ref_zdr,user_wechat_info_ref_zdr,user_youtube_info_ref_zdr,user_linkedin_thread_ref_zdr"};
//        String indexType = "zdr_data";

        //大索引
//        String[] indexName = new String[]{"think_tank_all","video_info_all","forum_threads_all","instagram_thread_all","twitter_info_all","youtube_info_all","facebook_info_all","mblog_info_all","wechat_info_all","appdata_all","newspaper_all","news_all","blog_all","platform_info_all"};
//        String indexType = "monitor_caiji_all";

        //预处理索引
//        String[] indexName = new String[]{"video_info_preprocess","forum_threads_preprocess","instagram_thread_preprocess","twitter_info_preprocess","youtube_info_preprocess","facebook_info_preprocess","mblog_info_preprocess","wechat_info_preprocess","appdata_preprocess","newspaper_preprocess","news_preprocess","blog_preprocess","platform_info_preprocess"};
//        String indexType = "monitor_caiji_preprocess";

        //小索引
//        String[] indexName = new String[]{"think_tank_small","video_info_small","forum_threads_small","instagram_thread_small","twitter_info_small","youtube_info_small","facebook_info_small","mblog_info_small","wechat_info_small","appdata_small","newspaper_small","news_small","blog_small","platform_info_small"};
//        String indexType = "monitor_caiji_small";

        //用户信息表
        String[] indexName = new String[]{"event_data_extract_result_v-202011","event_data_extract_result_v-202003","event_data_extract_result_v-202001","event_data_extract_result_v-201912",
        "event_data_extract_result_q-202011","event_data_extract_result_q-202001","event_data_extract_result_q-201912","all_data_v-202011","all_data_v-202003","all_data_v-202001","all_data_v-201912","all_data_q-202011","all_data_q-202001","all_data_q-201912"};
        String indexType = "analysis_data";


//        String[] filenames =new String[]{"topic_list","topic_size","at_list","at_size","communication_list","communication_size"};
//        String[] types =new String[]{"keyword","integer","keyword","integer","keyword","integer"};
        String[] filenames =new String[]{"kngentity_list"};
        String[] types =new String[]{"keyword"};
//        String[] filenames =new String[]{"pretitle"};
//        String[] types =new String[]{"text"};
//        String[] filenames =new String[]{"last_reply_time"};
//        String[] types =new String[]{"date"};
        for(String index:indexName){
            for(int i=0;i<filenames.length;i++){
                Map<String, String> map = new HashMap<>();
                map.put("type", types[i]);
//                map.put("analyzer", "standard");
                map.put("index", "not_analyzed");
//                map.put("format", "yyyy-MM-dd HH:mm:ss");
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

    public void testReIndex() {
        CasiaEsCreate casiaEsCreate = new CasiaEsCreate("beihang");
        Map<String,Object> map = casiaEsCreate.reIndex("event_news_ref_beihang1","event_news_ref_beihang");
        map.forEach((k,v)->{
            System.out.println(k+":"+v);
        });
    }

    public void testCreateIndexAlias() {
        CasiaEsCreate casiaEsCreate = new CasiaEsCreate("data");
        casiaEsCreate.setIndexName("demo_test");
        boolean rs = casiaEsCreate.createIndexAlias("wu");
        System.out.println(rs);
    }


    public void testRoutingAllocation() {
        CasiaEsCreate casiaEsCreate = new CasiaEsCreate("data");
        boolean rs = casiaEsCreate.routingAllocation(true);
        System.out.println(rs);
    }

    public void testRoutingRebalance() {
        CasiaEsCreate casiaEsCreate = new CasiaEsCreate("data");
        boolean rs = casiaEsCreate.routingRebalance(true);
        System.out.println(rs);
    }

    public void testDelayedNodeSettings() {
        CasiaEsCreate casiaEsCreate = new CasiaEsCreate("data");
        boolean rs = casiaEsCreate.delayedNodeSettings("1m");
        System.out.println(rs);
    }
}