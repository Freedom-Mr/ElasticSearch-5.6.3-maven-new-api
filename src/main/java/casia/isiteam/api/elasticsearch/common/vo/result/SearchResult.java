package casia.isiteam.api.elasticsearch.common.vo.result;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * ClassName: SearchResult
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/12
 * Email: zhiyou_wang@foxmail.com
 */
public class SearchResult {
    private long total_Doc = 0;
    private String scrollId;
    private JSONObject profile;
    private List<QueryInfo> queryInfos = new ArrayList<>();
    private List<AggsInfo> aggsInfos = new ArrayList<>();

    public List<AggsInfo> getAggsInfos() {
        return aggsInfos;
    }

    public void setAggsInfos(List<AggsInfo> aggsInfos) {
        this.aggsInfos = aggsInfos;
    }
    public SearchResult setAggsInfos(AggsInfo aggsInfo) {
        this.aggsInfos.add(aggsInfo);
        return this;
    }

    public List<QueryInfo> getQueryInfos() {
        return queryInfos;
    }
    public SearchResult setQueryInfos(List<QueryInfo> queryInfos) {
        this.queryInfos = queryInfos;
        return this;
    }
    public SearchResult setQueryInfos(QueryInfo dataInfo) {
        this.queryInfos.add(dataInfo);
        return this;
    }

    public long getTotal_Doc() {
        return total_Doc;
    }

    public SearchResult setTotal_Doc(long total_Doc) {
        this.total_Doc = total_Doc;
        return this;
    }

    public String getScrollId() {
        return scrollId;
    }

    public SearchResult setScrollId(String scrollId) {
        this.scrollId = scrollId;
        return this;
    }

    public JSONObject getProfile() {
        return profile;
    }

    public SearchResult setProfile(JSONObject profile) {
        this.profile = profile;
        return this;
    }
}
