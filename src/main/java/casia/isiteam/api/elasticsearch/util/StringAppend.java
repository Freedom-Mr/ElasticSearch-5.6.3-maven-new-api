package casia.isiteam.api.elasticsearch.util;

import casia.isiteam.api.elasticsearch.common.enums.AggsLevel;
import casia.isiteam.api.elasticsearch.common.staitcParms.ShareParms;

/**
 * ClassName: StringAppend
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/22
 * Email: zhiyou_wang@foxmail.com
 */
public class StringAppend {
    public static String aggsFieldAppend(AggsLevel aggsLevel,String field){
        return ShareParms.QUESTION+aggsLevel.getLevel()+ShareParms.QUESTION+field;
    }
    public static String aggsFieldAppend(String aggsLevel,String field){
        return ShareParms.QUESTION+aggsLevel+ShareParms.QUESTION+field;
    }
}
