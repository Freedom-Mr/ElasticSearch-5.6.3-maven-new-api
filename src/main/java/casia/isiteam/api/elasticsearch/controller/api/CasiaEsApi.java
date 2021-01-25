package casia.isiteam.api.elasticsearch.controller.api;

import casia.isiteam.api.elasticsearch.controller.*;
import casia.isiteam.api.elasticsearch.router.ApiRouter;
import casia.isiteam.api.toolutil.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: CasiaEsApi
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/7/8
 * Email: zhiyou_wang@foxmail.com
 */
public class CasiaEsApi {
    public CasiaEsApi(String driverName){
        this.driverName=driverName;
    }
    public CasiaEsApi(List<String> ipPorts){
        this.ipPorts=ipPorts;
    }
    private List<String> ipPorts = new ArrayList<>();
    public CasiaEsApi(List<String> ipPorts,String username,String password){
        this.ipPorts=ipPorts;
        this.userName=username;
        this.password=password;
    }
    private CasiaEsSearch casiaEsSearch;
    public CasiaEsSearch search(){
        if(!Validator.check(this.casiaEsSearch)){this.casiaEsSearch = Validator.check(driverName)?new CasiaEsSearch(driverName):new CasiaEsSearch(ipPorts,userName,password);}
        return this.casiaEsSearch;
    }
    private String driverName;
    private CasiaEsCreate casiaEsCreate;
    public CasiaEsCreate create(){
        if(!Validator.check(this.casiaEsCreate)){this.casiaEsCreate = Validator.check(driverName)?new CasiaEsCreate(driverName):new CasiaEsCreate(ipPorts,userName,password);}
        return this.casiaEsCreate;
    }
    private CasiaEsDelete casiaEsDelete;
    public CasiaEsDelete delete(){
        if(!Validator.check(this.casiaEsDelete)){this.casiaEsDelete = Validator.check(driverName)?new CasiaEsDelete(driverName):new CasiaEsDelete(ipPorts,userName,password);}
        return this.casiaEsDelete;
    }
    private CasiaEsQuery casiaEsQuery;
    public CasiaEsQuery query(){
        if(!Validator.check(this.casiaEsQuery)){this.casiaEsQuery = Validator.check(driverName)?new CasiaEsQuery(driverName):new CasiaEsQuery(ipPorts,userName,password);}
        return this.casiaEsQuery;
    }
    private String userName;
    private CasiaEsUpate casiaEsUpate;
    public CasiaEsUpate update(){
        if(!Validator.check(this.casiaEsUpate)){this.casiaEsUpate = Validator.check(driverName)?new CasiaEsUpate(driverName):new CasiaEsUpate(ipPorts,userName,password);}
        return this.casiaEsUpate;
    }
    private String password;
    private CasiaEsSql casiaEsSql;
    public CasiaEsSql sql(){
        if(!Validator.check(this.casiaEsSql)){this.casiaEsSql = Validator.check(driverName)?new CasiaEsSql(driverName):new CasiaEsSql(ipPorts,userName,password);}
        return this.casiaEsSql;
    }
    private CasiaEsSecurity casiaEsSecurity;
    public CasiaEsSecurity security(){
        if(!Validator.check(this.casiaEsSecurity)){this.casiaEsSecurity = Validator.check(driverName)?new CasiaEsSecurity(driverName):new CasiaEsSecurity(ipPorts,userName,password);}
        return this.casiaEsSecurity;
    }
}
