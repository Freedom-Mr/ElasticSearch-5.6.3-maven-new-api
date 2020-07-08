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
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
       /* SearchResult searchResult = casiaEsSearch.
                setRange(new RangeField(FieldOccurs.INCLUDES,"pubtime","2020-05-06 00:00:00","2020-05-08 00:00:00"))
                .setAggregations(
                    new AggsFieldBuider(
                            new DateInfo("pubtime","yyyy-MM-dd HH","1H",0L,"2020-05-06 00","2020-05-08 00").
                                    setAggsFieldBuider(
                                            new AggsFieldBuider(
                                                    new TypeInfo("eid")
                                            )
                                    )
                    ),
                    new AggsFieldBuider(
                            new DateInfo("addtime","yyyy-MM-dd","1d",0L,"2020-05-06","2020-05-08")
                    )
                ).executeQueryInfo();*/

        /**** TopData ***/
        SearchResult searchResult = casiaEsSearch
                .setAggregations(
                        new AggsFieldBuider(
                                new TopData(2).setAlias("top_list"),
                                new TopData(2, Arrays.asList(new SortField("pubtime",SortOrder.ASC)))
                        )
                ).executeAggsInfo();

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
        CasiaEsApi casiaEsApi = new CasiaEsApi("beihang");
        casiaEsApi.search().setIndexName("event_mblog_ref_beihang","beihang_data");
        casiaEsApi.search().
                setFrom(0).
                setSize(3).
            setQueryKeyWords(
                new KeywordsCombine(1,
                        new KeyWordsBuider("blogger","天津日报",FieldOccurs.INCLUDES, QueriesLevel.Term)
                )
            );

        SearchResult searchResult = casiaEsApi.search().setReturnField("i*","blogger","pubtime","title").executeQueryInfo();

        OutInfo.out(searchResult);

    }
}