package casia.isiteam.api.elasticsearch.util;

import casia.isiteam.api.toolutil.Validator;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * ClassName: JSONCompare
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/4/30
 * Email: zhiyou_wang@foxmail.com
 */
public class JSONCompare {
    private static Logger logger = LoggerFactory.getLogger( JSONCompare.class);

    public JSONObject o(){
        return new JSONObject();
    }
    public JSONObject o(String value){
        return JSONObject.parseObject(value);
    }
    public String os(String key,Object value){
        return "{\""+key+"\":"+value+"}";
    }
    public JSONObject o(String key,Object value){
        JSONObject jsonObject = new JSONObject();
        if( Validator.check(key) ){
            jsonObject.put(key,value);
        }
        return jsonObject;
    }
    public JSONObject o(JSONObject json,String key,Object value){
        if( Validator.check(key) ){
            json.put(key,value);
        }
        return json;
    }

    public JSONArray a(Object ... values){
        JSONArray jsonArray = new JSONArray();
        if( Validator.check(values) ){
            jsonArray.addAll(Arrays.asList(values));
        }
        return jsonArray;
    }
    public JSONArray a(JSONArray jsona,Object ... values){
        jsona.addAll(Arrays.asList(values));
        return jsona;
    }

    public JSONObject oByKey(JSONObject jsonObject,String key){
        if( jsonObject.containsKey(key) ){
            return jsonObject.getJSONObject(key);
        }
        return new JSONObject();
    }
    public JSONArray aByKey(JSONObject jsonObject,String key){
        if( jsonObject.containsKey(key) ){
            return jsonObject.getJSONArray(key);
        }
        return new JSONArray();
    }
    public String osByKey(JSONObject jsonObject,String key){
        if( jsonObject.containsKey(key) ){
            return jsonObject.getString(key);
        }
        return "";
    }
    public void oAddoKey(JSONObject jsonObject,String key){
        if( !jsonObject.containsKey(key) ){
            jsonObject.put(key,o());
        }
    }
    public void oAddaKey(JSONObject jsonObject,String key){
        if( !jsonObject.containsKey(key) ){
            jsonObject.put(key,a());
        }
    }

    /**
     * 验证结果
     * @param result
     * @param key
     * @param parm
     * @return
     */
    public static boolean validationResult(String result,String key,Boolean parm){
        try {
            JSONObject json = JSONObject.parseObject(result);
            return Validator.check(json) && json.containsKey(key) && parm == json.getBoolean(key) ? true :false;
        }catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
    }
    public static boolean validationResult(String result,String key,String ... parm){
        try {
            JSONObject json = JSONObject.parseObject(result);
            return Validator.check(json) && json.containsKey(key) && Arrays.asList(parm).contains(json.getString(key)) ? true :false;
        }catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
    }
    public static boolean validationResult(String result,String key){
        try {
            JSONObject json = JSONObject.parseObject(result);
            return Validator.check(json) && json.containsKey(key) ? json.getBoolean(key) :false;
        }catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
    }


}
