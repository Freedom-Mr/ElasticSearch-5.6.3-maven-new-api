package casia.isiteam.api.elasticsearch.common.vo.result;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: QueryInfo
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/12
 * Email: zhiyou_wang@foxmail.com
 */
public class QueryInfo {

    private Map<String,Object> field = new HashMap<>();
    private String indexName;
    private String indexType;
    private String id;
    private String score;
    private long total_Type = -1;

    public String getIndexName() {
        return indexName;
    }

    public QueryInfo setIndexName(String indexName) {
        this.indexName = indexName;
        return this;
    }

    public String getIndexType() {
        return indexType;
    }

    public QueryInfo setIndexType(String indexType) {
        this.indexType = indexType;
        return this;
    }

    public String getId() {
        return id;
    }

    public QueryInfo setId(String id) {
        this.id = id;
        return this;
    }

    public Map<String, Object> getField() {
        return field;
    }

    public QueryInfo setField(Map<String, Object> field) {
        this.field = field;
        return this;
    }

    public String getScore() {
        return score;
    }

    public QueryInfo setScore(String score) {
        this.score = score;
        return this;
    }

    public long getTotal_Type() {
        return total_Type;
    }

    public QueryInfo setTotal_Type(long total_Type) {
        this.total_Type = total_Type;
        return this;
    }
}
