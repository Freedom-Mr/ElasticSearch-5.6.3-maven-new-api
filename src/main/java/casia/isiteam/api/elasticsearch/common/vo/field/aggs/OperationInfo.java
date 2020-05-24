package casia.isiteam.api.elasticsearch.common.vo.field.aggs;

import casia.isiteam.api.elasticsearch.common.enums.OperationLevel;

/**
 * ClassName: OperationInfo
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/22
 * Email: zhiyou_wang@foxmail.com
 */
public class OperationInfo {
    /**
     * Operation level
     */
    private OperationLevel operationLevel;
    /**
     * field name
     */
    private String field;
    /**
     * set number where field missing a value, default values is 0
     */
    private Long missing;

    public OperationInfo(OperationLevel operationLevel, String field) {
        this.operationLevel = operationLevel;
        this.field = field;
    }

    public OperationInfo(OperationLevel operationLevel, String field, Long missing) {
        this.operationLevel = operationLevel;
        this.field = field;
        this.missing = missing;
    }

    public String getField() {
        return field;
    }

    public OperationInfo setField(String field) {
        this.field = field;
        return this;
    }

    public Long getMissing() {
        return missing;
    }

    public OperationInfo setMissing(Long missing) {
        this.missing = missing;
        return this;
    }

    public OperationLevel getOperationLevel() {
        return operationLevel;
    }

    public OperationInfo setOperationLevel(OperationLevel operationLevel) {
        this.operationLevel = operationLevel;
        return this;
    }
}
