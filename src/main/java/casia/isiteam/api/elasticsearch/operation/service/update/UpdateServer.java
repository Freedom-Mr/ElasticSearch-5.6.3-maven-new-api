package casia.isiteam.api.elasticsearch.operation.service.update;

import casia.isiteam.api.elasticsearch.operation.interfaces.ElasticSearchApi;
import casia.isiteam.api.elasticsearch.util.JSONCompare;
import casia.isiteam.api.elasticsearch.util.LogUtil;
import casia.isiteam.api.http.controller.CasiaHttpUtil;
import casia.isiteam.api.toolutil.Validator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * ClassName: UpdateServer
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/4/29
 * Email: zhiyou_wang@foxmail.com
 */
public class UpdateServer extends ElasticSearchApi implements ElasticSearchApi.UpadeApi {
    private Logger logger = LoggerFactory.getLogger( this.getClass());

    public void setIndexName(String indexName,String indexType) {
        addIndexName(indexName,indexType);
    }
    /**
     * update data info by _id
     * @param _id PRIMARY KEY
     * @param parameters parameters
     */
    public boolean updateParameterById (String _id ,Map< String, Object > parameters){
        if( !Validator.check(parameters) ){
            logger.warn(LogUtil.compositionLogEmpty("updateParameterById parameters "));
            return true;
        }
        String curl=curl(indexParmsStatus.getUrl(),indexParmsStatus.getIndexName(),indexParmsStatus.getIndexType(), _id , _UPDATE );
        logger.debug(LogUtil.compositionLogCurl(curl));
        JSONObject js = o(DOC,o(JSON.toJSONString(parameters)));
        CasiaHttpUtil casiaHttpUtil = new CasiaHttpUtil();
        String resultStr = casiaHttpUtil.post( curl,indexParmsStatus.getHeards(),null, js.toString() );
        return JSONCompare.validationResult(resultStr,RESULT,UPDATE,NOOP);
    }
    /**
     * update data info by query
     * @param _id PRIMARY KEY
     * @param parameters parameters
     */
    public boolean updateParameterByQuery (String _id ,Map< String, Object > parameters){
        return false;
    }
}
