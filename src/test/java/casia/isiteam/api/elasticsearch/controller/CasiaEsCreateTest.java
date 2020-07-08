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
        System.out.println(casiaEsCreate.createIndex("a",mapping));
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
        String[] indexName = new String[]{"think_tank_small","video_info_small","forum_threads_small","instagram_thread_small","twitter_info_small","youtube_info_small","facebook_info_small","mblog_info_small","wechat_info_small","appdata_small","newspaper_small","news_small","blog_small","platform_info_small"};
        String indexType = "monitor_caiji_small";

//        String[] filenames =new String[]{"topic_list","topic_size","at_list","at_size","communication_list","communication_size"};
//        String[] types =new String[]{"keyword","integer","keyword","integer","keyword","integer"};
        String[] filenames =new String[]{"pic_info","keywords_size","not_analyzed","mid","content_url","last_reply_author"};
        String[] types =new String[]{"text","text","text","long","keyword","keyword"};
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
        CasiaEsCreate casiaEsCreate = new CasiaEsCreate("data");
        Map<String,Object> map = casiaEsCreate.reIndex("demo_test","demo_test2");
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


}