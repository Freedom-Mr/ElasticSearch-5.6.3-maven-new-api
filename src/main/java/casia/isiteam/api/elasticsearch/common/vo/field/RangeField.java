package casia.isiteam.api.elasticsearch.common.vo.field;

import casia.isiteam.api.elasticsearch.common.enums.FieldOccurs;
import casia.isiteam.api.toolutil.Validator;

/**
 * ClassName: RangeField
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/13
 * Email: zhiyou_wang@foxmail.com
 */
public class RangeField {
    private FieldOccurs fieldOccurs;
    private String field;
    private Object gte;
    private Object lte;
    private Boolean includeLower;
    private Boolean includeUpper;

    public FieldOccurs getFieldOccurs() {
        return fieldOccurs;
    }

    public void setFieldOccurs(FieldOccurs fieldOccurs) {
        this.fieldOccurs = fieldOccurs;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getGte() {
        return gte;
    }

    public void setGte(Object gte) {
        this.gte = gte;
    }

    public Object getLte() {
        return lte;
    }

    public void setLte(Object lte) {
        this.lte = lte;
    }

    public RangeField(FieldOccurs fieldOccurs, String field, Object gte, Object lte) {
        this.fieldOccurs = fieldOccurs;
        this.field = field;
        this.gte = gte;
        this.lte = lte;
    }
    public RangeField(FieldOccurs fieldOccurs, String field, Object gte, Object lte,Boolean includeLower,Boolean includeUpper) {
        this.fieldOccurs = fieldOccurs;
        this.field = field;
        this.gte = gte;
        this.lte = lte;
        this.includeLower = includeLower;
        this.includeUpper = includeUpper;
    }

    public Boolean getIncludeLower() {
        return Validator.check( includeLower ) ? includeLower :true;
    }

    public RangeField setIncludeLower(Boolean includeLower) {
        this.includeLower = includeLower;
        return this;
    }

    public Boolean getIncludeUpper() {
        return Validator.check( includeUpper ) ? includeUpper :true;
    }

    public RangeField setIncludeUpper(Boolean includeUpper) {
        this.includeUpper = includeUpper;
        return this;
    }
}
