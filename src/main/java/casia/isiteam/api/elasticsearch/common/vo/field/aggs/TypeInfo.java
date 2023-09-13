package casia.isiteam.api.elasticsearch.common.vo.field.aggs;

/**
 * ClassName: TypeInfo
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/22
 * Email: zhiyou_wang@foxmail.com
 */
public class TypeInfo {
    /**
     * field name
     */
    private String field;
    /**
     * 精度，默认3000，最大40000
     */
    private Integer precision;
    /**
     * field alias
     */
    private String alias;

    public TypeInfo() {
    }
    public TypeInfo(String field) {
        this.field = field;
    }

    public TypeInfo(String field, String alias) {
        this.field = field;
        this.alias = alias;
    }

    public TypeInfo(String field, Integer precision) {
        this.field = field;
        this.precision = precision;
    }

    public TypeInfo(String field, Integer precision, String alias) {
        this.field = field;
        this.precision = precision;
        this.alias = alias;
    }

    public String getField() {
        return field;
    }

    public TypeInfo setField(String field) {
        this.field = field;
        return this;
    }

    public Integer getPrecision() {
        return precision;
    }

    public TypeInfo setPrecision(Integer precision) {
        this.precision = precision;
        return this;
    }

    public String getAlias() {
        return alias;
    }

    public TypeInfo setAlias(String alias) {
        this.alias = alias;
        return this;
    }
}
