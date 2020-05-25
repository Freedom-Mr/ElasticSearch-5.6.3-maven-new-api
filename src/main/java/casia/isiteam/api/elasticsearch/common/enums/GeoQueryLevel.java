package casia.isiteam.api.elasticsearch.common.enums;

/**
 * ClassName: GeoQueryLevel
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/25
 * Email: zhiyou_wang@qq.com
 */
public enum  GeoQueryLevel {
    /**
     * 相似区域
     */
//    SimilarShape("geo_shape"),
    /**
     * 划定长方形区域
     */
    Box("geo_bounding_box"),
    /**
     * 重心，根据半径扩展圆形
     */
    Distance("geo_distance"),
    /**
     * 重心，根据两半径扩展环形
     */
    DistanceRange("geo_distance_range"),
    /**
     * 划定多边形区域
     */
    Polygon("geo_polygon");

    private String level;
    private GeoQueryLevel(String level) {
        this.level = level;
    }
    public String getLevel() {
        return level;
    }
}
