package casia.isiteam.api.elasticsearch.common.vo.security;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @ClassName: RoleInfomation
 * @Description: unknown
 * @Vsersion: 1.0
 * <p>
 * Created by casia.wangzhiyou on 2021/1/21
 * Email: zhiyou_wang@foxmail.com
 */
public class RoleInfomation {
    /**
     * 角色
     */
    private String roleName;
    /**
     * 集群权限
     */
    private List<String> cluster;
    /**
     * 索引权限
     */
    private List<JSONObject> indices;
    /**
     * 应用权限，包含application、privileges、resources属性
     */
//    private List<JSONObject> applications;
    /**
     * 全局性的集群权限
     */
//    private Object global;
    /**
     * 赋予该role的用户拥有其他用户的权限
     */
    private List<String> run_as;
    /**
     * 元数据
     */
    private JSONObject metadata;
    private JSONObject transientMetadata;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<String> getCluster() {
        return cluster;
    }

    public void setCluster(List<String> cluster) {
        this.cluster = cluster;
    }

    public List<JSONObject> getIndices() {
        return indices;
    }

    public void setIndices(List<JSONObject> indices) {
        this.indices = indices;
    }

    /*public List<JSONObject> getApplications() {
        return applications;
    }

    public void setApplications(List<JSONObject> applications) {
        this.applications = applications;
    }
*/
    public List<String> getRun_as() {
        return run_as;
    }

    public void setRun_as(List<String> run_as) {
        this.run_as = run_as;
    }

    public JSONObject getMetadata() {
        return metadata;
    }

    public void setMetadata(JSONObject metadata) {
        this.metadata = metadata;
    }

    public JSONObject getTransientMetadata() {
        return transientMetadata;
    }

    public void setTransientMetadata(JSONObject transientMetadata) {
        this.transientMetadata = transientMetadata;
    }

    public RoleInfomation(String roleName, List<String> cluster, List<JSONObject> indices, List<String> run_as, JSONObject metadata, JSONObject transientMetadata) {
        this.roleName = roleName;
        this.cluster = cluster;
        this.indices = indices;
        this.run_as = run_as;
        this.metadata = metadata;
        this.transientMetadata = transientMetadata;
    }
    public RoleInfomation(){}
}
