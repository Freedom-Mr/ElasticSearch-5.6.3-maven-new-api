package casia.isiteam.api.elasticsearch.common.vo.security;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @ClassName: UserInfomation
 * @Description: unknown
 * @Vsersion: 1.0
 * <p>
 * Created by casia.wangzhiyou on 2021/1/20
 * Email: zhiyou_wang@foxmail.com
 */
public class UserInfomation {
    private String username;
    private String full_name;
    private String email;
    private JSONObject metadata;
    private List<String> roles;
    private Boolean enabled;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public JSONObject getMetadata() {
        return metadata;
    }

    public void setMetadata(JSONObject metadata) {
        this.metadata = metadata;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public UserInfomation(){

    }
    public UserInfomation(String username, String full_name, String email, JSONObject metadata, List<String> roles, Boolean enabled, String password) {
        this.username = username;
        this.full_name = full_name;
        this.email = email;
        this.metadata = metadata;
        this.roles = roles;
        this.enabled = enabled;
        this.password = password;
    }

    public UserInfomation(String username, List<String> roles, String password) {
        this.username = username;
        this.roles = roles;
        this.password = password;
    }
}
