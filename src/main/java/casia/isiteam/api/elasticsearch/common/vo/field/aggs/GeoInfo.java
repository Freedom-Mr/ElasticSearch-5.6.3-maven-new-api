package casia.isiteam.api.elasticsearch.common.vo.field.aggs;

import casia.isiteam.api.elasticsearch.common.enums.GeoLevel;

/**
 * ClassName: GeoInfo
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/24
 * Email: zhiyou_wang@foxmail.com
 */
public class GeoInfo {
    /**
     * Geo level type
     */
    private GeoLevel geoLevel;
    /**
     * field name
     */
    private String field;
    /**
     * wrap_longitude is an optional parameter which specifies whether the bounding box should be allowed to overlap the international date line.
     * The default value is true
     */
    private Boolean wrap;
    /**
     * field alias
     */
    private String alias;

    public GeoInfo() { }
    public GeoInfo(GeoLevel geoLevel ,String field) {
        this.geoLevel = geoLevel;
        this.field = field;
    }

    public GeoInfo(GeoLevel geoLevel, String field, String alias) {
        this.geoLevel = geoLevel;
        this.field = field;
        this.alias = alias;
    }

    public GeoInfo(GeoLevel geoLevel , String field, Boolean wrap) {
        this.geoLevel = geoLevel;
        this.field = field;
        this.wrap = wrap;
    }

    public GeoInfo(GeoLevel geoLevel, String field, Boolean wrap, String alias) {
        this.geoLevel = geoLevel;
        this.field = field;
        this.wrap = wrap;
        this.alias = alias;
    }

    public GeoLevel getGeoLevel() {
        return geoLevel;
    }

    public GeoInfo setGeoLevel(GeoLevel geoLevel) {
        this.geoLevel = geoLevel;
        return this;
    }

    public String getField() {
        return field;
    }

    public GeoInfo setField(String field) {
        this.field = field;
        return this;
    }

    public Boolean getWrap() {
        return wrap;
    }

    public GeoInfo setWrap(Boolean wrap) {
        this.wrap = wrap;
        return this;
    }

    public String getAlias() {
        return alias;
    }

    public GeoInfo setAlias(String alias) {
        this.alias = alias;
        return this;
    }
}
