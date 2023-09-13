package casia.isiteam.api.elasticsearch.controller;

import casia.isiteam.api.elasticsearch.common.enums.FieldOccurs;
import casia.isiteam.api.elasticsearch.common.enums.QueriesLevel;
import casia.isiteam.api.elasticsearch.common.vo.field.RangeField;
import casia.isiteam.api.elasticsearch.common.vo.field.search.KeyWordsBuider;
import casia.isiteam.api.elasticsearch.common.vo.field.search.KeywordsCombine;
import junit.framework.TestCase;

import java.util.Arrays;

/**
 * ClassName: CasiaEsDeleteTest
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/4/29
 * Email: zhiyou_wang@foxmail.com
 */
public class CasiaEsDeleteTest extends TestCase {


    public void testDeleteIndexByName() {

        CasiaEsDelete casiaEsDelete = new CasiaEsDelete("os1");
        casiaEsDelete.setIndexName("toutiao-news-2021-08","toutiao");
        System.out.println(casiaEsDelete.deleteIndexByName());
    }


    public void testDeleteDataByIds() {

        CasiaEsDelete casiaEsDelete = new CasiaEsDelete("data");
        casiaEsDelete.setIndexName("aircraft_info","graph");
        System.out.println(casiaEsDelete.deleteDataByIds(Arrays.asList(new String[]{"123456789","1"})));
    }

    public void testDeleteDataById() {

        CasiaEsDelete casiaEsDelete = new CasiaEsDelete("data");
        casiaEsDelete.setIndexName("aircraft_info","graph");
        System.out.println(casiaEsDelete.deleteDataById("e9523544ae3d16eb2cd930ca89561dc2"));
    }

    public void testDeleteScrollByIds() {

        CasiaEsDelete casiaEsDelete = new CasiaEsDelete("data");
        System.out.println(casiaEsDelete.deleteScrollByIds(Arrays.asList(new String[]{"DXF1ZXJ5QW5kRmV0Y2gBAAAAAAAAALQWZzZzanNIOGVULTZWS0Z4ckh6amVwUQ=="})));
    }

    public void testDeleteScrollByAll() {

        CasiaEsDelete casiaEsDelete = new CasiaEsDelete("data");
        System.out.println(casiaEsDelete.deleteScrollByAll());
    }

    public void testClearCache() {

        CasiaEsDelete casiaEsDelete = new CasiaEsDelete("data");
        casiaEsDelete.setIndexName("test","test_data");
        System.out.println(casiaEsDelete.clearCache());
    }

    public void testDeleteDataByQuery() {

        CasiaEsDelete casiaEsDelete = new CasiaEsDelete("data");
        casiaEsDelete.setIndexName("arr_adsa-2021-04-22","graph");
        casiaEsDelete.setRange(
                  new RangeField(FieldOccurs.INCLUDES,"pubtime",null,"2020-11-16 22:04:34")
        );
        /*casiaEsDelete.setQueryKeyWords(
                new KeywordsCombine(2,
                        new KeyWordsBuider("title","肺炎", FieldOccurs.INCLUDES, QueriesLevel.Term),
                        new KeyWordsBuider("title","病毒",FieldOccurs.INCLUDES, QueriesLevel.Term))
        );*/
        int deleteTotal= casiaEsDelete.deleteDataByQuery();
        System.out.println(deleteTotal);
    }
    public void testDeleteDataScrollByQuery() {

        CasiaEsDelete casiaEsDelete = new CasiaEsDelete("data");
        casiaEsDelete.setIndexName("arr_adsa-2021-04-22","graph");
//        casiaEsDelete.setRange(
//                new RangeField(FieldOccurs.INCLUDES,"pubtime",null,"2020-11-16 22:04:34")
//        );
        casiaEsDelete.setQueryKeyWords(
                new KeywordsCombine(1,
//                        new KeyWordsBuider("title","肺炎", FieldOccurs.INCLUDES, QueriesLevel.Term),
                        new KeyWordsBuider("title","张高丽",FieldOccurs.INCLUDES, QueriesLevel.Phrase))
        );
        casiaEsDelete.setConflicts("proceed");
        casiaEsDelete.setRefresh("wait_for");
        casiaEsDelete.setWaitForCompletion(false);
        casiaEsDelete.setScrollSize(1);
        String task = casiaEsDelete.deleteDataScrollByQuery();
        System.out.println(task);
    }
    public void testDeleteIndexAlias() {
        CasiaEsDelete casiaEsDelete = new CasiaEsDelete("data");
        casiaEsDelete.setIndexName("demo_test","test_data");
        Boolean rs= casiaEsDelete.deleteIndexAlias("wu");
        System.out.println(rs);
    }
}