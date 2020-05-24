package casia.isiteam.api.elasticsearch.common.enums;

/**
 * ClassName: GeoLevel
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/24
 * Email: zhiyou_wang@foxmail.com
 */
public enum GeoLevel {
    Bounds("geo_bounds"),
    Centroid("geo_centroid");


    private String level;
    private GeoLevel(String level) {
        this.level = level;
    }
    public String getLevel() {
        return level;
    }
}
