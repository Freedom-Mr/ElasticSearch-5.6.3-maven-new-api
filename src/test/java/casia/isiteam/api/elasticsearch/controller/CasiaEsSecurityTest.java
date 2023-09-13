package casia.isiteam.api.elasticsearch.controller;

import casia.isiteam.api.elasticsearch.common.vo.security.RoleInfomation;
import casia.isiteam.api.elasticsearch.common.vo.security.UserInfomation;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: CasiaEsSecurityTest
 * @Description: unknown
 * @Vsersion: 1.0
 * <p>
 * Created by casia.wangzhiyou on 2021/1/20
 * Email: zhiyou_wang@foxmail.com
 */
public class CasiaEsSecurityTest extends TestCase {
    private Logger logger = LoggerFactory.getLogger( this.getClass());
    public void testqueryAllUsers() {
        CasiaEsSecurity casiaEsSecurity = new CasiaEsSecurity("os");
        UserInfomation userInfomation = casiaEsSecurity.queryUser();
        System.out.println(userInfomation.getUsername());

        List<UserInfomation> list = casiaEsSecurity.queryAllUsers();
        list.forEach(i->{
            System.out.println(i.getUsername());
        });
    }

    public void testChangePassword() {
        CasiaEsSecurity casiaEsSecurity = new CasiaEsSecurity("nn");
        System.out.println(casiaEsSecurity.changePassword("wzy123456"));
    }

    public void testCreateUser() {
        CasiaEsSecurity casiaEsSecurity = new CasiaEsSecurity("nn");
        UserInfomation user = new UserInfomation();
        user.setUsername("searchUser");
        user.setPassword("123456");
        List<String> roles= new ArrayList<>();
        roles.add("os_search_role");
        user.setRoles(roles);

        System.out.println(casiaEsSecurity.createUser(user));
    }

    public void testDelUser() {
        CasiaEsSecurity casiaEsSecurity = new CasiaEsSecurity("os");
        System.out.println(casiaEsSecurity.delUser("searchUser"));
    }

    public void testSetUserState() {
        CasiaEsSecurity casiaEsSecurity = new CasiaEsSecurity("all");
        System.out.println(casiaEsSecurity.setUserState(true,"web"));
    }

    public void testQueryAllRoles() {
        CasiaEsSecurity casiaEsSecurity = new CasiaEsSecurity("os");
        List<RoleInfomation> list = casiaEsSecurity.queryAllRoles();
        list.forEach(s->{
            System.out.println(s.getRoleName());
        });
    }

    public void testQueryRole() {
        CasiaEsSecurity casiaEsSecurity = new CasiaEsSecurity("os");
        RoleInfomation roleInfomation = casiaEsSecurity.queryRole("os_search_role");
        System.out.println(roleInfomation.getIndices());
        System.out.println(roleInfomation.getRoleName());
    }

    public void testCreateRole() {
        CasiaEsSecurity casiaEsSecurity = new CasiaEsSecurity("nn");
        RoleInfomation roleInfomation = new RoleInfomation();
        roleInfomation.setRoleName("os_write_role");

        List<String> clusters = new ArrayList<>();
        clusters.add("all");
        roleInfomation.setCluster(clusters);

        List<JSONObject> indices = new ArrayList<>();
        JSONObject indice = new JSONObject();
        indice.put("names",new String[]{"*"});
        indice.put("privileges",new String[]{"read","read_cross_cluster","index","view_index_metadata"});
//        indice.put("privileges",new String[]{"read","read_cross_cluster","index","create","delete","write","delete_index","create_index"});
//        indice.put("privileges",new String[]{"all"});
        indices.add(indice);
        roleInfomation.setIndices(indices);

        /*List<String> run_as = new ArrayList<>();
        run_as.add("other_user");
        roleInfomation.setRun_as(run_as);

        JSONObject meta = new JSONObject();
        meta.put("version",1);
        roleInfomation.setMetadata(meta);*/

        boolean rs = casiaEsSecurity.createRole(roleInfomation);
        System.out.println(rs);
    }

    public void testDelRole() {
        CasiaEsSecurity casiaEsSecurity = new CasiaEsSecurity("nn");
        System.out.println(casiaEsSecurity.delRole("os_write_role"));
    }
}