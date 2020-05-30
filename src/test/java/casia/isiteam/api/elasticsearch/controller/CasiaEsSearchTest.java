package casia.isiteam.api.elasticsearch.controller;

import casia.isiteam.api.elasticsearch.common.enums.*;
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

        AggsFieldBuider aggsFieldBuider = new AggsFieldBuider();
//        aggsFieldBuider.addType(
//                new TypeInfo("site"),
//                new TypeInfo("eid")
//        );
        AggsFieldBuider cFieldBuider = new AggsFieldBuider();
        cFieldBuider.addType(new TypeInfo("eid"));
        cFieldBuider.addGeo(new GeoInfo(GeoLevel.Bounds,"lal"),new GeoInfo(GeoLevel.Centroid,"lal"));
//        cFieldBuider.addTopDatas(new TopData(2));
//        cFieldBuider.addType(new TypeInfo("domain"));
//        cFieldBuider.addOperation(new OperationInfo(OperationLevel.Avg,"eid"));
//        cFieldBuider.addTerm(new TermInfo("domain",2));
        aggsFieldBuider.addTerm(
                new TermInfo("site",4,cFieldBuider)
//                new TermInfo("eid",5, SortOrder.ASC)
        );
//        aggsFieldBuider.addDate(new DateInfo("pubtime","yyyy-MM-dd","1d",1L,cFieldBuider));
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
        SearchResult searchResult = casiaEsSearch
                .setAggregations(
                new AggsFieldBuider(
                        new DateInfo("pubtime","yyyy-MM","1M",1L)
                )
        ).executeAggsInfo();


        System.out.println("total_doc："+searchResult.getTotal_Doc());
        System.out.println("scrollId："+searchResult.getScrollId());
        System.out.println("json："+ JSON.toJSONString(searchResult) );
        outInfo(searchResult.getAggsInfos(),0);
    }
    public void outInfo( int childrens,List<QueryInfo> aggsInfos){
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<childrens;i++ ){
            sb.append("\t");
        }
        aggsInfos.forEach(s->{
            System.out.print(sb+"{id："+s.getId());
            System.out.print("}，{index_name："+s.getIndexName());
            System.out.print("}，{index_type："+s.getIndexType());
            System.out.print("}，{score："+s.getScore());
            System.out.print("}，{Total_Type："+s.getTotal_Operation());
            System.out.print("}，{field："+s.getField());
            System.out.println("}");
        });
    }
    public void outInfo(List<AggsInfo> aggsInfos,int childrens){
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<childrens;i++ ){
            sb.append("\t");
        }
        for(AggsInfo s:aggsInfos){
            System.out.print(sb+"{field："+s.getField());
            System.out.print("}，{type："+s.getType());
            System.out.print("}，{total_doc："+s.getTotal_Doc());
            System.out.print("}，{total_Operation："+s.getTotal_Operation());
            System.out.println("}");
            if(Validator.check(s.getChildren())){
                outInfo(s.getChildren(),childrens+1);
            }
            if( Validator.check(s.getQueryInfos()) ){
                outInfo( childrens+1,s.getQueryInfos());
            }if( Validator.check(s.getLonLatInfos()) ){
                outLaLInfo( childrens+1,s.getLonLatInfos());
            }
        }
    }
    public void outLaLInfo( int childrens,List<LonLatInfo> aggsInfos){
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<childrens;i++ ){
            sb.append("\t");
        }
        aggsInfos.forEach(s->{
            System.out.print(sb+"{field："+s.getField());
            System.out.print("}，{type："+s.getType());
            System.out.print("}，{lon："+s.getLon());
            System.out.print("}，{lat："+s.getLat());
            System.out.println("}");
        });
    }
    public void testSetMatchAllQuery() {
        casiaEsSearch.setIndexName("test","test_data");
        List<KeyWordsBuider> list= new ArrayList<>();
        SearchResult searchResult = casiaEsSearch.
                setFrom(0).
                setSize(3).
//                addSort(
//                        new SortField("id", SortOrder.DESC),
//                        new SortField("eid", SortOrder.ASC)
//                ).
//                setRange(
//                        new RangeField(FieldOccurs.INCLUDES,"id",32174657,33677173)
////                        new RangeField(FieldOccurs.EXCLUDES,"pubtime","2020-01-09 07:36:00",null)
//                ).
                setQueryKeyWords(
                        new KeywordsCombine(2,
                               new KeyWordsBuider("title","肺炎",FieldOccurs.INCLUDES, QueriesLevel.Phrase),
                               new KeyWordsBuider("title","病毒",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
//                               new KeyWordsBuider("lal",new GeoQueryInfo().addDistance(new LonLat(112.967240F,28.211238F),"100km"),FieldOccurs.INCLUDES, GeoQueryLevel.Distance)
                               new KeyWordsBuider("lal",new GeoQueryInfo().addBox(new LonLat(112.967240F,28.211238F),new LonLat(111.967240F,26.211238F)),FieldOccurs.INCLUDES, GeoQueryLevel.Box)
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
                        ),
                        new KeywordsCombine(1,
                                new KeyWordsBuider("title","疫情",FieldOccurs.INCLUDES, QueriesLevel.Term)
                        )
                ).
//                setExistsFilter("content","title").
//                setMissingFilter("it").
//                openProfile().
//                setMinScore(0.1F).
//                setHighlight(null,null,"id").
                setReturnField("i*","site","pubtime","title").
                setReturnField(true).
//                setReturnField(FieldOccurs.EXCLUDES,"ip").
                executeQueryInfo();

        logger.info("total："+searchResult.getTotal_Doc());
        logger.info("scrollId："+searchResult.getScrollId());
        logger.info("QueryInfoSize："+searchResult.getQueryInfos().size());
        searchResult.getQueryInfos().forEach(s->{
            System.out.print("{id："+s.getId());
            System.out.print("}，{_score："+s.getScore());
            System.out.print("}，{indexName："+s.getIndexName());
            System.out.print("}，{indexType："+s.getIndexType());
            System.out.print("}，{fileds："+s.getField());
            System.out.println("}");
        });


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