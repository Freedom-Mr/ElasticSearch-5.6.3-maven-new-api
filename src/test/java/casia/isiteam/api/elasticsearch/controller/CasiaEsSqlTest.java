package casia.isiteam.api.elasticsearch.controller;

import casia.isiteam.api.elasticsearch.common.vo.result.SearchResult;
import casia.isiteam.api.elasticsearch.util.OutInfo;
import casia.isiteam.api.toolutil.escape.CasiaEscapeUtil;
import com.alibaba.fastjson.JSON;
import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName: CasiaEsSqlTest
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/6/12
 * Email: zhiyou_wang@foxmail.com
 */
public class CasiaEsSqlTest extends TestCase {
    private Logger logger = LoggerFactory.getLogger( this.getClass());

    public void testExecuteQueryInfo() {

        CasiaEsSql casiaEsSql = new CasiaEsSql("web");


//        String sql  = "select * from test limit 10";
//        String sql  = "select * from test/test_data limit 10";
//        String sql  = "select * from test/test_data,demo_test/test_data limit 10";
//        String sql  = "select * from test where eid =687 limit 10";
//        String sql  = "select * from test where eid =687 and keywords ='暴徒' ";
//        String sql  = "select eid,title from test where eid =687 and title ='香港' ";
//        String sql  = "select eid,site from test where eid =687 and site like '香港%' ";
//        String sql  = "select eid from test where eid =687 and pubtime >='2020-05-09 08:35:12' order by pubtime asc";
//        String sql  = "select count(eid) from test ";
//        String sql  = "select sum(eid),avg(eid)  from test group by eid";

        String sql  = " select title from test where title = matchQuery('香港 抗议') order by _score DESC LIMIT 3";
//        String sql  = "select count(id) from test order by range(id, 32669280,32669285,32669287)";
//        String sql  = "select count(id) from test order by date_histogram(field='pubtime','interval'='1d')";
//        String sql  = "select test from test order by date_histogram(field='pubtime','format'='yyyy-MM-dd' ,'2020-01-01','2020-02-01','now-8d','now-7d','now-6d','now')";

        //GEO_BOUNDING_BOX(fieldName,topLeftLongitude,topLeftLatitude,bottomRightLongitude,bottomRightLatitude)
//        String sql  = "select * from test where GEO_BOUNDING_BOX(lal, 112.967240, 28.211238, 111.967240, 26.211238) LIMIT 3";
        //GEO_DISTANCE(fieldName,distance,fromLongitude,fromLatitude)
//        String sql  = "select * from test where GEO_DISTANCE(lal,'1km', 112.967240, 28.211238) LIMIT 3";
//        String sql  = "select * from test where GEO_DISTANCE_RANGE(lal,'1km','10km', 112.967240, 28.211238) LIMIT 3";
//        String sql  = "select * from test where GEO_POLYGON(lal,'1km','10km', 112.967240, 28.211238) LIMIT 3";
//        String sql  = "select * from test where GEO_POLYGON(lal, 112.967240 ,28.211238 , 115.967240, 28.211238, 116.967240,30.211238) LIMIT 3";

        casiaEsSql.setQuerySql(sql);
        SearchResult searchResult = casiaEsSql.executeQueryInfo();

        OutInfo.out(searchResult);
    }
}