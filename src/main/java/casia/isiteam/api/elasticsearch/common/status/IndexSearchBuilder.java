package casia.isiteam.api.elasticsearch.common.status;

import casia.isiteam.api.elasticsearch.common.staitcParms.ElasticResultParms;
import casia.isiteam.api.toolutil.Validator;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * ClassName: IndexSearchBuilder
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/12
 * Email: zhiyou_wang@foxmail.com
 */
public class IndexSearchBuilder extends ElasticResultParms {
    private JSONObject search = new JSONObject();
    private JSONObject pire = new JSONObject();
    private JSONArray nodes = new JSONArray();
    private JSONObject query = new JSONObject();
    private JSONObject queryBigBool = new JSONObject();
    private JSONObject orges = new JSONObject();
    private JSONObject count = new JSONObject();
    private JSONObject application = new JSONObject();
    private JSONObject aggs = new JSONObject();
    private JSONObject user = new JSONObject();
    private JSONObject info = new JSONObject();
    private JSONObject threads = new JSONObject();

    /****  search  ****/
    public JSONObject getDelSearch() {
        if(Validator.check(queryBigBool)){
            query.put(BOOL,queryBigBool);
        }
        return query;
    }
    public JSONObject getSearch() {
        query.put(BOOL,queryBigBool);
        search.put(QUERY,query);
        search.put(AGGS,aggs);
        return search;
    }
    public IndexSearchBuilder putSearchCover(String key,Object parms) {
        search.put(key,parms);
        return this;
    }
    public IndexSearchBuilder removeSearch(String key) {
        if( search.containsKey(key) ){
            search.remove(key);
        }
        return this;
    }

    /****  count  ****/
    public JSONObject getCount() {
        query.put(BOOL,queryBigBool);
        count.put(QUERY,query);
        count.put(SIZE,0);
        count.put(AGGS,aggs);
        return count;
    }
    public IndexSearchBuilder putCountCover(String key,Object parms) {
        count.put(key,parms);
        return this;
    }
    public IndexSearchBuilder removeCount(String key) {
        if( count.containsKey(key) ){
            count.remove(key);
        }
        return this;
    }
    /****  aggs  ****/
    public JSONObject getAggs() {
        return aggs;
    }
    public IndexSearchBuilder putAggsCover(String key,Object parms) {
        aggs.put(key,parms);
        return this;
    }
    public IndexSearchBuilder removeAggs(String key) {
        if( aggs.containsKey(key) ){
            aggs.remove(key);
        }
        return this;
    }

    /****  query  ****/
    public JSONObject getQuery() {
        info.put(BOOL,queryBigBool);
        return info;
    }





    /****  queryBigBoolBuilder  ****/
    public JSONObject getQueryBigBool() {
        return queryBigBool;
    }
    public IndexSearchBuilder addQueryBigBoolKeyArray(String key) {
        if( !queryBigBool.containsKey(key) ){
            queryBigBool.put(key,new JSONArray());
        }
        return this;
    }
    public IndexSearchBuilder addQueryBigBoolKeyObject(String key) {
        if( !queryBigBool.containsKey(key) ){
            queryBigBool.put(key,new JSONObject());
        }
        return this;
    }
    public IndexSearchBuilder removeQueryBigBool(String key) {
        if( queryBigBool.containsKey(key) ){
            queryBigBool.remove(key);
        }
        return this;
    }
    public IndexSearchBuilder putQueryBigBool(String key,Object parms) {
        queryBigBool.put(key,parms);
        return this;
    }
    /*******sql*****/
    public JSONObject getThreads() {
        return threads;
    }
    public void setThreads(String sql) {
        this.threads.put(_SQL,sql.replaceAll("^\\s",""));
    }
}
