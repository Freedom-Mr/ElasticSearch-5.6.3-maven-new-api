package casia.isiteam.api.elasticsearch.common.vo.field.aggs;

/**
 * ClassName: GridInfo
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/6/25
 * Email: zhiyou_wang@foxmail.com
 */
public class GridInfo {

    /**
     * field name
     */
    private String field;

    /**
     * precision
     */
    private int precision = 5;

    /**
     *
     */
    private AggsFieldBuider aggsFieldBuider;
    /**
     * field alias
     */
    private String alias;
    public GridInfo() {}
    public GridInfo(String field, int precision) {
        this.field = field;
        this.precision = precision;
    }

    public GridInfo(String field, int precision, String alias) {
        this.field = field;
        this.precision = precision;
        this.alias = alias;
    }

    public GridInfo(String field) {
        this.field = field;
    }

    public GridInfo(String field, String alias) {
        this.field = field;
        this.alias = alias;
    }

    public GridInfo(String field, int precision, AggsFieldBuider aggsFieldBuider) {
        this.field = field;
        this.precision = precision;
        this.aggsFieldBuider = aggsFieldBuider;
    }

    public GridInfo(String field, int precision, AggsFieldBuider aggsFieldBuider, String alias) {
        this.field = field;
        this.precision = precision;
        this.aggsFieldBuider = aggsFieldBuider;
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public GridInfo setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public String getField() {
        return field;
    }

    public GridInfo setField(String field) {
        this.field = field;
        return this;
    }

    public int getPrecision() {
        return precision;
    }

    public GridInfo setPrecision(int precision) {
        this.precision = precision;
        return this;
    }

    public AggsFieldBuider getAggsFieldBuider() {
        return aggsFieldBuider;
    }

    public GridInfo setAggsFieldBuider(AggsFieldBuider aggsFieldBuider) {
        this.aggsFieldBuider = aggsFieldBuider;
        return this;
    }
}
