package casia.isiteam.test.delete;

import casia.isiteam.api.elasticsearch.controller.CasiaEsDelete;


/**
 * ClassName: DeleteIndexTest
 * Description: 删除索引
 * <p>
 * Created by casia.wzy on 2020/5/16
 * Email: zhiyou_wang@foxmail.com
 */
public class DeleteIndexTest {
    /**
     * 删除索引
     * @param args
     */
    public static void main(String[] args) {
        //读取索引地址配置
        CasiaEsDelete casiaEsDelete = new CasiaEsDelete("data");
        casiaEsDelete.setIndexName("demo_test","test_data");
        casiaEsDelete.deleteIndexByName();
    }
}
