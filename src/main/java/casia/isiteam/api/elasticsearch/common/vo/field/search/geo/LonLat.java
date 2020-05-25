package casia.isiteam.api.elasticsearch.common.vo.field.search.geo;

/**
 * ClassName: LonLat
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/25
 * Email: zhiyou_wang@qq.com
 */
public class LonLat {
    private Float lon;
    private Float lat;

    public LonLat(Float lon, Float lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public Float getLon() {
        return lon;
    }

    public Float getLat() {
        return lat;
    }
}
