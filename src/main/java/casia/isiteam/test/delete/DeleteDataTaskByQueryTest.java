package casia.isiteam.test.delete;

import casia.isiteam.api.elasticsearch.common.enums.FieldOccurs;
import casia.isiteam.api.elasticsearch.common.vo.field.RangeField;
import casia.isiteam.api.elasticsearch.controller.CasiaEsDelete;

import java.util.Arrays;

/**
 * @ClassName: DeleteDataTaskByQueryTest
 * @Description: 通过条件执行自动删除任务
 * @Vsersion: 1.0
 * <p>
 * Created by casia.wangzhiyou on 2021/4/23
 * Email: zhiyou_wang@foxmail.com
 */
public class DeleteDataTaskByQueryTest {
    /**
     * 通过条件执行自动删除任务
     * @param args
     */
    public static void main(String[] args) {
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
        casiaEsDelete.setConflicts("proceed");
        casiaEsDelete.setRefresh("wait_for");
        casiaEsDelete.setWaitForCompletion(false);
        casiaEsDelete.setScrollSize(1);
        String task = casiaEsDelete.deleteDataScrollByQuery();
        System.out.println(task);

    }
}
