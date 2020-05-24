package casia.isiteam.api.elasticsearch.common.vo.field;

import casia.isiteam.api.elasticsearch.common.enums.SortOrder;

/**
 * ClassName: SortField
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/12
 * Email: zhiyou_wang@foxmail.com
 */
public class SortField {
    private String field;
    private SortOrder sortOrder;

    public String getField() {
        return field;
    }

    public SortField setField(String field) {
        this.field = field;
        return this;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public SortField setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public SortField(String field, SortOrder sortOrder) {
        this.field = field;
        this.sortOrder = sortOrder;
    }
}
