package casia.isiteam.api.elasticsearch.operation.service.search;

import casia.isiteam.api.elasticsearch.common.enums.AggsLevel;
import casia.isiteam.api.elasticsearch.common.enums.FieldOccurs;
import casia.isiteam.api.elasticsearch.common.enums.GeoLevel;
import casia.isiteam.api.elasticsearch.common.enums.GeoQueryLevel;
import casia.isiteam.api.elasticsearch.common.status.IndexSearchBuilder;
import casia.isiteam.api.elasticsearch.common.vo.result.SearchResult;
import casia.isiteam.api.elasticsearch.common.vo.field.aggs.AggsFieldBuider;
import casia.isiteam.api.elasticsearch.common.vo.field.search.KeywordsCombine;
import casia.isiteam.api.elasticsearch.common.vo.field.RangeField;
import casia.isiteam.api.elasticsearch.common.vo.field.SortField;
import casia.isiteam.api.elasticsearch.operation.interfaces.ElasticSearchApi;
import casia.isiteam.api.elasticsearch.util.ExecuteResult;
import casia.isiteam.api.elasticsearch.util.LogUtil;
import casia.isiteam.api.elasticsearch.util.StringAppend;
import casia.isiteam.api.http.controller.CasiaHttpUtil;
import casia.isiteam.api.toolutil.Validator;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName: SearchServer
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/4/29
 * Email: zhiyou_wang@foxmail.com
 */
public class SearchServer extends ElasticSearchApi implements ElasticSearchApi.SearchApi {
    private Logger logger = LoggerFactory.getLogger( this.getClass());

    public void setIndexName(String indexName,String indexType) {
        addIndexName(indexName,indexType);
    }
    /**
     * clearn query parms
     */
    @Override
    public void reset(){
        indexSearchBuilder = new IndexSearchBuilder();
    };

    @Override
    public void setMatchAllQuery(){
        indexSearchBuilder.removeSearch(QUERY);
    };
    public void setFrom(int from) {
        indexSearchBuilder.putSearchCover(FROM,from);
    }
    public void setSize(int size) {
        indexSearchBuilder.putSearchCover(SIZE,size);
    }
    public void addSort(SortField... sortFields) {
        JSONArray sortOrderArray = a();
        for(SortField sortField: sortFields ){
            sortOrderArray = a(sortOrderArray,o(sortField.getField(),sortField.getSortOrder().getSymbolValue()));
        }
        indexSearchBuilder.putSearchCover(SORT,sortOrderArray);
    }
    public void setReturnField(Boolean isReturnAll) {
        if(isReturnAll == false){
            indexSearchBuilder.putSearchCover(_SOURCE,isReturnAll);
        }
    }
    public void setReturnField(FieldOccurs fieldOccurs, String ... fileds) {
        JSONObject filedJson = o();
        if( !indexSearchBuilder.getSearch().containsKey(_SOURCE) ){
            indexSearchBuilder.getSearch().put(_SOURCE,o());
        }
        if (indexSearchBuilder.getSearch().get(_SOURCE) instanceof Boolean) {
            if( indexSearchBuilder.getSearch().getBoolean(_SOURCE) == false ){
                return;
            }
        }
        filedJson = indexSearchBuilder.getSearch().getJSONObject(_SOURCE);
        if( !filedJson.containsKey(fieldOccurs.getSymbolValue()) ){
            filedJson.put(fieldOccurs.getSymbolValue(),a());
        }
        for(String filed: fileds ){
            filedJson.getJSONArray(fieldOccurs.getSymbolValue()).add(filed);
        }
        indexSearchBuilder.putSearchCover(_SOURCE,filedJson);
    }
    public void setHighlight(String pre_tags,String post_tags,String ... fileds){
        if( !Validator.check(fileds) ){
            return;
        }
        JSONObject jsonfields = o(
                o(PRE_TAGS, Validator.check(pre_tags)? pre_tags : MARK_S ),
                POST_TAGS,Validator.check(post_tags)? post_tags : MARK_E);
        for( String filed: fileds ){
            o(jsonfields,FIELDS,o(filed,o(o(FRAGMENT_SIZE,MAX_1000000), NUMBER_OF_FRAGMENTS,MAX_1000000)));
        }
        indexSearchBuilder.putSearchCover(HIGHLIGHT,jsonfields);
    }
    public void setMinScore(Float min_score){
        if( !Validator.check(min_score) ){
            return;
        }
        indexSearchBuilder.putSearchCover(MIN_SCORE,min_score);
    }
    public void openProfile(){
        indexSearchBuilder.putSearchCover(PROFILE,true);
        indexSearchBuilder.putCountCover(PROFILE,true);
    }
    @Override
    public void setRange(RangeField ... rangeFields) {
        for(RangeField filed : rangeFields ){
            JSONObject rangefiled = o(RANGE,o(filed.getField(),o(o(GTE,filed.getGte()),LTE,filed.getLte())));
            if( !indexSearchBuilder.addQueryBigBoolKeyArray(filed.getFieldOccurs().getIsMust()).
                    getQueryBigBool().getJSONArray(filed.getFieldOccurs().getIsMust()).stream().filter(s ->
                    JSONObject.parseObject(s.toString()).containsKey(RANGE) &&  JSONObject.parseObject(s.toString()).getString(RANGE) .equals(
                            rangefiled.getString(RANGE)
                    )
            ).findFirst().isPresent() ){
                indexSearchBuilder.getQueryBigBool().getJSONArray(filed.getFieldOccurs().getIsMust()).add(rangefiled);
            }
        }
    }
    @Override
    public void setFieldExistsFilter(FieldOccurs fieldOccurs,String ... fileds) {
        for(String filed : fileds ){
            JSONObject existsFiled = o(EXISTS,o(FIELD,filed));
            if( !indexSearchBuilder.addQueryBigBoolKeyArray(fieldOccurs.getIsMust()).getQueryBigBool().getJSONArray(fieldOccurs.getIsMust()).stream().filter(s ->
                    JSONObject.parseObject(s.toString()).containsKey(EXISTS) &&  JSONObject.parseObject(s.toString()).getString(EXISTS) .equals(
                            existsFiled.getString(EXISTS)
                    )
            ).findFirst().isPresent() ){
                indexSearchBuilder.getQueryBigBool().getJSONArray(fieldOccurs.getIsMust()).add(existsFiled);
            }
        }
    }

    public void setQueryKeyWords(KeywordsCombine ... keywordsCombines){
        for(KeywordsCombine keywordsCombine: keywordsCombines){
            JSONObject newShould = parsQueryKeyWords(o(),keywordsCombine);
            oAddaKey(indexSearchBuilder.getQueryBigBool(),SHOULD);
            if( !indexSearchBuilder.getQueryBigBool().getJSONArray(SHOULD).stream().filter(s->
                s.toString().equals( newShould.toString() )
            ).findFirst().isPresent() ){
                indexSearchBuilder.getQueryBigBool().getJSONArray(SHOULD).add(newShould);
                indexSearchBuilder.getQueryBigBool().put(MINIMUM_SHOULD_MATCH,indexSearchBuilder.getQueryBigBool().getJSONArray(SHOULD).size());
            }
        }
    }

    /**
     * pars translate query build keyword
     * @param jsono
     * @param keywordsCombine
     * @return
     */
    private JSONObject parsQueryKeyWords(JSONObject jsono,KeywordsCombine keywordsCombine){
        if( Validator.check(keywordsCombine) && Validator.check(keywordsCombine.getKeyWordsBuiders()) ){
            keywordsCombine.getKeyWordsBuiders().forEach(s->{
                String SM = Validator.check(s.getFieldOccurs()) && s.getFieldOccurs().getIsMust().equals(MUST_NOT) ? MUST_NOT : SHOULD;
                if( !Validator.check(s.getKeywordsCombines()) ){
                    JSONObject matchjson = o();

                    //关键词格式
                    if( Validator.check(s.getQueriesLevel()) ){
                        matchjson.put( s.getQueriesLevel().getLevel(),o(s.getField(),s.getKeyWord())) ;
                    }
                    //地理位置格式
                    else if( Validator.check(s.getGeoQueryLevel()) && Validator.check(s.getGeoQueryInfo()) ){
                        if( s.getGeoQueryLevel().getLevel() .equals(GeoQueryLevel.Polygon.getLevel()) ){
                            JSONArray jsonArray =a();
                            s.getGeoQueryInfo().getPolygon().forEach(lal->jsonArray.add(o(o(LAT,lal.getLat()),LON,lal.getLon())));
                            matchjson.put(s.getGeoQueryLevel().getLevel(),o(s.getField(),o(POINTS,jsonArray) ));
                        }else if( s.getGeoQueryLevel().getLevel() .equals(GeoQueryLevel.Box.getLevel()) ){
                            matchjson.put(s.getGeoQueryLevel().getLevel(),o(s.getField(),o()));
                            s.getGeoQueryInfo().getBox().forEach((k,v)->{
                                matchjson.getJSONObject(s.getGeoQueryLevel().getLevel()).getJSONObject(s.getField()).put(k,o(o(LAT,v.getLat()),LON,v.getLon()));
                            });
                        }else if( s.getGeoQueryLevel().getLevel() .equals(GeoQueryLevel.Distance.getLevel()) ){
                            matchjson.put(s.getGeoQueryLevel().getLevel(),o());
                            matchjson.getJSONObject(s.getGeoQueryLevel().getLevel()).put(DISTANCE, s.getGeoQueryInfo().getDistance() );
                            matchjson.getJSONObject(s.getGeoQueryLevel().getLevel()).put(s.getField(),o(o(LAT,s.getGeoQueryInfo().getDistanceGeo().getLat()),LON,s.getGeoQueryInfo().getDistanceGeo().getLon()));
                        }else if( s.getGeoQueryLevel().getLevel() .equals(GeoQueryLevel.DistanceRange.getLevel()) ){
                            matchjson.put(s.getGeoQueryLevel().getLevel(),o());
                            matchjson.getJSONObject(s.getGeoQueryLevel().getLevel()).put(FROM, s.getGeoQueryInfo().getFrom() );
                            matchjson.getJSONObject(s.getGeoQueryLevel().getLevel()).put(TO, s.getGeoQueryInfo().getTo() );
                            matchjson.getJSONObject(s.getGeoQueryLevel().getLevel()).put(s.getField(),o(o(LAT,s.getGeoQueryInfo().getDistanceGeo().getLat()),LON,s.getGeoQueryInfo().getDistanceGeo().getLon()));
                        }
                    }
                    //其他
                    else{
                        return;
                    }

                    //组装
                    if( keywordsCombine.getKeyWordsBuiders().size() == 1 && !SM.equals(MUST_NOT) ){
                        jsono.putAll(matchjson);
                        return;
                    }
                    oAddoKey(jsono,BOOL);
                    if( SM.equals(MUST_NOT) ){
                        oAddaKey(jsono.getJSONObject(BOOL),SHOULD);
                        JSONObject js = o(BOOL,o(MUST_NOT,matchjson));
                        if( !jsono.getJSONObject(BOOL).getJSONArray(SHOULD).stream().filter(a->
                                a.toString() .equals( js.toString() ) ).findFirst().isPresent()
                        ){
                            jsono.getJSONObject(BOOL).getJSONArray(SHOULD).add( js );
                        }
                    }else{
                        oAddaKey(jsono.getJSONObject(BOOL),SM);
                        if( !jsono.getJSONObject(BOOL).getJSONArray(SM).stream().filter(a->
                                a.toString() .equals( matchjson.toString() ) ).findFirst().isPresent()
                        ){
                            jsono.getJSONObject(BOOL).getJSONArray(SM).add( matchjson );
                        }
                    }
                }else {
                    s.getKeywordsCombines().forEach(d->{
                        oAddoKey(jsono,BOOL);
                        oAddaKey(jsono.getJSONObject(BOOL),SHOULD);
                        jsono.getJSONObject(BOOL).getJSONArray(SHOULD).add(0,o());
                        parsQueryKeyWords(jsono.getJSONObject(BOOL).getJSONArray(SHOULD).getJSONObject(0),d);
                    });
                }
            });
            if(jsono.containsKey(BOOL)){
                keywordsCombine.setMinimumMatch( keywordsCombine.getMinimumMatch()>0 && keywordsCombine.getMinimumMatch()<=jsono.getJSONObject(BOOL).getJSONArray(SHOULD).size() ?
                        keywordsCombine.getMinimumMatch() : jsono.getJSONObject(BOOL).getJSONArray(SHOULD).size() );
                jsono.getJSONObject(BOOL).put(MINIMUM_SHOULD_MATCH,keywordsCombine.getMinimumMatch());
            }
            return jsono;
        }else{
            return o();
        }
    }

    /**
     *  build Aggregations info
     * @param aggsFieldBuiders
     */
    public void setAggregations(AggsFieldBuider ... aggsFieldBuiders){
        for(AggsFieldBuider aggsFieldBuider : aggsFieldBuiders ){
            pareAggsFieldBuider(aggsFieldBuider,indexSearchBuilder.getAggs());
        }
    }

    private void pareAggsFieldBuider(AggsFieldBuider aggsFieldBuider,JSONObject object){
        //类型
        if( Validator.check(aggsFieldBuider.getCardinalitys()) ){
            aggsFieldBuider.getCardinalitys().forEach(s->{
                String newField = StringAppend.aggsFieldAppend(AggsLevel.Group,s.getField());
                if( !object.containsKey( newField) ){
                    object.put( newField ,
                            o(AggsLevel.Group.getLevel(),
                                    Validator.check(s.getPrecision()) ?
                                            o(o(FIELD,s.getField()),PRECISION_THRESHOLD,s.getPrecision()) :
                                            o(FIELD,s.getField()) ) );
                }
            });
        }
        //TOP 聚合字段信息
        if( Validator.check(aggsFieldBuider.getTermInfos()) ){
            aggsFieldBuider.getTermInfos().forEach(s->{
                String newField = StringAppend.aggsFieldAppend(AggsLevel.Term,s.getField());
                if( !object.containsKey( newField) ){
                    object.put( newField ,
                        o(AggsLevel.Term.getLevel(),
                            o(
                                o(
                                    o(
                                        o(
                                            o(FIELD,s.getField()),
                                            Validator.check(s.getSize()) ? SIZE : NONE,s.getSize()
                                        ),
                                        Validator.check(s.getShardSize()) ? SHARD_SIZE : NONE,s.getShardSize()
                                    ),
                                    Validator.check(s.getMinDocTotal()) ? MIN_DOC_COUNT : NONE,s.getMinDocTotal()
                                ),
                                Validator.check(s.getSortOrder()) ? ORDER : NONE ,o(_COUNT, Validator.check(s.getSortOrder()) ? s.getSortOrder().getSymbolValue() : NONE)
                            )
                        )
                    );
                    if( Validator.check(s.getAggsFieldBuider()) ){
                        o( object.getJSONObject(newField),AGGS,o());
                        pareAggsFieldBuider(s.getAggsFieldBuider(),object.getJSONObject(newField).getJSONObject(AGGS));
                    }
                }
            });
        }
        //date
        if( Validator.check(aggsFieldBuider.getDateInfos()) ){
            aggsFieldBuider.getDateInfos().forEach(s->{
                String newField = StringAppend.aggsFieldAppend(AggsLevel.Date,s.getField());
                if( !object.containsKey( newField) ){
                    object.put( newField ,
                            o(AggsLevel.Date.getLevel(),
                                o(
                                    o(
                                        o(
                                            o(FIELD,s.getField()),
                                            FORMAT,s.getFormat()
                                        ),
                                        INTERVAL,s.getInterval()
                                    ),
                                    Validator.check(s.getMinDocTotal()) ? MIN_DOC_COUNT : NONE,s.getMinDocTotal()
                                )
                            )
                    );
                    if( Validator.check(s.getAggsFieldBuider()) ){
                        o( object.getJSONObject(newField),AGGS,o());
                        pareAggsFieldBuider(s.getAggsFieldBuider(),object.getJSONObject(newField).getJSONObject(AGGS));
                    }
                }
            });
        }
        //Operation
        if( Validator.check(aggsFieldBuider.getOperationInfos()) ){
            aggsFieldBuider.getOperationInfos().forEach(s->{
                String newField = StringAppend.aggsFieldAppend(s.getOperationLevel().getLevel(),s.getField());
                if( !object.containsKey( newField) ){
                    object.put( newField ,
                            o(s.getOperationLevel().getLevel(),
                                    o( o(FIELD,s.getField()), Validator.check(s.getMissing()) ? MISSING : NONE,s.getMissing())
                            )
                    );
                }
            });
        }
        //Geo
        if( Validator.check(aggsFieldBuider.getGeoInfos()) ){
            aggsFieldBuider.getGeoInfos().forEach(s->{
                String newField = StringAppend.aggsFieldAppend(s.getGeoLevel().getLevel(),s.getField());
                if( !object.containsKey( newField) ){
                    object.put( newField ,
                            o(s.getGeoLevel().getLevel(),
                                    o( o(FIELD,s.getField()),
                                    s.getGeoLevel().getLevel().equals(GeoLevel.Bounds.getLevel()) && Validator.check(s.getWrap()) ? WRAP_LONGITUDE : NONE,s.getWrap())
                            )
                    );
                }
            });
        }
        //TopData
        if( Validator.check(aggsFieldBuider.getTopDatas()) ){
            aggsFieldBuider.getTopDatas().forEach(s->{
                String newField = StringAppend.aggsFieldAppend(AggsLevel.Top,HITS+DATA);
                if( !object.containsKey( newField) ){
                    JSONArray sorts = a();
                    s.getSortFields().stream().forEach(c->{
                        JSONObject json =o(c.getField(), o(ORDER,c.getSortOrder().getSymbolValue()));
                        if( sorts.contains(json) ){
                            sorts.add(json);
                        }
                    });
                    JSONObject _source= o();
                    s.getExcludesFields().stream().forEach(c->{
                        if( _source.containsKey(EXCLUDES) ){
                            _source.getJSONArray(EXCLUDES).add(c);
                        }else{
                            _source.put(EXCLUDES,a(c));
                        }
                    });
                    s.getIncludesFields().stream().forEach(c->{
                        if( _source.containsKey(INCLUDES) ){
                            _source.getJSONArray(INCLUDES).add(c);
                        }else{
                            _source.put(INCLUDES,a(c));
                        }
                    });

                    object.put( newField ,
                            o(AggsLevel.Top.getLevel(),
                                    o(
                                        o(
                                                o(Validator.check(s.getSize()) ? SIZE : NONE,s.getSize()),
                                                Validator.check(sorts) ? SORT : NONE,sorts
                                        ),
                                        Validator.check(_source) ? _SOURCE : NONE,_source
                                    )
                            )
                    );
                }
            });
        }
    }

    /**
     * execute query
     */
    @Override
    public SearchResult executeQueryInfo() {
        if( !Validator.check( indexSearchBuilder.getSearch() ) ){
            logger.debug(LogUtil.compositionLogEmpty("query parms"));
        }
        String curl =curl(indexParmsStatus.getUrl(),indexParmsStatus.getIndexName(),indexParmsStatus.getIndexType(),_SEARCH);
        logger.debug(LogUtil.compositionLogCurl(curl,indexSearchBuilder.getSearch()) );
        String resultStr = new CasiaHttpUtil().post(curl,indexParmsStatus.getHeards(),null,indexSearchBuilder.getSearch().toString() );
        return ExecuteResult.executeQueryResult(o(resultStr));
    }
    /**
     * execute query
     * scroll_time example：1m
     * scroll_id  id
     */
    @Override
    public SearchResult executeScrollInfo(String scroll_time,String scroll_id) {
        if( !Validator.check( scroll_time ) ){
            logger.warn(LogUtil.compositionLogEmpty("scroll_time parms"));
            return new SearchResult();
        }
        String curl =curlSymbol( curl(indexParmsStatus.getUrl(),!Validator.check(scroll_id)?indexParmsStatus.getIndexName():"",!Validator.check(scroll_id)?indexParmsStatus.getIndexType():"", _SEARCH),Validator.check(scroll_id)?SLASH:QUESTION  ,
        Validator.check(scroll_id)?SCROLL:SCROLL+EQUAL+scroll_time);
        String bodys=Validator.check(scroll_id)?o(o(SCROLL,scroll_time),SCROLL_ID,scroll_id).toString():indexSearchBuilder.getSearch().toString();
        logger.debug(LogUtil.compositionLogCurl(curl,bodys));
        String resultStr = new CasiaHttpUtil().post(curl,
                indexParmsStatus.getHeards(),
                null,bodys
                );
        return ExecuteResult.executeQueryResult(o(resultStr));
    }
    /**
     * execute query
     */
    @Override
    public SearchResult executeQueryTotal() {
        String curl=curl(indexParmsStatus.getUrl(),indexParmsStatus.getIndexName(),indexParmsStatus.getIndexType(),_COUNT);
        String bodys = os(QUERY,indexSearchBuilder.getQuery().toString());
        logger.debug(LogUtil.compositionLogCurl(curl,bodys) );
        String resultStr = new CasiaHttpUtil().post(curl,indexParmsStatus.getHeards(),null,bodys);
        return ExecuteResult.executeQueryTotal(o(resultStr));
    }
    /**
     * execute query
     */
    @Override
    public SearchResult executeAggsInfo() {
        if( !Validator.check( indexSearchBuilder.getAggs() ) ){
            logger.warn(LogUtil.compositionLogEmpty("aggs parms"));
            return new SearchResult();
        }
        String curl=curl(indexParmsStatus.getUrl(),indexParmsStatus.getIndexName(),indexParmsStatus.getIndexType(),_SEARCH);
        logger.debug(LogUtil.compositionLogCurl(curl,indexSearchBuilder.getCount().toString() ) );
        String resultStr = new CasiaHttpUtil().post(curl,indexParmsStatus.getHeards(),null,indexSearchBuilder.getCount().toString());
        return ExecuteResult.executeAggsResult(o(resultStr));
    }
}
