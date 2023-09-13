package casia.isiteam.api.elasticsearch.controller;

import casia.isiteam.api.elasticsearch.common.vo.security.RoleInfomation;
import casia.isiteam.api.elasticsearch.common.vo.security.UserInfomation;
import casia.isiteam.api.elasticsearch.operation.interfaces.ElasticSearchApi;
import casia.isiteam.api.elasticsearch.router.ApiRouter;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: CasiaEsSecurity
 * @Description: unknown
 * @Vsersion: 1.0
 * <p>
 * Created by casia.wangzhiyou on 2021/1/20
 * Email: zhiyou_wang@foxmail.com
 */
public class CasiaEsSecurity {
    private Logger logger = LoggerFactory.getLogger( this.getClass());

    private ElasticSearchApi.SecurityApi securityApi;
    public CasiaEsSecurity(String driverName){
        securityApi = ApiRouter.getSecurityRouter();
        securityApi.config(driverName);
    }

    public CasiaEsSecurity(List<String> ipPorts){
        securityApi = ApiRouter.getSecurityRouter();
        securityApi.config(ipPorts);
    }

    public CasiaEsSecurity(List<String> ipPorts,String username,String password){
        securityApi = ApiRouter.getSecurityRouter();
        securityApi.config(ipPorts,username,password);
    }

    /**
     * set indexName and indexType
     * @param indexName
     * @param indexType
     */
    public void setIndexName(String indexName,String indexType) {
        securityApi.setIndexName(indexName,indexType);
    }
    public void setIndexName(String indexName) {
        securityApi.setIndexName(indexName,null);
    }
    public void setIndexType(String indexType) {
        securityApi.setIndexName(null,indexType);
    }

    /**
     * Query the current user information
     */
    public UserInfomation queryUser() {
        return securityApi.searchAuthenticate();
    }
    public List<UserInfomation> queryAllUsers() {
        return securityApi.searchAllUser();
    }
    public boolean changePassword(String password){
        return securityApi.resetPassword(password);
    }
    public boolean createUser(UserInfomation userInfomation){
        return securityApi.addUser(userInfomation);
    }
    public boolean delUser(UserInfomation userInfomation){
        return securityApi.delUser(userInfomation);
    }
    public boolean delUser(String userName){
        UserInfomation userInfo = new UserInfomation();
        userInfo.setUsername(userName);
        return securityApi.delUser(userInfo);
    }
    public boolean setUserState(boolean isEnable,String userName){
        return securityApi.userState(isEnable,userName);
    }
    public List<RoleInfomation> queryAllRoles(){
        return securityApi.allRoles();
    }
    public RoleInfomation queryRole(String roleName){
        return securityApi.getRoleInfo(roleName);
    }
    public boolean createRole(RoleInfomation roleInfomation){
        return securityApi.addRole(roleInfomation);
    }
    public boolean delRole(String roleName){
        return securityApi.delRole(roleName);
    }
}
