package casia.isiteam.api.elasticsearch.common.enums;

import java.util.Arrays;
import java.util.List;

/**
 * ClassName: AggsLevel
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/22
 * Email: zhiyou_wang@foxmail.com
 */
public enum AggsLevel {

    Term("terms","Term"),
    Date("date_histogram","Date"),
    Group("cardinality","Group"),
    Top("top_hits","Top"),

//    Stats("extended_stats"),
    Avg("avg","Avg"),
    Sum("sum","Sum"),
    Max("max","Max"),
    Min("min","Min"),

    Bounds("geo_bounds","Bounds"),
    Centroid("geo_centroid","Centroid");

    private String level;
    private String name;
    private AggsLevel(String level,String name) {
        this.level = level;
        this.name = name;
    }
    public String getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

}
