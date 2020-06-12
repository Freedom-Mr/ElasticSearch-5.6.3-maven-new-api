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

    CasiaEsDelete casiaEsDelete = new CasiaEsDelete("data");

    public void testDeleteIndexByName() {
        casiaEsDelete.setIndexName("test","");
        System.out.println(casiaEsDelete.deleteIndexByName());
    }


    public void testDeleteDataByIds() {
        casiaEsDelete.setIndexName("test","test_data");
        System.out.println(casiaEsDelete.deleteDataByIds(Arrays.asList(new String[]{"123456789","1"})));
    }

    public void testDeleteDataById() {
        casiaEsDelete.setIndexName("test","test_data");
        System.out.println(casiaEsDelete.deleteDataById("1"));
    }

    public void testDeleteScrollByIds() {
        System.out.println(casiaEsDelete.deleteScrollByIds(Arrays.asList(new String[]{"DXF1ZXJ5QW5kRmV0Y2gBAAAAAAAAALQWZzZzanNIOGVULTZWS0Z4ckh6amVwUQ=="})));
    }

    public void testDeleteScrollByAll() {
        System.out.println(casiaEsDelete.deleteScrollByAll());
    }

    public void testClearCache() {
        casiaEsDelete.setIndexName("test","test_data");
        System.out.println(casiaEsDelete.clearCache());
    }

    public void testDeleteDataByQuery() {
        casiaEsDelete.setIndexName("test","test_data");
        casiaEsDelete.setRange(
                  new RangeField(FieldOccurs.INCLUDES,"pubtime","2020-05-09 07:36:00",null)
        );
        casiaEsDelete.setQueryKeyWords(
                new KeywordsCombine(2,
                        new KeyWordsBuider("title","肺炎", FieldOccurs.INCLUDES, QueriesLevel.Term),
                        new KeyWordsBuider("title","病毒",FieldOccurs.INCLUDES, QueriesLevel.Term))
        );
        int deleteTotal= casiaEsDelete.deleteDataByQuery();
        System.out.println(deleteTotal);
    }
}