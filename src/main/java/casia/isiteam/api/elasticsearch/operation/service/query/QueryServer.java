package casia.isiteam.api.elasticsearch.operation.service.query;

import casia.isiteam.api.elasticsearch.operation.interfaces.ElasticSearchApi;
import casia.isiteam.api.elasticsearch.util.LogUtil;
import casia.isiteam.api.http.controller.CasiaHttpUtil;
import casia.isiteam.api.toolutil.Validator;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: QueryServer
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/4/29
 * Email: zhiyou_wang@foxmail.com
 */
public class QueryServer extends ElasticSearchApi implements ElasticSearchApi.QueryApi {
    private Logger logger = LoggerFactory.getLogger( this.getClass());

    public void setIndexName(String indexName,String indexType) {
        addIndexName(indexName,indexType);
    }
    /**
     * search index by indexName
     * @param indexName
     * @return JSONObject
     */
    @Override
    public JSONObject queryIndexByName(String indexName) {
        if( !Validator.check( indexName ) ){
            logger.warn(LogUtil.compositionLogEmpty("indexName"));
            return null;
        }
        String curl=curl(indexParmsStatus.getUrl(),Validator.check(indexName)? indexName : indexParmsStatus.getIndexName() );
        logger.debug(LogUtil.compositionLogCurl(curl,indexName));
        CasiaHttpUtil casiaHttpUtil = new CasiaHttpUtil();
        String resultStr = casiaHttpUtil.get(curl,indexParmsStatus.getHeards());
        return JSONObject.parseObject(resultStr);
    }
    /**
     * search all indexName
     * @return true or false
     */
    public List<String> queryIndexNames (){
        List<String> list = new ArrayList<String>();
        String curl=curlSymbol( curl(indexParmsStatus.getUrl(),_CAT,INDICES),QUESTION,h+EQUAL+INDEX);
        logger.debug(LogUtil.compositionLogCurl(curl,""));
        if(debugInfo()){logger.info(LogUtil.compositionLogCurl(curl,""));};
        CasiaHttpUtil casiaHttpUtil = new CasiaHttpUtil();
        String resultStr = casiaHttpUtil.get(curl,indexParmsStatus.getHeards());
        try {
            if( Validator.check(resultStr) ){
                String[] indexnames = resultStr.split(N);
                for (String indexname : indexnames) {
                    list.add(indexname.replaceAll(BLANK_OR_T, NONE).trim());
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("query index names Exception:{}",e.getMessage());
            return list;
        }
        return list;
    }
    /**
     * search all Alias
     * or
     * search all Alias by indexName
     * or
     * search all Alias by wildcard
     * @return map
     */
    public Map<String,List<String>> queryIndexAlias(String wildcard){
        Map<String,List<String>> map = new HashMap<>();

        String curl = curl(indexParmsStatus.getUrl(),indexParmsStatus.getIndexName(),_ALIAS,Validator.check(wildcard)?wildcard:STAR);
        CasiaHttpUtil casiaHttpUtil = new CasiaHttpUtil();
        String resultStr = casiaHttpUtil.get(curl,indexParmsStatus.getHeards());
        try {
            JSONObject rs =o(resultStr);
            if( !rs.containsKey(ERROR)  ){
                rs.forEach((k,v)->{
                    List<String> list = new ArrayList<String>();
                    o(String.valueOf(v)).getJSONObject(ALIASES).forEach((o,p)->{
                        list.add(o);
                    });
                    if( Validator.check(list) ){
                        map.put(k,list);
                    }
                });
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("query index Alias Exception:{}",e.getMessage());
            return map;
        }
        return map;
    }
}
