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
import casia.isiteam.api.elasticsearch.util.OutInfo;
import casia.isiteam.api.toolutil.Validator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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

    CasiaEsSearch casiaEsSearch = new CasiaEsSearch("web");
    public void testSetAggs() {
        casiaEsSearch.setIndexName("test","test_data");
        casiaEsSearch.setSize(0);
        AggsFieldBuider aggsFieldBuider = new AggsFieldBuider();
//        aggsFieldBuider.addType(
//                new TypeInfo("site"),
//                new TypeInfo("eid")
//        );
//        AggsFieldBuider cFieldBuider = new AggsFieldBuider();
//        cFieldBuider.addType(new TypeInfo("eid"));
//        cFieldBuider.addGeo(new GeoInfo(GeoLevel.Bounds,"lal"),new GeoInfo(GeoLevel.Centroid,"lal"));
//        cFieldBuider.addTopDatas(new TopData(2));
//        cFieldBuider.addType(new TypeInfo("domain"));
//        cFieldBuider.addOperation(new OperationInfo(OperationLevel.Avg,"eid"));
//        cFieldBuider.addTerm(new TermInfo("domain",2));
//        aggsFieldBuider.addTerm(
//                new TermInfo("site",4,cFieldBuider)
////                new TermInfo("eid",5, SortOrder.ASC)
//        );
//        aggsFieldBuider.addDate(new DateInfo("pubtime","yyyy-MM-dd HH","1H",0L,"2020-01-20 00","2020-02-01 00"));
//        SearchResult searchResult = casiaEsSearch.setRange(new RangeField(FieldOccurs.INCLUDES,"pubtime","2020-01-20 00:00:00","2020-02-01 00:00:00"))
//                .setAggregations(aggsFieldBuider).executeQueryInfo();
       /* aggsFieldBuider.addOperation(
                new OperationInfo(OperationLevel.Sum,"eid"),
                new OperationInfo(OperationLevel.Avg,"eid",100L),
                new OperationInfo(OperationLevel.Max,"eid"),
                new OperationInfo(OperationLevel.Max,"eid")
        );*/
        /*aggsFieldBuider.addTopDatas(
                new TopData(2)
        );*/
//        aggsFieldBuider.addGeo(new GeoInfo(GeoLevel.Bounds,"lal"),new GeoInfo(GeoLevel.Centroid,"lal"));
//        SearchResult searchResult = casiaEsSearch
//                .setAggregations(
//                new AggsFieldBuider(
//                        new DateInfo("pubtime","yyyy-MM","1M",1L)
//                )
//        ).executeAggsInfo();
//        SearchResult searchResult = casiaEsSearch
//                .setAggregations(
//                        new AggsFieldBuider(
////                                new PriceInfo("eid","*-50","50-70","100-*")
////
////                                new IpRangeInfo("ip","27.195.96.196-27.195.96.199","27.195.96.199-*","27.195.96.127/23").setAggsFieldBuider(
////                                        new AggsFieldBuider(
//////                                                new DateInfo("pubtime","yyyy-MM","1M",1L)
////                                            new GridInfo("lal",3)
////                                        )
////                                )
//
//                                new GridInfo("lal",3)
//                        )
//                ).executeQueryInfo();
        SearchResult searchResult = casiaEsSearch
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
                                        new KeyWordsBuider("domain","epochtimes.com",FieldOccurs.INCLUDES, QueriesLevel.Term)
                                )
                        )
                ).executeAggsInfo();
        OutInfo.out(searchResult);
    }

    public void testSetMatchAllQuery() {
        casiaEsSearch.setIndexName("test","test_data");
        List<KeyWordsBuider> list= new ArrayList<>();
        SearchResult searchResult = casiaEsSearch.
                setFrom(0).
                setSize(3).
//                addSort(
//                        new SortField("pubtime", SortOrder.DESC)
//                        new SortField("eid", SortOrder.ASC)
//                ).
                setRange(
                        new RangeField(FieldOccurs.INCLUDES,"id",32174657,33677173)
////                        new RangeField(FieldOccurs.EXCLUDES,"pubtime","2020-01-09 07:36:00",null)
                ).
                setQueryKeyWords(
                        new KeywordsCombine(1,
                               new KeyWordsBuider("mmsi","\\sad*dsa-?>.(",FieldOccurs.INCLUDES, QueriesLevel.Term),
                               new KeyWordsBuider("lal",new GeoQueryInfo().addBox(
                                        new LonLat(112.967240F,28.211238F),
                                        new LonLat(111.967240F,26.211238F)),FieldOccurs.INCLUDES, GeoQueryLevel.Box)
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

       /* casiaEsSearch.reset();
        searchResult = casiaEsSearch.setMatchAllQuery().executeQuery();
        System.out.println("total："+searchResult.getTotal());
        searchResult.getDataInfos().forEach(s->{
            System.out.print("id："+s.getId());
            System.out.print("\tindexName："+s.getIndexName());
            System.out.print("\tindexType："+s.getIndexType());
            System.out.print("\tfileds："+s.getField());
            System.out.println("");
        });*/
    }
}