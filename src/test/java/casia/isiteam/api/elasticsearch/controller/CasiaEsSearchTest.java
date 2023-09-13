package casia.isiteam.api.elasticsearch.controller;

import casia.isiteam.api.elasticsearch.common.enums.*;
import casia.isiteam.api.elasticsearch.common.vo.field.SortField;
import casia.isiteam.api.elasticsearch.common.vo.field.aggs.*;
import casia.isiteam.api.elasticsearch.common.vo.field.search.geo.GeoQueryInfo;
import casia.isiteam.api.elasticsearch.common.vo.field.search.geo.LonLat;
import casia.isiteam.api.elasticsearch.common.vo.result.AggsInfo;
import casia.isiteam.api.elasticsearch.common.vo.result.LonLatInfo;
import casia.isiteam.api.elasticsearch.common.vo.result.QueryInfo;
import casia.isiteam.api.elasticsearch.common.vo.result.SearchResult;
import casia.isiteam.api.elasticsearch.common.vo.field.search.KeyWordsBuider;
import casia.isiteam.api.elasticsearch.common.vo.field.search.KeywordsCombine;
import casia.isiteam.api.elasticsearch.common.vo.field.RangeField;
import casia.isiteam.api.elasticsearch.controller.api.CasiaEsApi;
import casia.isiteam.api.elasticsearch.util.OutInfo;
import casia.isiteam.api.toolutil.Validator;
import casia.isiteam.api.toolutil.file.CasiaFileUtil;
import casia.isiteam.api.toolutil.time.CasiaTimeFormat;
import casia.isiteam.api.toolutil.time.CasiaTimeUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * ClassName: CasiaEsSearchTest
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/4/29
 * Email: zhiyou_wang@foxmail.com
 */
public class CasiaEsSearchTest extends TestCase {
    private Logger logger = LoggerFactory.getLogger( this.getClass());


    public void testSetAggs() {
        CasiaEsSearch casiaEsSearch = new CasiaEsSearch("small");
        casiaEsSearch.setIndexName("all-s-forum-*", "all");
        casiaEsSearch.setSize(0);
        AggsFieldBuider aggsFieldBuider = new AggsFieldBuider();

        /**** Type ***/
       /* SearchResult searchResult = casiaEsSearch
                .setAggregations(
                        new AggsFieldBuider(
                                new TermInfo("publish_author_md5",2, 0L , SortOrder.DESC ,
                                        new AggsFieldBuider(   new TopData(1 ).setReturnField(FieldOccurs.INCLUDES,"publish_author_name"))
                                )
                        )
                       *//* ,new AggsFieldBuider(
                                new TypeInfo("domain"),
                                new TypeInfo("site","site2")
                        )*//*
                ).executeAggsInfo();*/
//        casiaEsSearch.setQueryKeyWords( new KeywordsCombine( 1,
//                new KeyWordsBuider("content"," 普京批准新国家战略 俄媒：中俄永远做兄弟 与塔利班交火后 超过1000名阿富汗政府军撤入塔吉克斯坦 汾酒·凤凰军机处|黑海对峙罗生门  韩国成功测试潜射导弹 新威慑体系究竟瞄准了谁？",FieldOccurs.INCLUDES, QueriesLevel.Wildcard)
//                new KeyWordsBuider("title","疫苗",FieldOccurs.INCLUDES, QueriesLevel.Keywords)
//                ,new KeyWordsBuider("title","辉瑞",FieldOccurs.INCLUDES, QueriesLevel.Keywords)
//                ,new KeyWordsBuider("title","提升",FieldOccurs.INCLUDES, QueriesLevel.Keywords)
//                ,new KeyWordsBuider("title","授权",FieldOccurs.INCLUDES, QueriesLevel.Keywords)
//                ,new KeyWordsBuider("title","生物",FieldOccurs.INCLUDES, QueriesLevel.Keywords)
//                new KeyWordsBuider("publish_author_md5","fc625bca4f14b77e74323e5e39fe454d",FieldOccurs.INCLUDES, QueriesLevel.Term)
//        ));
        /**** Term ***/
        SearchResult searchResult = casiaEsSearch
                .setQueryKeyWords(
                        new KeywordsCombine(1
                        ,new KeyWordsBuider("source_host","tianya.cn",FieldOccurs.INCLUDES, QueriesLevel.Phrase)
//                        ,new KeyWordsBuider("site_channel_model_name","乌克兰",FieldOccurs.INCLUDES, QueriesLevel.Phrase)
                ))
                .setAggregations(
                        new AggsFieldBuider(
                                new TermInfo("site_channel_model_name#site_channel_model_name",500)
                        )
                        /*, new AggsFieldBuider(
                                new TermInfo("channel_name",10).setSortOrder(SortOrder.DESC)
                                        .setAggsFieldBuider(
                                                new AggsFieldBuider(
                                                        new TopData(1, Arrays.asList(new SortField("pubtime",SortOrder.DESC)))
                                                )
                                        ),
                                new TermInfo("eid",2,"eid2").setSortOrder(SortOrder.DESC)
                        )*/
                ).executeAggsInfo();

        /**** OperationInfo ***/
       /* SearchResult searchResult = casiaEsSearch
                .setAggregations(
                        new AggsFieldBuider(
                                new OperationInfo(OperationLevel.Avg,"eid"),
                                new OperationInfo(OperationLevel.Sum,"eid")
                        ),
                        new AggsFieldBuider().
                                addOperation( new OperationInfo(OperationLevel.Max,"eid")).
                                addType(new TypeInfo("eid")
                        )
                ).executeAggsInfo();*/

        /**** date ***/
      /*  SearchResult searchResult = casiaEsSearch
                .setRange(new RangeField(FieldOccurs.INCLUDES,"pubtime","2022-05-01 00:00:00","2022-05-13 23:59:59"))
                .setAggregations(
                    new AggsFieldBuider(
                            new DateInfo("pubtime","yyyy-MM-dd","1d",0L,"2022-05-01","2022-05-13").
                                    setAggsFieldBuider(
                                            new AggsFieldBuider(
                                                    new DateInfo("pubhms","HH:mm:ss","6h",0L,"00:00:00","07:00:00")
                                            )
                                    )
                    )
                    *//*new AggsFieldBuider(
                            new DateInfo("pubhms","HH:mm:ss","30m",0L,"00:00:00","23:59:59")
                    )*//*
                ).executeQueryInfo();*/

        /**** TopData ***/
       /* SearchResult searchResult = casiaEsSearch
                .setAggregations(
                        new AggsFieldBuider(
                                new TopData(2).setAlias("top_list"),
                                new TopData(2, Arrays.asList(new SortField("pubtime",SortOrder.ASC)))
                        )
                ).executeAggsInfo();*/

        /**** GeoInfo ***/
        /*SearchResult searchResult = casiaEsSearch
                .setAggregations(
                        new AggsFieldBuider(
                                new GeoInfo(GeoLevel.Bounds,"lal"),
                                new GeoInfo(GeoLevel.Centroid,"lal")
                        )
                ).executeAggsInfo();*/

        /**** Price ***/
//        SearchResult searchResult = casiaEsSearch
//                .setAggregations(
//                        new AggsFieldBuider(
//                                new PriceInfo("eid","*-50","50-70","100-*").setAggsFieldBuider(
//                                        new AggsFieldBuider(
//                                                new OperationInfo(OperationLevel.Avg,"eid")
//                                        ).addTopDatas(new TopData(2))
//                                )
//                        )
//                ).executeAggsInfo();

        /**** IP ***/
        /*SearchResult searchResult = casiaEsSearch
                .setAggregations(
                        new AggsFieldBuider(
                                new IpRangeInfo("ip","27.195.96.196-27.195.96.199","27.195.96.199-*","27.195.96.127/23")
                                        .setAggsFieldBuider(
                                            new AggsFieldBuider(
                                                    new DateInfo("pubtime","yyyy-MM","1M",1L)
    //                                            new GridInfo("lal",3)
                                            )
                                ),
                                new IpRangeInfo("ip","27.195.96.196-27.195.96.199","27.195.96.199-*","27.195.96.127/23")
                                        .setAlias("iplal")
                                        .setAggsFieldBuider(
                                                new AggsFieldBuider(
                                                    new GridInfo("lal",3)
                                                )
                                        )
                        )
                ).executeAggsInfo();*/

        /**** Grid ***/
       /* SearchResult searchResult = casiaEsSearch
                .setAggregations(
                        new AggsFieldBuider(
                                new GridInfo("lal",3).
                                        setAggsFieldBuider(
                                            new AggsFieldBuider(
//                                                    new DateInfo("pubtime","yyyy-MM","1M",1L)
                                                    new GeoInfo(GeoLevel.Bounds,"lal")
                                            )
                                        )
                        )
                ).executeAggsInfo();*/

        /**** KeyWords ***/
//        SearchResult searchResult = casiaEsSearch
//                .setAggregations(
//                        new AggsFieldBuider(
//                                new KeywordsCombine(1,"zzzz",
//                                        new KeyWordsBuider("content","北京",FieldOccurs.INCLUDES, QueriesLevel.Phrase),
//                                        new KeyWordsBuider("title","北京",FieldOccurs.INCLUDES, QueriesLevel.Phrase)
//                                ),
//                                new KeywordsCombine(1,
//                                        new KeyWordsBuider("content","上海",FieldOccurs.INCLUDES, QueriesLevel.Phrase),
//                                        new KeyWordsBuider("title","上海",FieldOccurs.INCLUDES, QueriesLevel.Phrase)
//                                )
////                                new KeywordsCombine(2,
////                                        new KeyWordsBuider("domain","ifeng.com",FieldOccurs.INCLUDES, QueriesLevel.Term),
////                                        new KeyWordsBuider().setKeywordsCombines(
////                                                    new KeywordsCombine(1,
////                                                            new KeyWordsBuider("domain","hkbtv.cn",FieldOccurs.INCLUDES, QueriesLevel.Term),
////                                                            new KeyWordsBuider("domain","tt.cn",FieldOccurs.INCLUDES, QueriesLevel.Term)
////                                                    )
////                                            )
////                                ),
////                                new KeywordsCombine(1,
////                                        new KeyWordsBuider("domain","hkbtv.cn",FieldOccurs.INCLUDES, QueriesLevel.Term),
////                                        new KeyWordsBuider("domain","epochtimes.com",FieldOccurs.INCLUDES, QueriesLevel.Term),
////                                        new KeyWordsBuider("lal",new GeoQueryInfo().addBox(
////                                                new LonLat(112.967240F,28.211238F),
////                                                new LonLat(111.967240F,26.211238F)),FieldOccurs.INCLUDES, GeoQueryLevel.Box)
////                                ).setAggsFieldBuider(
////                                        new AggsFieldBuider(
////                                                new DateInfo("pubtime","yyyy-MM","1M",1L).setAlias("pub")
////                                        )
////                                )
//
//                        )
//                ).executeAggsInfo();

        /**** Matrix ***/
        /*SearchResult searchResult = casiaEsSearch
                .setAggregations(
                        new AggsFieldBuider(
                                new TermInfo("eid",3).setAggsFieldBuider(
                                        new AggsFieldBuider(
                                                new MatrixInfo("support_level","id")
                                        )
                                )
                        )
                ).executeAggsInfo();*/

        OutInfo.out(searchResult);
    }

    public void testSetMatchAllQuery() {
        CasiaEsSearch casiaEsSearch = new CasiaEsSearch("web");
        casiaEsSearch.setIndexName("test","test_data");
        List<KeyWordsBuider> list= new ArrayList<>();
        SearchResult searchResult = casiaEsSearch.
                setFrom(0).
                setSize(3).
//                addSort(
//                        new SortField("pubtime", SortOrder.DESC)
//                        new SortField("eid", SortOrder.ASC)
//                ).
//                setRange(
//                        new RangeField(FieldOccurs.INCLUDES,"id",32174657,33677173)
//                        new RangeField(FieldOccurs.EXCLUDES,"pubtime","2020-01-09 07:36:00",null)
//                ).
                setQueryKeyWords(
                        new KeywordsCombine(1,
                                new KeyWordsBuider("site","中国",FieldOccurs.INCLUDES, QueriesLevel.Term)
//                               new KeyWordsBuider("mmsi","\\sad*dsa-?>.(",FieldOccurs.INCLUDES, QueriesLevel.Term),
//                               new KeyWordsBuider("lal",new GeoQueryInfo().addBox(
//                                        new LonLat(112.967240F,28.211238F),
//                                        new LonLat(111.967240F,26.211238F)),FieldOccurs.INCLUDES, GeoQueryLevel.Box)
//                               new KeyWordsBuider("lal",new GeoQueryInfo().addDistance(new LonLat(112.967240F,28.211238F),"100km"),FieldOccurs.INCLUDES, GeoQueryLevel.Distance)
//                               new KeyWordsBuider("lal",new GeoQueryInfo().addBox(new LonLat(112.967240F,28.211238F),new LonLat(111.967240F,26.211238F)),FieldOccurs.INCLUDES, GeoQueryLevel.Box)
                               ,new KeyWordsBuider("lal",new GeoQueryInfo().addDistanceRange(new LonLat(112.967240F,28.211238F),"50km","100km"),FieldOccurs.INCLUDES, GeoQueryLevel.DistanceRange)
                            /*   new KeyWordsBuider("lal",new GeoQueryInfo().addPolygon(
                                       new LonLat(112.967240F,28.211238F),
                                       new LonLat(115.967240F,28.211238F),
                                       new LonLat(116.967240F,30.211238F)
                               ),FieldOccurs.EXCLUDES, GeoQueryLevel.Polygon)*/
//                               new KeyWordsBuider("site","新.*",FieldOccurs.INCLUDES, QueriesLevel.Regexp),
                               ,new KeyWordsBuider(
                                       new KeywordsCombine(1,
                                               new KeyWordsBuider("id","32677740",FieldOccurs.EXCLUDES, QueriesLevel.Term),
                                               new KeyWordsBuider("title","广告",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                                               new KeyWordsBuider(
                                                       new KeywordsCombine(1,new KeyWordsBuider("content","武汉",FieldOccurs.INCLUDES, QueriesLevel.Phrase) )
                                               )
                                       )
                               )
                        )
                ).
                setExistsFilter("content","title").
                setMissingFilter("it").
//                openProfile().
                setMinScore(0.1F).
//                setHighlight(null,null,"id").
                setReturnField("i*","site","pubtime","title").
                setReturnField(true).
//                setReturnField(FieldOccurs.EXCLUDES,"ip").
                executeQueryInfo();

        OutInfo.out(searchResult);

    }
    public void test() {
        CasiaEsApi casiaEsApi = new CasiaEsApi("aliyun");
        casiaEsApi.search().setIndexName("*_small","monitor_caiji_small");
        casiaEsApi.search().addSort(new SortField("pubtime", SortOrder.ASC));
        casiaEsApi.search().setRange(
                new RangeField(FieldOccurs.INCLUDES,
                        "pubtime",
                        null,
                        CasiaTimeUtil.addSubtractDateTime(CasiaTimeUtil.getNowDateTime(), CasiaTimeFormat.YYYY_MM_dd_HH_mm_ss,-1, ChronoUnit.DAYS)
//                        CasiaTimeUtil.getNowDateTime()
                )
        ).setSize(1);
       /* casiaEsApi.search().setQueryKeyWords(
                new KeywordsCombine(1,
//                        new KeyWordsBuider("author","梅新育",FieldOccurs.INCLUDES, QueriesLevel.Term)
                        new KeyWordsBuider("id","2115341614",FieldOccurs.INCLUDES, QueriesLevel.Term)
                )
        );*/

        SearchResult searchResult = casiaEsApi.search().executeQueryInfo();
        OutInfo.out(searchResult);

    }
    public void test2() {
        CasiaEsApi casiaEsApi = new CasiaEsApi("xx5");
        casiaEsApi.search().setIndexName("user_blog_ref,user_youtube_ref,user_wechat_ref,user_threads_ref,user_mblog_ref,user_instagram_ref,user_facebook_blog_ref","zdr_data");
        casiaEsApi.search().addSort(new SortField("pubtime", SortOrder.DESC));
        casiaEsApi.search().setRange(
                new RangeField(FieldOccurs.INCLUDES,
                        "pubtime",
                        CasiaTimeUtil.addSubtractDateTime(CasiaTimeUtil.getNowDateTime(), CasiaTimeFormat.YYYY_MM_dd_HH_mm_ss,-7, ChronoUnit.DAYS),
                        CasiaTimeUtil.getNowDateTime()
                )
        );
        /*casiaEsApi.search().setQueryKeyWords(
                new KeywordsCombine(1,
                        new KeyWordsBuider("content_translation","习",FieldOccurs.INCLUDES, QueriesLevel.Term),
                        new KeyWordsBuider("content","习",FieldOccurs.INCLUDES, QueriesLevel.Term)
                )
        );*/

        String keyword = "习;共产党;共匪;共党";
        String not_keyword = "";
        String[] fields = new String[]{"content","content_translation","title","title_translation"};
        //添加包含关键词
        List<KeyWordsBuider> inkeys = new ArrayList<>();
        if(keyword != null && !keyword.equals("")){
            String[] includekeywords = keyword.split(";");
            for(String mustKwd:includekeywords){
                for(String field :fields){
                    inkeys.add(new KeyWordsBuider(field,mustKwd,FieldOccurs.INCLUDES, QueriesLevel.Term));
                }
            }
        }
        //添加不包含关键词
        List<KeyWordsBuider> exkeys = new ArrayList<>();
        if(not_keyword != null && !not_keyword.equals("")){
            String[] excludeKeywords = not_keyword.split(";");
            for(String notKwd:excludeKeywords){
                for(String field :fields){
                    inkeys.add(new KeyWordsBuider(field,notKwd,FieldOccurs.EXCLUDES, QueriesLevel.Term));
                }
            }
        }
        KeywordsCombine keywordsCombine;
        if( inkeys.size()>0 && exkeys.size()>0 ){
            casiaEsApi.search().setQueryKeyWords(
                    new KeywordsCombine(2,
                            new KeyWordsBuider(
                                    new KeywordsCombine(1,
                                            inkeys
                                    )
                            ),
                            new KeyWordsBuider(
                                    new KeywordsCombine(1,
                                            exkeys
                                    )
                            )
                    )
            );
        }else if ( inkeys.size()>0 && exkeys.size()==0 ){
            casiaEsApi.search().setQueryKeyWords(
                    new KeywordsCombine(1,
                            inkeys
                    )
            );
        }else if ( inkeys.size()==0 && exkeys.size()>0 ){
            casiaEsApi.search().setQueryKeyWords(
                    new KeywordsCombine(1,
                            exkeys
                    )
            );
        }

        SearchResult searchResult = casiaEsApi.search().executeQueryInfo();
        OutInfo.out(searchResult);

    }
    public void test3() {
//        CasiaEsApi casiaEsApi = new CasiaEsApi("headlines");
//        casiaEsApi.search().setIndexName("os-headlines-news-*", "headlines");
        CasiaEsApi casiaEsApi = new CasiaEsApi("person");
        casiaEsApi.search().setIndexName("all-p-*", "person");
        casiaEsApi.search().setSize(1);
        casiaEsApi.search()
                .setQueryKeyWords(
                new KeywordsCombine(1
                        ,new KeyWordsBuider("source_md5","343bf9419e61ec36e9328c44c1569797",FieldOccurs.INCLUDES, QueriesLevel.Phrase)
                )
        );

//        casiaEsApi.search().setAggregations(
//                        new AggsFieldBuider(
//                                new TermInfo("publish_group_id",10)
//                        )
//                );


//        casiaEsApi.search()
//                .setRange(new RangeField(FieldOccurs.INCLUDES,"txt_extraction_info.stance_info.pubtime","2022-11-10 00:00:00","2022-11-14 23:59:59"))
//                .setAggregations(
//                new AggsFieldBuider(
//                        new DateInfo("txt_extraction_info.stance_info.pubtime","yyyy-MM-dd","1d",0L,"2022-11-10","2022-11-14")
//                                .setAggsFieldBuider(
//                                        new AggsFieldBuider(
//                                                new DateInfo("txt_extraction_info.stance_info.pubtime","HH:mm:ss","6h",0L,"00:00:00","07:00:00")
//                                        )
//                                )
//                        new PriceInfo("txt_extraction_info.stance_info.sentiment","*-0","0-1","1-*")
//                 new IpRangeInfo("txt_extraction_info.stance_info.sentiment","27.195.96.196-27.195.96.199","27.195.96.199-*","27.195.96.127/23")
                        /*new AggsFieldBuider(
                                new DateInfo("pubhms","HH:mm:ss","30m",0L,"00:00:00","23:59:59")
                        )*/

//                        new TermInfo("txt_extraction_info.stance_info.sentiment",2, 0L , SortOrder.DESC ,
//                                new AggsFieldBuider(   new TopData(1 ).setReturnField(FieldOccurs.INCLUDES,"publish_author_name"))
//                        )
//                        new TermInfo("it",2).
//                                setAggsFieldBuider(
//                                        new AggsFieldBuider(
//                                                new TopData(2).setAlias("top_list")
//                                        )
//                                )

//                        new TermInfo("txt_extraction_info.stance_info.sentiment",2).setSortOrder(SortOrder.ASC).
//                                setAggsFieldBuider(
//                                    new AggsFieldBuider(
//                                            new TopData(2).setAlias("top_list")
//                                    )
//                        )
//                            new TermInfo("image_extraction_info.image_to_category.info.names")
//                           ,new TypeInfo("txt_extraction_info.stance_info.stance")
//                        new OperationInfo(OperationLevel.Avg,"txt_extraction_info.stance_info.sentiment"),
//                        new TopData(2).setAlias("top_list")
//                        new TopData(2, Arrays.asList(new SortField("pubtime",SortOrder.ASC)))
//                )
//     )  ;
//        casiaEsApi.search().setRange(new RangeField(FieldOccurs.INCLUDES,"txt_extraction_info.stance_info.sentiment",0,1));
//        casiaEsApi.search().addSort(new SortField("txt_extraction_info.stance_info.sentiment", SortOrder.DESC));
//        casiaEsApi.search().setExistsFilter("txt_extraction_info.stance_info.sentiment");
//        casiaEsApi.search().setReturnField("txt_extraction_info");

        SearchResult searchResult = casiaEsApi.search().executeQueryInfo();
        OutInfo.out(searchResult);
    };
}