package casia.isiteam.api.elasticsearch.common.status;

import casia.isiteam.api.elasticsearch.common.staitcParms.ShareParms;
import casia.isiteam.api.elasticsearch.common.vo._Entity_Db;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: IndexAuthorStatus
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/4/28
 * Email: zhiyou_wang@foxmail.com
 */
public class IndexAuthorStatus extends ShareParms {
    private Logger logger = LoggerFactory.getLogger( this.getClass());
    protected static Map<String, _Entity_Db> elasticDb = new HashMap<>();
}
