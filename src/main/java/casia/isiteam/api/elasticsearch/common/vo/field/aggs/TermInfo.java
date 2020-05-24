package casia.isiteam.api.elasticsearch.common.vo.field.aggs;

import casia.isiteam.api.elasticsearch.common.enums.SortOrder;

/**
 * ClassName: TermInfo
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/22
 * Email: zhiyou_wang@foxmail.com
 */
public class TermInfo {
    /**
     * field name
     */
    private String field;
    /**
     * return top info，default values is 10
     */
    private Integer size;
    /**
     * min doc count, default values is 1
     */
    private Long minDocTotal;
    /**
     * shard size, default values equals "size"
     */
    private Integer shardSize;
    /**
     * sort by field doc_total
     */
    private SortOrder sortOrder;
    /**
     *
     */
    private AggsFieldBuider aggsFieldBuider;

    public TermInfo(String field) {
        this.field = field;
    }

    public TermInfo(String field, Integer size) {
        this.field = field;
        this.size = size;
    }

    public TermInfo(String field, Integer size, SortOrder sortOrder) {
        this.field = field;
        this.size = size;
        this.sortOrder = sortOrder;
    }

    public TermInfo(String field, Integer size, Long minDocTotal, SortOrder sortOrder) {
        this.field = field;
        this.size = size;
        this.minDocTotal = minDocTotal;
        this.sortOrder = sortOrder;
    }

    public TermInfo(String field, Integer size, Long minDocTotal, SortOrder sortOrder, AggsFieldBuider aggsFieldBuider) {
        this.field = field;
        this.size = size;
        this.minDocTotal = minDocTotal;
        this.sortOrder = sortOrder;
        this.aggsFieldBuider = aggsFieldBuider;
    }

    public TermInfo(String field, Integer size, AggsFieldBuider aggsFieldBuider) {
        this.field = field;
        this.size = size;
        this.aggsFieldBuider = aggsFieldBuider;
    }

    public TermInfo(String field, Integer size, Long minDocTotal, Integer shardSize, SortOrder sortOrder, AggsFieldBuider aggsFieldBuider) {
        this.field = field;
        this.size = size;
        this.minDocTotal = minDocTotal;
        this.shardSize = shardSize;
        this.sortOrder = sortOrder;
        this.aggsFieldBuider = aggsFieldBuider;
    }

    public String getField() {
        return field;
    }

    public TermInfo setField(String field) {
        this.field = field;
        return this;
    }

    public Integer getSize() {
        return size;
    }

    public TermInfo setSize(Integer size) {
        this.size = size;
        return this;
    }

    public Integer getShardSize() {
        return shardSize;
    }

    public TermInfo setShardSize(Integer shardSize) {
        this.shardSize = shardSize;
        return this;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public TermInfo setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public Long getMinDocTotal() {
        return minDocTotal;
    }

    public TermInfo setMinDocTotal(Long minDocTotal) {
        this.minDocTotal = minDocTotal;
        return this;
    }

    public AggsFieldBuider getAggsFieldBuider() {
        return aggsFieldBuider;
    }

    public TermInfo setAggsFieldBuider(AggsFieldBuider aggsFieldBuider) {
        this.aggsFieldBuider = aggsFieldBuider;
        return this;
    }
}
