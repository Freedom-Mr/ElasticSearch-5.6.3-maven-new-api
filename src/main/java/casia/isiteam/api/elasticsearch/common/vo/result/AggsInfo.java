package casia.isiteam.api.elasticsearch.common.vo.result;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: AggsInfo
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/22
 * Email: zhiyou_wang@foxmail.com
 */
public class AggsInfo {
    private String field;
    private long total_Doc=-1;
    private float total_Operation=-1;
    private String type;
    private List<AggsInfo> children = new ArrayList<>();
    private List<QueryInfo> queryInfos = new ArrayList<>();
    private List<LonLatInfo> lonLatInfos = new ArrayList<>();

    public AggsInfo() {

    }
    public AggsInfo(String type,String field) {
        this.type = type;
        this.field = field;
    }
    public AggsInfo(String type,String field, long total_Doc, float total_Operation) {
        this.type = type;
        this.field = field;
        this.total_Doc = total_Doc;
        this.total_Operation = total_Operation;
    }

    public AggsInfo(String type,String field, long total_Doc,float total_Operation, List<AggsInfo> children) {
        this.type = type;
        this.field = field;
        this.total_Doc = total_Doc;
        this.total_Operation = total_Operation;
        this.children = children;
    }

    public AggsInfo(String field, long total_Doc, float total_Operation, String type, List<AggsInfo> children, List<QueryInfo> queryInfos) {
        this.field = field;
        this.total_Doc = total_Doc;
        this.total_Operation = total_Operation;
        this.type = type;
        this.children = children;
        this.queryInfos = queryInfos;
    }

    public AggsInfo(String field, long total_Doc, float total_Operation, String type, List<AggsInfo> children, List<QueryInfo> queryInfos, List<LonLatInfo> lonLatInfos) {
        this.field = field;
        this.total_Doc = total_Doc;
        this.total_Operation = total_Operation;
        this.type = type;
        this.children = children;
        this.queryInfos = queryInfos;
        this.lonLatInfos = lonLatInfos;
    }

    public List<QueryInfo> getQueryInfos() {
        return queryInfos;
    }

    public AggsInfo setQueryInfos(List<QueryInfo> queryInfos) {
        this.queryInfos = queryInfos;
        return this;
    }

    public String getField() {
        return field;
    }

    public AggsInfo setField(String field) {
        this.field = field;
        return this;
    }

    public long getTotal_Doc() {
        return total_Doc;
    }

    public AggsInfo setTotal_Doc(long total_Doc) {
        this.total_Doc = total_Doc;
        return this;
    }

    public float getTotal_Operation() {
        return total_Operation;
    }

    public AggsInfo setTotal_Operation(float total_Operation) {
        this.total_Operation = total_Operation;
        return this;
    }

    public List<AggsInfo> getChildren() {
        return children;
    }

    public AggsInfo setChildren(List<AggsInfo> children) {
        this.children = children;
        return this;
    }

    public String getType() {
        return type;
    }

    public AggsInfo setType(String type) {
        this.type = type;
        return this;
    }

    public List<LonLatInfo> getLonLatInfos() {
        return lonLatInfos;
    }

    public AggsInfo setLonLatInfos(LonLatInfo ... lonLatInfo) {
        for(LonLatInfo info :lonLatInfo){
            this.lonLatInfos.add(info);
        }

        return this;
    }
}
