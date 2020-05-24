package casia.isiteam.api.elasticsearch.common.vo.result;

/**
 * ClassName: LonLatInfo
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/24
 * Email: zhiyou_wang@foxmail.com
 */
public class LonLatInfo {
    private String field;
    private String type;
    private float lon;
    private float lat;

    public LonLatInfo(String field,String type, float lon, float lat) {
        this.field = field;
        this.type = type;
        this.lon = lon;
        this.lat = lat;
    }

    public String getType() {
        return type;
    }

    public LonLatInfo setType(String type) {
        this.type = type;
        return this;
    }

    public String getField() {
        return field;
    }

    public LonLatInfo setField(String field) {
        this.field = field;
        return this;
    }

    public float getLon() {
        return lon;
    }

    public LonLatInfo setLon(float lon) {
        this.lon = lon;
        return this;
    }

    public float getLat() {
        return lat;
    }

    public LonLatInfo setLat(float lat) {
        this.lat = lat;
        return this;
    }
}
