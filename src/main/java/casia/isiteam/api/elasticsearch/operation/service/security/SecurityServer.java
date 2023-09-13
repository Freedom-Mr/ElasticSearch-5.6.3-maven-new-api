package casia.isiteam.api.elasticsearch.operation.service.security;

import casia.isiteam.api.elasticsearch.common.vo.security.RoleInfomation;
import casia.isiteam.api.elasticsearch.common.vo.security.UserInfomation;
import casia.isiteam.api.elasticsearch.operation.interfaces.ElasticSearchApi;
import casia.isiteam.api.elasticsearch.util.ExecuteResult;
import casia.isiteam.api.elasticsearch.util.JSONCompare;
import casia.isiteam.api.elasticsearch.util.LogUtil;
import casia.isiteam.api.http.controller.CasiaHttpUtil;
import casia.isiteam.api.toolutil.Validator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: SecurityServer
 * @Description: unknown
 * @Vsersion: 1.0
 * <p>
 * Created by casia.wangzhiyou on 2021/1/20
 * Email: zhiyou_wang@foxmail.com
 */
public class SecurityServer extends ElasticSearchApi implements ElasticSearchApi.SecurityApi {
    private Logger logger = LoggerFactory.getLogger( this.getClass());

    public void setIndexName(String indexName,String indexType) {
        addIndexName(indexName,indexType);
    }

    public UserInfomation searchAuthenticate(){
        UserInfomation user = new UserInfomation();
        String curl=curl(indexParmsStatus.getUrl(),_XPACK,SECURITY,_AUTHENTICATE);
        String resultStr = new CasiaHttpUtil().get(curl, indexParmsStatus.getHeards());
        List<UserInfomation> list = ExecuteResult.parseUserResult(o(resultStr));
        return Validator.check(list)?list.get(0):user;
    }
    public List<UserInfomation> searchAllUser(){
        String curl=curl(indexParmsStatus.getUrl(),_XPACK,SECURITY,USER);
        String resultStr = new CasiaHttpUtil().get(curl, indexParmsStatus.getHeards());
        return ExecuteResult.parseUserResult(o(resultStr));
    }
    public boolean resetPassword(String password){
        if( !Validator.check( password ) ){
            logger.warn(LogUtil.compositionLogEmpty("password parms"));
            return false;
        }
        String curl=curl(indexParmsStatus.getUrl(),_XPACK,SECURITY,USER,ELASTIC,_PASSWORD);
        String resultStr = new CasiaHttpUtil().post(curl, indexParmsStatus.getHeards(),null,o(PASSWORD,password).toString());
        return !Validator.check(JSON.parse(resultStr));
    }
    public boolean addUser(UserInfomation userInfomation){
        String curl=curl(indexParmsStatus.getUrl(),_XPACK,SECURITY,USER,userInfomation.getUsername());
        System.out.println(curl);
        System.out.println(o(JSONObject.toJSONString(userInfomation)).toString());
        String resultStr = new CasiaHttpUtil().post(curl, indexParmsStatus.getHeards(),null,o(JSONObject.toJSONString(userInfomation)).toString());
        return JSONCompare.validationResult(o(resultStr).getString(CREATED),CREATED);
    }
    public boolean delUser(UserInfomation userInfomation){
        String curl=curl(indexParmsStatus.getUrl(),_XPACK,SECURITY,USER,userInfomation.getUsername());
        String resultStr = new CasiaHttpUtil().delete(curl, indexParmsStatus.getHeards());
        return JSONCompare.validationResult(resultStr,FOUND);
    }
    public boolean userState(boolean isEnable,String userName){
        String curl=curl(indexParmsStatus.getUrl(),_XPACK,SECURITY,USER,userName,isEnable?_ENABLE:_DISABLE);
        String resultStr = new CasiaHttpUtil().put(curl, indexParmsStatus.getHeards());
        return !Validator.check(o(resultStr));
    }
    public List<RoleInfomation> allRoles(){
        String curl=curl(indexParmsStatus.getUrl(),_XPACK,SECURITY,ROLE);
        String resultStr = new CasiaHttpUtil().get(curl, indexParmsStatus.getHeards());
        return ExecuteResult.parserRoleResult(o(resultStr));
    }
    public RoleInfomation getRoleInfo(String roleName){
        RoleInfomation roleInfomation = new RoleInfomation();
        String curl=curl(indexParmsStatus.getUrl(),_XPACK,SECURITY,ROLE,roleName);
        String resultStr = new CasiaHttpUtil().get(curl, indexParmsStatus.getHeards());
        List<RoleInfomation> list = ExecuteResult.parserRoleResult(o(resultStr));
        return  Validator.check(list)?list.get(0):roleInfomation;
    }
    public boolean addRole(RoleInfomation roleInfomation){
        String curl=curl(indexParmsStatus.getUrl(),_XPACK,SECURITY,ROLE,roleInfomation.getRoleName());
        String jsonObject = JSON.toJSONString(roleInfomation);
        JSONObject body = JSONObject.parseObject(jsonObject);
        body.remove(ROLENAME);
        System.out.println(curl);
        System.out.println(body.toString());
        String resultStr = new CasiaHttpUtil().post(curl, indexParmsStatus.getHeards(),null,body.toString());
        return  JSONCompare.validationResult(o(resultStr).getString(ROLE),CREATED);
    }
    public boolean delRole(String roleName){
        String curl=curl(indexParmsStatus.getUrl(),_XPACK,SECURITY,ROLE,roleName);
        System.out.println(curl);
        String resultStr = new CasiaHttpUtil().delete(curl, indexParmsStatus.getHeards());
        return  JSONCompare.validationResult(resultStr,FOUND);
    }
}
