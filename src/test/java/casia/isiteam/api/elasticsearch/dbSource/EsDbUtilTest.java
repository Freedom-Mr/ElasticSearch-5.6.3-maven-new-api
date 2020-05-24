package casia.isiteam.api.elasticsearch.dbSource;

import casia.isiteam.api.elasticsearch.common.vo._Entity_Db;
import junit.framework.TestCase;

/**
 * ClassName: EsDbUtilTest
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/4/29
 * Email: zhiyou_wang@foxmail.com
 */
public class EsDbUtilTest extends TestCase {

    public void testGetDb() {
        _Entity_Db  db = EsDbUtil.getDb("monitor");
        System.out.println(db.getIpPort());
    }
}