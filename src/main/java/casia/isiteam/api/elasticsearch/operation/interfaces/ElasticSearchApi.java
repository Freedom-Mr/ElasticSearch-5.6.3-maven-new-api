package casia.isiteam.api.elasticsearch.operation.interfaces;

import casia.isiteam.api.elasticsearch.common.enums.FieldOccurs;
import casia.isiteam.api.elasticsearch.common.vo.field.aggs.AggsFieldBuider;
import casia.isiteam.api.elasticsearch.common.vo.result.SearchResult;
import casia.isiteam.api.elasticsearch.common.vo.field.search.KeywordsCombine;
import casia.isiteam.api.elasticsearch.common.vo.field.RangeField;
import casia.isiteam.api.elasticsearch.common.vo.field.SortField;
import casia.isiteam.api.elasticsearch.operation.security.EncapsulationInfo;
import casia.isiteam.api.toolutil.Validator;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ElasticSearchApi
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/4/28
 * Email: zhiyou_wang@foxmail.com
 */
public class ElasticSearchApi extends EncapsulationInfo {
    public interface SearchApi {
        void config(String driverName);
        void config(List<String> ipPorts);
        void config(List<String> ipPorts,String username,String password);
        void setIndexName(String indexName,String indexType);
        void reset();
        void setMatchAllQuery();
        void setFrom(int start);
        void setSize(int rows);
        void addSort(SortField ... sortFields);
        void setReturnField(Boolean isReturnAll);
        void setReturnField(FieldOccurs fieldOccurs,String ... fileds);
        void setHighlight(String pre_tags,String post_tags,String ... fileds);
        void setMinScore(Float min_score);
        void openProfile();
        void setRange(RangeField ... rangeFields);
        void setFieldExistsFilter(FieldOccurs fieldOccurs,String ... fileds);
        void setQueryKeyWords(KeywordsCombine... keywordsCombines);
        void setAggregations(AggsFieldBuider... aggsFieldBuiders);
        SearchResult executeQueryInfo();
        SearchResult executeScrollInfo(String scroll_time,String scroll_id);
        SearchResult executeQueryTotal();
        @Deprecated
        SearchResult executeAggsInfo();

    }

    public interface CreateApi {
        void config(String driverName);
        void config(List<String> ipPorts);
        void config(List<String> ipPorts,String username,String password);
        void setIndexName(String indexName,String indexType);
        boolean creatIndex(String indexName,String mapping);
        boolean creatIndex(String mapping);
        boolean writeData(List<JSONObject> datas , String uniqueKeyName ,String bakingName);
        Map<String,Object> writeDatas( List<JSONObject> datas , String uniqueKeyName ,String bakingName);
        boolean insertField(String fieldName,Map<String, String> map);
        boolean closeIndex();
        boolean openIndex();
        boolean flushIndex();
        boolean refreshIndex();
        Map<String,Object> reIndexData(String oldIndexName,String newIndexName);
        boolean addIndexAlias(String alias);
    }
    public interface QueryApi {
        void config(String driverName);
        void config(List<String> ipPorts);
        void config(List<String> ipPorts,String username,String password);
        void setIndexName(String indexName,String indexType);
        JSONObject queryIndexByName(String indexName);
        List<String> queryIndexNames();
        public Map<String,List<String>> queryIndexAlias(String wildcard);
    }
    public interface DelApi {
        void config(String driverName);
        void config(List<String> ipPorts);
        void config(List<String> ipPorts,String username,String password);
        void setIndexName(String indexName,String indexType);
        boolean deleteIndexByName();
        boolean deleteDataById(String id);
        boolean deleteDataByIds(List<String> _ids);
        boolean deleteScrollByIds(List<String> scroll_ids);
        boolean deleteScrollByAll();
        boolean clearCache();

        void setQueryKeyWords(KeywordsCombine... keywordsCombines);
        void setRange(RangeField ... rangeFields);
        void setFieldExistsFilter(FieldOccurs fieldOccurs,String ... fileds);
        int deleteDataByQuery();
        boolean delIndexAlias(String alias);
    }

    public interface UpadeApi {
        void config(String driverName);
        void config(List<String> ipPorts);
        void config(List<String> ipPorts,String username,String password);
        void setIndexName(String indexName,String indexType);
        boolean updateParameterById (String _id ,Map< String, Object > parameters);
    }

    public interface SqlApi {
        void config(String driverName);
        void config(List<String> ipPorts);
        void config(List<String> ipPorts,String username,String password);
        void setQuerySql(String sql);
        SearchResult executeQueryInfo();
    }

    public void addIndexName(String indexName,String indexType) {
        if(Validator.check(indexName)){indexParmsStatus.setIndexName(indexName);};
        if(Validator.check(indexType)){indexParmsStatus.setIndexType(indexType);};
    }

}
