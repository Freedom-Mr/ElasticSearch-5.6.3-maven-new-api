package casia.isiteam.api.elasticsearch.common.status;

import java.util.Map;
/**
 * ClassName: IndexParmsStatus
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/4/29
 * Email: zhiyou_wang@foxmail.com
 */
public class IndexParmsStatus{
    private String url;
    private Map<String,String> heards;
    private String indexName;
    private String indexType;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getHeards() {
        return heards;
    }

    public void setHeards(Map<String, String> heards) {
        this.heards = heards;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }
}
