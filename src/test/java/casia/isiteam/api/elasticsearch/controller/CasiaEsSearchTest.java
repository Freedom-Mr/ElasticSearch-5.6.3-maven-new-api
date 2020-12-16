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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        CasiaEsSearch casiaEsSearch = new CasiaEsSearch("web");
        casiaEsSearch.setIndexName("test","test_data");
        casiaEsSearch.setSize(0);
        AggsFieldBuider aggsFieldBuider = new AggsFieldBuider();

        /**** Type ***/
        /*SearchResult searchResult = casiaEsSearch
                .setAggregations(
                        new AggsFieldBuider(
                                new TypeInfo("site"),
                                new TypeInfo("eid").setPrecision(1000)
                        ),
                        new AggsFieldBuider(
                                new TypeInfo("domain"),
                                new TypeInfo("site","site2")
                        )
                ).executeAggsInfo();*/

        /**** Term ***/
        /*SearchResult searchResult = casiaEsSearch
                .setAggregations(
                        new AggsFieldBuider(
                                new TermInfo("domain",2).setAggsFieldBuider(
                                        new AggsFieldBuider(
                                                new TypeInfo("site"),
                                                new TypeInfo("eid")
                                        )
                                ),
                                new TermInfo("site",2)
                        ),
                        new AggsFieldBuider(
                                new TermInfo("eid",2).setSortOrder(SortOrder.ASC),
                                new TermInfo("eid",2,"eid2").setSortOrder(SortOrder.DESC)
                        )
                ).executeAggsInfo();*/

        /**** OperationInfo ***/
        /*SearchResult searchResult = casiaEsSearch
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
        SearchResult searchResult = casiaEsSearch.
                setRange(new RangeField(FieldOccurs.INCLUDES,"pubtime","2020-05-06 00:00:00","2020-06-06 00:00:00"))
                .setAggregations(
                    new AggsFieldBuider(
                            new DateInfo("pubtime","yyyy-MM-dd","7d",0L,"2020-05-06","2020-06-06").
                                    setAggsFieldBuider(
                                            new AggsFieldBuider(
                                                    new TypeInfo("eid")
                                            )
                                    )
                    ),
                    new AggsFieldBuider(
                            new DateInfo("addtime","yyyy-MM-dd","7d",0L,"2020-05-06","2020-06-06")
                    )
                ).executeQueryInfo();

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
        /*SearchResult searchResult = casiaEsSearch
                .setAggregations(
                        new AggsFieldBuider(
                                new KeywordsCombine(2,
                                        new KeyWordsBuider("domain","ifeng.com",FieldOccurs.INCLUDES, QueriesLevel.Term),
                                        new KeyWordsBuider().setKeywordsCombines(
                                                    new KeywordsCombine(1,
                                                            new KeyWordsBuider("domain","hkbtv.cn",FieldOccurs.INCLUDES, QueriesLevel.Term),
                                                            new KeyWordsBuider("domain","tt.cn",FieldOccurs.INCLUDES, QueriesLevel.Term)
                                                    )
                                            )
                                ),
                                new KeywordsCombine(1,
                                        new KeyWordsBuider("domain","hkbtv.cn",FieldOccurs.INCLUDES, QueriesLevel.Term),
                                        new KeyWordsBuider("domain","epochtimes.com",FieldOccurs.INCLUDES, QueriesLevel.Term),
                                        new KeyWordsBuider("lal",new GeoQueryInfo().addBox(
                                                new LonLat(112.967240F,28.211238F),
                                                new LonLat(111.967240F,26.211238F)),FieldOccurs.INCLUDES, GeoQueryLevel.Box)
                                ).setAggsFieldBuider(
                                        new AggsFieldBuider(
                                                new DateInfo("pubtime","yyyy-MM","1M",1L).setAlias("pub")
                                        )
                                )
                        )
                ).executeAggsInfo();*/

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
//                               new KeyWordsBuider("lal",new GeoQueryInfo().addDistanceRange(new LonLat(112.967240F,28.211238F),"50km","100km"),FieldOccurs.INCLUDES, GeoQueryLevel.DistanceRange)
                            /*   new KeyWordsBuider("lal",new GeoQueryInfo().addPolygon(
                                       new LonLat(112.967240F,28.211238F),
                                       new LonLat(115.967240F,28.211238F),
                                       new LonLat(116.967240F,30.211238F)
                               ),FieldOccurs.EXCLUDES, GeoQueryLevel.Polygon)*/
//                               new KeyWordsBuider("site","新.*",FieldOccurs.INCLUDES, QueriesLevel.Regexp),
                               /*new KeyWordsBuider(
                                       new KeywordsCombine(1,
                                               new KeyWordsBuider("id","32677740",FieldOccurs.EXCLUDES, QueriesLevel.Term),
                                               new KeyWordsBuider("title","广告",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                                               new KeyWordsBuider(
                                                       new KeywordsCombine(1,new KeyWordsBuider("content","武汉",FieldOccurs.INCLUDES, QueriesLevel.Phrase) )
                                               )
                                       )
                               )*/
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
//        casiaEsApi.search().addSort(new SortField("pubtime", SortOrder.ASC));
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
        String indexname = "event_data_extract_result_v-202003";
        String indexnameFile = indexname+".txt";
        CasiaEsApi casiaEsApi = new CasiaEsApi("liangqun");
        casiaEsApi.search().setIndexName(indexname, "analysis_data");
        casiaEsApi.search().addSort(new SortField("pubtime", SortOrder.ASC)).setSize(9999);
        /*casiaEsApi.search().setRange(
                new RangeField(FieldOccurs.INCLUDES,
                        "pubtime",
//                        "2019-01-09 16:15:36",
                        "2020-01-01 00:00:00",
//                        "2020-01-01 00:00:00"
                        CasiaTimeUtil.getNowDateTime()
                )
        );*/

        SearchResult searchResult  = casiaEsApi.search().executeQueryInfo();
        System.out.println(searchResult.getTotal_Doc());

        StringBuffer sv = new StringBuffer();
        searchResult.getQueryInfos().forEach(s->{
            JSONObject jsonObject = new JSONObject();
            jsonObject.putAll(s.getField());

            JSONObject jsonObject2  = new JSONObject();
            jsonObject2.put("_id",s.getId());
            JSONObject jsonObject1  = new JSONObject();
            jsonObject1.put("index",jsonObject2);

            sv.append(sv.length()>0?"\r\n":"").append(jsonObject1);
            sv.append("\r\n"+jsonObject);
        });

        CasiaFileUtil.createFile(indexnameFile);
        CasiaFileUtil.write(indexnameFile,sv.toString(),false);
    };
}