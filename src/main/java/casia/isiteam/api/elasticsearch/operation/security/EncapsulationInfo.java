package casia.isiteam.api.elasticsearch.operation.security;

import casia.isiteam.api.elasticsearch.common.staitcParms.HttpHeader;
import casia.isiteam.api.elasticsearch.common.staitcParms.ShareParms;
import casia.isiteam.api.elasticsearch.common.status.IndexParmsStatus;
import casia.isiteam.api.elasticsearch.common.status.IndexSearchBuilder;
import casia.isiteam.api.elasticsearch.dbSource.EsDbUtil;
import casia.isiteam.api.elasticsearch.util.JSONCompare;
import casia.isiteam.api.toolutil.Validator;
import casia.isiteam.api.toolutil.random.CasiaRandomUtil;
import casia.isiteam.api.toolutil.secretKey.base.CasiaBaseUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ClassName: EncapsulationInfo
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/4/29
 * Email: zhiyou_wang@foxmail.com
 */
public class EncapsulationInfo extends EsDbUtil {
    protected String url(String driverName){
        String [] ipPorts = getDb(driverName).getIpPort().split(COMMA_OR_SEMICOLON);
        Object[] ipPort = CasiaRandomUtil.randomNumber(ipPorts,1);
        return Validator.check(ipPort) ?HTTP+String.valueOf(ipPort[0]) : null;
    }
    protected String url(List<String> ipPorts){
        List<Object> ipPort = CasiaRandomUtil.randomNumber(ipPorts.stream().collect(Collectors.toList()),1);
        return Validator.check(ipPort) ? HTTP+String.valueOf(ipPort.get(0)) : null;
    }
    protected Map<String, String> heards(String driverName){
        String key = CasiaBaseUtil.encrypt64(new StringBuffer().append(getDb(driverName).getUsername()).append(COLON).append(getDb(driverName).getPassword()).toString());
        Map<String, String> maps = new HashMap<>();
        maps.put(HttpHeader.USER_AGENT_FIREFOX.getName(),HttpHeader.USER_AGENT_FIREFOX.getValue());
        maps.put(HttpHeader.ACCEPT.getName(),HttpHeader.ACCEPT.getValue());
        maps.put(HttpHeader.ACCEPT_LANGUAGE.getName(),HttpHeader.ACCEPT_LANGUAGE.getValue());
        maps.put(HttpHeader.ACCEPT_ENCODING.getName(),HttpHeader.ACCEPT_ENCODING.getValue());
        maps.put(HttpHeader.AUTHORIZATION.getName(),HttpHeader.AUTHORIZATION.getValue()+key);
        return maps;
    }
    protected Map<String, String> heards(String username,String password){
        String key = CasiaBaseUtil.encrypt64(new StringBuffer().append(username).append(COLON).append(password).toString());
        Map<String, String> maps = new HashMap<>();
        maps.put(HttpHeader.USER_AGENT_FIREFOX.getName(),HttpHeader.USER_AGENT_FIREFOX.getValue());
        maps.put(HttpHeader.ACCEPT.getName(),HttpHeader.ACCEPT.getValue());
        maps.put(HttpHeader.ACCEPT_LANGUAGE.getName(),HttpHeader.ACCEPT_LANGUAGE.getValue());
        maps.put(HttpHeader.ACCEPT_ENCODING.getName(),HttpHeader.ACCEPT_ENCODING.getValue());
        maps.put(HttpHeader.AUTHORIZATION.getName(),HttpHeader.AUTHORIZATION.getValue()+key);
        return maps;
    }
    protected String curl(String url,String ... routers){
        if( Validator.check(routers) ){
            for(String router:routers){
                url+= Validator.check(router) ? SLASH +router:"";
            }
        }
        return url;
    }
    protected String curlSymbol(String url,String symbol, String ... routers){
        if( Validator.check(routers) ){
            for(String router:routers){
                url+= Validator.check(router) ? symbol+router:"";
            }
        }
        return url;
    }

    protected IndexSearchBuilder indexSearchBuilder = new IndexSearchBuilder();
    protected IndexParmsStatus indexParmsStatus = new IndexParmsStatus();
    public void config(String driverName) {
        indexParmsStatus.setUrl(url(driverName));
        indexParmsStatus.setHeards(heards(driverName));
    }
    public void config(List<String> ipPorts) {
        indexParmsStatus.setUrl(url(ipPorts));
        indexParmsStatus.setHeards(heards(null,null));
    }
    public void config(List<String> ipPorts,String username,String password) {
        indexParmsStatus.setUrl(url(ipPorts));
        indexParmsStatus.setHeards(heards(username,password));
    }

}
