package casia.isiteam.api.elasticsearch.controller;

import casia.isiteam.api.elasticsearch.common.enums.FieldOccurs;
import casia.isiteam.api.elasticsearch.common.vo.field.aggs.AggsFieldBuider;
import casia.isiteam.api.elasticsearch.common.vo.result.SearchResult;
import casia.isiteam.api.elasticsearch.common.vo.field.search.KeywordsCombine;
import casia.isiteam.api.elasticsearch.common.vo.field.RangeField;
import casia.isiteam.api.elasticsearch.common.vo.field.SortField;
import casia.isiteam.api.elasticsearch.operation.interfaces.ElasticSearchApi;
import casia.isiteam.api.elasticsearch.router.ApiRouter;
import casia.isiteam.api.toolutil.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * ClassName: CasiaEsSearch
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/4/27
 * Email: zhiyou_wang@foxmail.com
 */
public class CasiaEsSearch {
    private Logger logger = LoggerFactory.getLogger( this.getClass());

    private ElasticSearchApi.SearchApi searchApi;
    public CasiaEsSearch(String driverName){
        searchApi = ApiRouter.getSearchRouter();
        searchApi.config(driverName);
    }

    public CasiaEsSearch(List<String> ipPorts){
        searchApi = ApiRouter.getSearchRouter();
        searchApi.config(ipPorts);
    }

    public CasiaEsSearch(List<String> ipPorts,String username,String password){
        searchApi = ApiRouter.getSearchRouter();
        searchApi.config(ipPorts,username,password);
    }
    /**
     * set indexName and indexType
     * @param indexName
     * @param indexType
     */
    public void setIndexName(String indexName,String indexType) {
        searchApi.setIndexName(indexName,indexType);
    }
    public void setIndexName(String indexName) {
        searchApi.setIndexName(indexName,null);
    }
    public void setIndexType(String indexType) {
        searchApi.setIndexName(null,indexType);
    }
    /**
     * reset query
     */
    public CasiaEsSearch reset() {
        searchApi.reset();
        return this;
    }
    /**
     * execute scroll info
     * scroll_time: example：1m
     * scroll_id: First request is null
     */
    public SearchResult executeScrollInfo(String scroll_time,String scroll_id){
        return searchApi.executeScrollInfo(scroll_time,scroll_id);
    }
    /**
     * execute query info
     */
    public SearchResult executeQueryInfo() {
        return searchApi.executeQueryInfo();
    }
    /**
     * execute query total
     */
    public SearchResult executeQueryTotal() {
        return searchApi.executeQueryTotal();
    }
    /**
     * execute query total
     */
    @Deprecated
    public SearchResult executeAggsInfo() {
        return searchApi.executeAggsInfo();
    }
    /**
     * set query all
     */
    public CasiaEsSearch setMatchAllQuery() {
        searchApi.setMatchAllQuery();
        return this;
    }
    /**
     * set query from
     * @param from
     * @return
     */
    public CasiaEsSearch setFrom(int from) {
        searchApi.setFrom(Validator.check(from) ? from : 0);
        return this;
    }
    /**
     * set query return size
     * @param size
     * @return
     */
    public CasiaEsSearch setSize(int size) {
        searchApi.setSize(Validator.check(size) ? size : 10);
        return this;
    }

    /**
     * set sort filed
     * @param sortFields
     * @return
     */
    public CasiaEsSearch addSort(SortField ... sortFields) {
        if( Validator.check(sortFields) ){
            searchApi.addSort(sortFields);
        }
        return this;
    }
    /**
     * set sort filed
     * @param isReturnAll  true OR false
     * @return
     */
    public CasiaEsSearch setReturnField(Boolean isReturnAll) {
        if( Validator.check(isReturnAll) ){
            searchApi.setReturnField(isReturnAll);
        }
        return this;
    }
    /**
     * set sort filed
     * @param fields
     * @return
     */
    public CasiaEsSearch setReturnField(String ... fields) {
        if( Validator.check(fields) ){
            searchApi.setReturnField(FieldOccurs.INCLUDES, fields);
        }
        return this;
    }
    /**
     * set sort filed
     * @param fieldOccurs FieldOccurs.INCLUDES  OR   FieldOccurs.EXCLUDES
     * @param fields
     * @return
     */
    public CasiaEsSearch setReturnField(FieldOccurs fieldOccurs, String ... fields) {
        if( Validator.check(fields) ){
            searchApi.setReturnField(fieldOccurs, fields);
        }
        return this;
    }
    /**
     * set match Highlight
     * fields: must query keyword by analyzer field
     * @param pre_tags  example：<mark>
     * @param post_tags example：</mark>
     * @param fields
     * @return
     */
    public CasiaEsSearch setHighlight(String pre_tags,String post_tags, String ... fields) {
        if( Validator.check(fields) ){
            searchApi.setHighlight(pre_tags,post_tags,fields);
        }
        return this;
    }
    /**
     * set filter min score
     * not return score when set field sort
     * @param min_score  example：0.5
     * @return
     */
    public CasiaEsSearch setMinScore(Float min_score){
        if( Validator.check(min_score) ){
            searchApi.setMinScore(min_score);
        }
        return this;
    }
    /**
     * open print The Profile API provides detailed timing
     * @return
     */
    public CasiaEsSearch openProfile(){
        searchApi.openProfile();
        return this;
    }
    /**
     * open return Scroll info
     * @param scroll_time : active time, 2m
     * @return
     */
    public CasiaEsSearch setScroll(String scroll_time){
        searchApi.openScroll(scroll_time);
        return this;
    }
    /**
     * set filed range
     * @param rangeFields
     * @return
     */
    public CasiaEsSearch setRange(RangeField ... rangeFields) {
        if( Validator.check(rangeFields) ){
            searchApi.setRange(rangeFields);
        }
        return this;
    }
    /**
     * filter missing filed
     * @param fields
     * @return
     */
    public CasiaEsSearch setMissingFilter(String ... fields) {
        if( Validator.check(fields) ){
            searchApi.setFieldExistsFilter(FieldOccurs.EXCLUDES,fields);
        }
        return this;
    }
    /**
     *  filter exists filed
     * @param fields
     * @return
     */
    public CasiaEsSearch setExistsFilter(String ... fields) {
        if( Validator.check(fields) ){
            searchApi.setFieldExistsFilter(FieldOccurs.INCLUDES,fields);
        }
        return this;
    }
    /**
     *  set query keyword
     * @param keywordsCombines
     * @return
     */
    public CasiaEsSearch setQueryKeyWords(KeywordsCombine ... keywordsCombines) {
        if( Validator.check(keywordsCombines) ){
            searchApi.setQueryKeyWords(keywordsCombines);
        }
        return this;
    }
    /**
     *  set query keyword
     * @param aggsFieldBuiders
     * @return
     */
    public CasiaEsSearch setAggregations(AggsFieldBuider... aggsFieldBuiders) {
        if( Validator.check(aggsFieldBuiders) ){
            searchApi.setAggregations(aggsFieldBuiders);
        }
        return this;
    }
}
