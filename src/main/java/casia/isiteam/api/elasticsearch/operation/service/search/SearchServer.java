package casia.isiteam.api.elasticsearch.operation.service.search;

import casia.isiteam.api.elasticsearch.common.enums.AggsLevel;
import casia.isiteam.api.elasticsearch.common.enums.FieldOccurs;
import casia.isiteam.api.elasticsearch.common.enums.GeoLevel;
import casia.isiteam.api.elasticsearch.common.enums.GeoQueryLevel;
import casia.isiteam.api.elasticsearch.common.status.IndexSearchBuilder;
import casia.isiteam.api.elasticsearch.common.vo.field.search.KeyWordsBuider;
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
import casia.isiteam.api.toolutil.regex.CasiaRegexUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

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
    public void openScroll(String scroll_time){
        indexSearchBuilder.putScrollTime(scroll_time);
    };
    @Override
    public void setRange(RangeField ... rangeFields) {
        for(RangeField filed : rangeFields ){
            JSONObject rangefiled = filed.getField().contains(DOT2)?
                    o( NESTED, o(o(PATH,filed.getField().substring(0,filed.getField().lastIndexOf(DOT2))), QUERY,o(BOOL, o( MUST,a( o(RANGE,o(filed.getField(),o(o(filed.getIncludeLower()?GTE:GT,filed.getGte()),filed.getIncludeUpper()?LTE:LT,filed.getLte()))) )))) ) :
                    o(RANGE,o(filed.getField(),o(o(filed.getIncludeLower()?GTE:GT,filed.getGte()),filed.getIncludeUpper()?LTE:LT,filed.getLte())));
            String isNESTED = filed.getField().contains(DOT2)? NESTED : RANGE ;
            if( !indexSearchBuilder.addQueryBigBoolKeyArray(filed.getFieldOccurs().getIsMust()).
                    getQueryBigBool().getJSONArray(filed.getFieldOccurs().getIsMust()).stream().filter(s ->
                            JSONObject.parseObject(s.toString()).containsKey(isNESTED) &&  JSONObject.parseObject(s.toString()).getString(isNESTED) .equals(
                                    rangefiled.getString(isNESTED)
                            )
                    ).findFirst().isPresent() ){
                indexSearchBuilder.getQueryBigBool().getJSONArray(filed.getFieldOccurs().getIsMust()).add(rangefiled);
            }
        }
    }
    @Override
    public void setFieldExistsFilter(FieldOccurs fieldOccurs,String ... fileds) {
        for(String filed : fileds ){
            JSONObject existsFiled = filed.contains(DOT2)? o( NESTED, o(o(PATH,filed.substring(0,filed.lastIndexOf(DOT2))), QUERY,o(BOOL, o( MUST,a( o(EXISTS,o(FIELD,filed)) )))) ) : o(EXISTS,o(FIELD,filed));
            String isNESTED = filed.contains(DOT2)? NESTED : EXISTS ;
            if( !indexSearchBuilder.addQueryBigBoolKeyArray(fieldOccurs.getIsMust()).getQueryBigBool().getJSONArray(fieldOccurs.getIsMust()).stream().filter(s ->
                    JSONObject.parseObject(s.toString()).containsKey(isNESTED) &&  JSONObject.parseObject(s.toString()).getString(isNESTED) .equals(
                            existsFiled.getString(isNESTED)
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
                String newField = StringAppend.aggsFieldAppend(AggsLevel.Group,s.getField().replaceAll(SIGN,DOT),s.getAlias());
                if( !object.containsKey(newField) ){
                    JSONObject cardinality = o(AggsLevel.Group.getLevel(),Validator.check(s.getPrecision()) ? o(o(FIELD,s.getField().replaceAll(SIGN,DOT)),PRECISION_THRESHOLD,s.getPrecision()):o(FIELD,s.getField().replaceAll(SIGN,DOT)) );
                    if( s.getField().contains(DOT2) ){
                        String[] fileds = s.getField().split(DOT);
                        JSONObject aggsn = o();
                        JSONObject child_aggs = o();
                        String path_filed = s.getField();
                        for (int i=fileds.length-1;i>=0;i--) {
                            path_filed =fileds.length-1 == i ? path_filed: path_filed.substring(0,path_filed.lastIndexOf(DOT2));
                            child_aggs = JSONObject.parseObject(aggsn.toString());aggsn.clear();
                            aggsn = o(newField,i==fileds.length-1 ? cardinality : o(o(AGGS,child_aggs),NESTED,o(PATH,path_filed.replaceAll(SIGN,DOT))));
                        }
                        object.put(newField,aggsn.getJSONObject(newField));
                    }else{
                        object.put(newField ,cardinality);
                    }
                };
            });
        }
        //Term 聚合字段信息
        if( Validator.check(aggsFieldBuider.getTermInfos()) ){
            aggsFieldBuider.getTermInfos().forEach(s->{
                String newField = StringAppend.aggsFieldAppend(AggsLevel.Term,s.getField().replaceAll(SIGN,DOT),s.getAlias());
                if( !object.containsKey( newField) ){
                    JSONObject terms = o(AggsLevel.Term.getLevel(),
                            o(
                                    o(
                                            o(
                                                    o(
                                                            o(FIELD,s.getField().replaceAll(SIGN,DOT)),
                                                            Validator.check(s.getSize()) ? SIZE : NONE,s.getSize()
                                                    ),
                                                    Validator.check(s.getShardSize()) ? SHARD_SIZE : NONE,s.getShardSize()
                                            ),
                                            Validator.check(s.getMinDocTotal()) ? MIN_DOC_COUNT : NONE,s.getMinDocTotal()
                                    ),
                                    Validator.check(s.getSortOrder()) ? ORDER : NONE ,o(_COUNT, Validator.check(s.getSortOrder()) ? s.getSortOrder().getSymbolValue() : NONE)
                            )
                    );
//
                    if( s.getField().contains(DOT2) ){
                        String[] fileds = s.getField().split(DOT);
                        JSONObject aggsn = o();
                        JSONObject child_aggs = o();
                        String path_filed = s.getField();
                        for (int i=fileds.length-1;i>=0;i--) {
                            path_filed =fileds.length-1 == i ? path_filed: path_filed.substring(0,path_filed.lastIndexOf(DOT2));
                            child_aggs = JSONObject.parseObject(aggsn.toString());aggsn.clear();
                            aggsn = o(newField,i==fileds.length-1 ? terms : o(o(AGGS,child_aggs),NESTED,o(PATH,path_filed.replaceAll(SIGN,DOT))));
                            if( fileds.length-1 == i && Validator.check(s.getAggsFieldBuider())){
                                o(aggsn.getJSONObject(newField),AGGS,o());
                                pareAggsFieldBuider(s.getAggsFieldBuider(),aggsn.getJSONObject(newField).getJSONObject(AGGS));
                            }
                        }
                        if( !object.containsKey(newField) ){
                            object.put(newField,aggsn.getJSONObject(newField));
                        }
                    }else{
                        object.put(newField ,terms);
                        if( Validator.check(s.getAggsFieldBuider()) ){
                            o( object.getJSONObject(newField),AGGS,o());
                            pareAggsFieldBuider(s.getAggsFieldBuider(),object.getJSONObject(newField).getJSONObject(AGGS));
                        }
                    }
                }
            });
        }
        //date
        if( Validator.check(aggsFieldBuider.getDateInfos()) ){
            aggsFieldBuider.getDateInfos().forEach(s->{
                String newField = StringAppend.aggsFieldAppend(AggsLevel.Date,s.getField().replaceAll(SIGN,DOT),s.getAlias());
                JSONObject  date_histogram = o(AggsLevel.Date.getLevel(),o(o(o(o(o(FIELD,s.getField().replaceAll(SIGN,DOT)),FORMAT,s.getFormat()),INTERVAL,s.getInterval()),Validator.check(s.getMinDocTotal()) ? MIN_DOC_COUNT : NONE,s.getMinDocTotal()),EXTENDED_BOUNDS,o(o( Validator.check(s.getMinDate()) ? MIN :NONE,s.getMinDate()),Validator.check(s.getMaxDate()) ? MAX :NONE,s.getMaxDate() )));
                if( !object.containsKey( newField) ){
                    if(s.getField().contains(DOT2) ){
                        String[] fileds = s.getField().split(DOT);
                        JSONObject aggsn = o();
                        JSONObject child_aggs = o();
                        String path_filed = s.getField();
                        for (int i=fileds.length-1;i>=0;i--) {
                            path_filed =fileds.length-1 == i ? path_filed: path_filed.substring(0,path_filed.lastIndexOf(DOT2));
                            child_aggs = JSONObject.parseObject(aggsn.toString());aggsn.clear();
                            aggsn = o(newField,i==fileds.length-1 ? date_histogram : o(o(AGGS,child_aggs),NESTED,o(PATH,path_filed.replaceAll(SIGN,DOT))));
                            if( fileds.length-1 == i && Validator.check(s.getAggsFieldBuider())){
                                o(aggsn.getJSONObject(newField),AGGS,o());
                                pareAggsFieldBuider(s.getAggsFieldBuider(),aggsn.getJSONObject(newField).getJSONObject(AGGS));
                            }
                        }
                        if( !object.containsKey(newField) ){
                            object.put(newField,aggsn.getJSONObject(newField));
                        }
                    }else {
                        object.put(newField ,date_histogram);
                        if( Validator.check(s.getAggsFieldBuider()) ){
                            o( object.getJSONObject(newField),AGGS,o());
                            pareAggsFieldBuider(s.getAggsFieldBuider(),object.getJSONObject(newField).getJSONObject(AGGS));
                        }
                    }
                }
            });
        }
        //Operation
        if( Validator.check(aggsFieldBuider.getOperationInfos()) ){
            aggsFieldBuider.getOperationInfos().forEach(s->{
                String newField = StringAppend.aggsFieldAppend(s.getOperationLevel().getLevel(),s.getField().replaceAll(SIGN,DOT),s.getAlias());
                if( !object.containsKey( newField) ){
                    JSONObject operation =  o(s.getOperationLevel().getLevel(),o( o(FIELD,s.getField().replaceAll(SIGN,DOT)), Validator.check(s.getMissing()) ? MISSING : NONE,s.getMissing()));
                    if( s.getField().contains(DOT2) ){
                        String[] fileds = s.getField().split(DOT);
                        JSONObject aggsn = o();
                        JSONObject child_aggs = o();
                        String path_filed = s.getField();
                        for (int i=fileds.length-1;i>=0;i--) {
                            path_filed =fileds.length-1 == i ? path_filed: path_filed.substring(0,path_filed.lastIndexOf(DOT2));
                            child_aggs = JSONObject.parseObject(aggsn.toString());aggsn.clear();
                            aggsn = o(newField,i==fileds.length-1 ? operation : o(o(AGGS,child_aggs),NESTED,o(PATH,path_filed.replaceAll(SIGN,DOT))));
                        }
                        object.put(newField,aggsn.getJSONObject(newField));
                    }else{
                        object.put(newField ,operation);
                    }
                }
            });
        }
        //Geo
        if( Validator.check(aggsFieldBuider.getGeoInfos()) ){
            aggsFieldBuider.getGeoInfos().forEach(s->{
                String newField = StringAppend.aggsFieldAppend(s.getGeoLevel().getLevel(),s.getField().replaceAll(SIGN,DOT),s.getAlias());
                if( !object.containsKey( newField) ){
                    JSONObject geo = o(s.getGeoLevel().getLevel(),
                            o( o(FIELD,s.getField().replaceAll(SIGN,DOT)),
                                    s.getGeoLevel().getLevel().equals(GeoLevel.Bounds.getLevel()) && Validator.check(s.getWrap()) ? WRAP_LONGITUDE : NONE,s.getWrap())
                    );
                    if( s.getField().contains(DOT2) ){
                        String[] fileds = s.getField().split(DOT);
                        JSONObject aggsn = o();
                        JSONObject child_aggs = o();
                        String path_filed = s.getField();
                        for (int i=fileds.length-1;i>=0;i--) {
                            path_filed =fileds.length-1 == i ? path_filed: path_filed.substring(0,path_filed.lastIndexOf(DOT2));
                            child_aggs = JSONObject.parseObject(aggsn.toString());aggsn.clear();
                            aggsn = o(newField,i==fileds.length-1 ? geo : o(o(AGGS,child_aggs),NESTED,o(PATH,path_filed.replaceAll(SIGN,DOT))));
                        }
                        object.put(newField,aggsn.getJSONObject(newField));
                    }else{
                        object.put( newField ,geo);
                    }
                }
            });
        }
        //TopData
        if( Validator.check(aggsFieldBuider.getTopDatas()) ){
            aggsFieldBuider.getTopDatas().forEach(s->{
                String newField = StringAppend.aggsFieldAppend(AggsLevel.Top,Validator.check(s.getAlias()) ? s.getAlias() : HITS+DATA );
                if( !object.containsKey( newField) ){
                    JSONArray sorts = a();
                    s.getSortFields().stream().forEach(c->{
                        JSONObject json =o(c.getField().replaceAll(SIGN,DOT), o(ORDER,c.getSortOrder().getSymbolValue()));
                        if( !sorts.contains(json) ){
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
        //Price 范围文档数
        if( Validator.check(aggsFieldBuider.getPriceInfos()) ){
            aggsFieldBuider.getPriceInfos().forEach(s->{
                String newField = StringAppend.aggsFieldAppend(AggsLevel.Price,s.getField().replaceAll(SIGN,DOT),s.getAlias());
                if( !object.containsKey( newField) ){
                    JSONArray array = a();
                    s.getRanges().forEach(r->{
                        String[] rs = r.split(CROSS);
                        if( Validator.check(rs) && rs.length ==1){
                            JSONObject ob =o(FROM,Long.parseLong(rs[0]));
                            if(!array.contains(ob)){array.add(ob);}
                        }else  if( Validator.check(rs) && rs.length ==2){
                            JSONObject ss= o();
                            try {
                                long from_ = Long.parseLong(rs[0]);
                                o(ss,FROM, from_);
                            }catch (Exception e){}
                            try {
                                long to_ = Long.parseLong(rs[1]);
                                o(ss,TO, to_);
                            }catch (Exception e){}
                            if(Validator.check(ss) && !array.contains(ss)){array.add(ss);}
                        }
                    });
                    JSONObject price = o(AggsLevel.Price.getLevel(), o(o(o(FIELD,s.getField().replaceAll(SIGN,DOT)),KEYED,s.getKeyed()),RANGES,array));
                    if( s.getField().contains(DOT2) ){
                        String[] fileds = s.getField().split(DOT);
                        JSONObject aggsn = o();
                        JSONObject child_aggs = o();
                        String path_filed = s.getField();
                        for (int i=fileds.length-1;i>=0;i--) {
                            path_filed =fileds.length-1 == i ? path_filed: path_filed.substring(0,path_filed.lastIndexOf(DOT2));
                            child_aggs = JSONObject.parseObject(aggsn.toString());aggsn.clear();
                            aggsn = o(newField,i==fileds.length-1 ? price : o(o(AGGS,child_aggs),NESTED,o(PATH,path_filed.replaceAll(SIGN,DOT))));
                            if( fileds.length-1 == i && Validator.check(s.getAggsFieldBuider())){
                                o(aggsn.getJSONObject(newField),AGGS,o());
                                pareAggsFieldBuider(s.getAggsFieldBuider(),aggsn.getJSONObject(newField).getJSONObject(AGGS));
                            }
                        }
                        if( !object.containsKey(newField) ){
                            object.put(newField,aggsn.getJSONObject(newField));
                        }
                    }else{
                        object.put(newField,price);
                        if( Validator.check(s.getAggsFieldBuider()) ){
                            o( object.getJSONObject(newField),AGGS,o());
                            pareAggsFieldBuider(s.getAggsFieldBuider(),object.getJSONObject(newField).getJSONObject(AGGS));
                        }
                    }
                }
            });
        }
        //IP 范围文档数
        if( Validator.check(aggsFieldBuider.getIpRangeInfos()) ){
            aggsFieldBuider.getIpRangeInfos().forEach(s->{
                String newField = StringAppend.aggsFieldAppend(AggsLevel.IPRange,s.getField().replaceAll(SIGN,DOT),s.getAlias());
                if( !object.containsKey( newField) ){
                    JSONArray array = a();
                    s.getRanges().forEach(r->{
                        if( Validator.check(CasiaRegexUtil.matchCIDR(r)) ){
                            JSONObject ob =o(MASK,r);
                            if( !array.contains(ob) ){
                                array.add(ob);
                            }
                        }else{
                            String[] rs = r.split(CROSS);
                            if( Validator.check(rs) && rs.length ==1){
                                JSONObject ob =o(FROM,rs[0]);
                                if(!array.contains(ob)){array.add(ob);}
                            }else  if( Validator.check(rs) && rs.length ==2){
                                JSONObject ss =o();
                                if( Validator.check(CasiaRegexUtil.matchIP(rs[0])) ){
                                    ss.put(FROM,rs[0]);
                                }
                                if( Validator.check(CasiaRegexUtil.matchIP(rs[1])) ){
                                    ss.put(TO,rs[1]);
                                }
                                if(Validator.check(ss) && !array.contains(ss)){array.add(ss);}
                            }
                        }
                    });
                    JSONObject range = o(AggsLevel.IPRange.getLevel(),o(o(o(FIELD,s.getField().replaceAll(SIGN,DOT)),KEYED,s.getKeyed()),RANGES,array));
                    if( s.getField().contains(DOT2) ){
                        String[] fileds = s.getField().split(DOT);
                        JSONObject aggsn = o();
                        JSONObject child_aggs = o();
                        String path_filed = s.getField();
                        for (int i=fileds.length-1;i>=0;i--) {
                            path_filed =fileds.length-1 == i ? path_filed: path_filed.substring(0,path_filed.lastIndexOf(DOT2));
                            child_aggs = JSONObject.parseObject(aggsn.toString());aggsn.clear();
                            aggsn = o(newField,i==fileds.length-1 ? range : o(o(AGGS,child_aggs),NESTED,o(PATH,path_filed.replaceAll(SIGN,DOT))));
                            if( fileds.length-1 == i && Validator.check(s.getAggsFieldBuider())){
                                o(aggsn.getJSONObject(newField),AGGS,o());
                                pareAggsFieldBuider(s.getAggsFieldBuider(),aggsn.getJSONObject(newField).getJSONObject(AGGS));
                            }
                        }
                        if( !object.containsKey(newField) ){
                            object.put(newField,aggsn.getJSONObject(newField));
                        }
                    }else{
                        object.put( newField ,range);
                        if( Validator.check(s.getAggsFieldBuider()) ){
                            o( object.getJSONObject(newField),AGGS,o());
                            pareAggsFieldBuider(s.getAggsFieldBuider(),object.getJSONObject(newField).getJSONObject(AGGS));
                        }
                    }
                }
            });
        }
        //地理网格文档数
        if( Validator.check(aggsFieldBuider.getGridInfos()) ){
            aggsFieldBuider.getGridInfos().forEach(s->{
                String newField = StringAppend.aggsFieldAppend(AggsLevel.Grid,s.getField().replaceAll(SIGN,DOT),s.getAlias());
                if( !object.containsKey( newField) ){
                    JSONObject grid = o(AggsLevel.Grid.getLevel(), o( o(FIELD,s.getField().replaceAll(SIGN,DOT)), PRECISION,s.getPrecision()));
                    if( s.getField().contains(DOT2) ){
                        String[] fileds = s.getField().split(DOT);
                        JSONObject aggsn = o();
                        JSONObject child_aggs = o();
                        String path_filed = s.getField();
                        for (int i=fileds.length-1;i>=0;i--) {
                            path_filed =fileds.length-1 == i ? path_filed: path_filed.substring(0,path_filed.lastIndexOf(DOT2));
                            child_aggs = JSONObject.parseObject(aggsn.toString());aggsn.clear();
                            aggsn = o(newField,i==fileds.length-1 ? grid : o(o(AGGS,child_aggs),NESTED,o(PATH,path_filed.replaceAll(SIGN,DOT))));
                            if( fileds.length-1 == i && Validator.check(s.getAggsFieldBuider())){
                                o(aggsn.getJSONObject(newField),AGGS,o());
                                pareAggsFieldBuider(s.getAggsFieldBuider(),aggsn.getJSONObject(newField).getJSONObject(AGGS));
                            }
                        }
                        if( !object.containsKey(newField) ){
                            object.put(newField,aggsn.getJSONObject(newField));
                        }
                        return;
                    }else{
                        object.put(newField ,grid);
                    }
                }
                if( Validator.check(s.getAggsFieldBuider()) ){
                    o( object.getJSONObject(newField),AGGS,o());
                    pareAggsFieldBuider(s.getAggsFieldBuider(),object.getJSONObject(newField).getJSONObject(AGGS));
                }
            });
        }
        //KeywordsCombines
        if( Validator.check(aggsFieldBuider.getKeywordsCombines()) ){
            aggsFieldBuider.getKeywordsCombines().forEach(s->{
                JSONObject newShould = parsQueryKeyWords(o(),s);
                if( Validator.check(newShould) ){
                    String keyName=s.getKeyName();
                    if( !Validator.check(s.getKeyName()) ){
                        for(KeyWordsBuider keyWordsBuider:s.getKeyWordsBuiders()){
                            keyName=keyName+ (Validator.check(keyName)?BLANK:NONE)+getKeyString(keyWordsBuider);
                        };
                    }
                    String newField = StringAppend.aggsFieldAppend(AggsLevel.KeyWord,keyName);
                    if( !object.containsKey( newField) ){
                        object.put( newField ,o(AggsLevel.KeyWord.getLevel(),o(AggsLevel.KeyWord.getLevel(),o(keyName,newShould))));
                    }
                    if( Validator.check(s.getAggsFieldBuider()) ){
                        o( object.getJSONObject(newField),AGGS,o());
                        pareAggsFieldBuider(s.getAggsFieldBuider(),object.getJSONObject(newField).getJSONObject(AGGS));
                    }
                }
            });
        }
        //Matrix Info
        if( Validator.check(aggsFieldBuider.getMatrixInfos()) ){
            aggsFieldBuider.getMatrixInfos().forEach(s->{
                String newField = StringAppend.aggsFieldAppend(AggsLevel.Matrix,MATRIXINFO);
                if( !object.containsKey(newField) ){
                    object.put( newField , o(MATRIX_STATS,o(o(FIELDS,a(s.getFields())),Validator.check(s.getMissing()) ? MISSING :NONE,o(INCOME,s.getMissing()))) );
                }
            });
        }
    }
    private String getKeyString(KeyWordsBuider keyWordsBuider){
        String keyString = NONE;
        if( Validator.check(keyWordsBuider.getKeywordsCombines() )){
            for(KeywordsCombine s:keyWordsBuider.getKeywordsCombines()){
                if( Validator.check(s.getKeyWordsBuiders()) ){
                    for(KeyWordsBuider c:s.getKeyWordsBuiders()){
                        keyString =keyString+ (Validator.check(keyString)?BLANK:NONE)+getKeyString(c);
                    }
                }
            }
        }else{
            keyString=keyString+(Validator.check(keyString)?BLANK:NONE)+ keyWordsBuider.getFieldOccurs().getIsExist()+
                    keyWordsBuider.getField()+COLON+keyWordsBuider.getKeyWord();
        }
        return keyString;
    }


    /**
     * execute query
     */
    @Override
    public SearchResult executeQueryInfo() {
        if( !Validator.check( indexSearchBuilder.getSearch() ) ){
            logger.warn(LogUtil.compositionLogEmpty("query parms"));
        }
        String curl =curl(indexParmsStatus.getUrl(),indexParmsStatus.getIndexName(),indexParmsStatus.getIndexType(),_SEARCH);
        if( Validator.check(indexSearchBuilder.getScrollTime()) ){
            curl =curlSymbol(curlSymbol(curlSymbol(curl,QUESTION,PRETTY),AND,SCROLL),EQUAL,indexSearchBuilder.getScrollTime());
        }
//        logger.debug(LogUtil.compositionLogCurl(curl,indexSearchBuilder.getSearch()) );
        if(debugInfo()){
            logger.info(LogUtil.compositionLogCurl(curl,indexSearchBuilder.getSearch()) );
        }
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
            SearchResult searchResult= new SearchResult();
            searchResult.setStatus(false);
            return searchResult;
        }
        String curl =curlSymbol( curl(indexParmsStatus.getUrl(),!Validator.check(scroll_id)?indexParmsStatus.getIndexName():"",!Validator.check(scroll_id)?indexParmsStatus.getIndexType():"", _SEARCH),Validator.check(scroll_id)?SLASH:QUESTION  ,
        Validator.check(scroll_id)?SCROLL:SCROLL+EQUAL+scroll_time);
        String bodys=Validator.check(scroll_id)?o(o(SCROLL,scroll_time),SCROLL_ID,scroll_id).toString():indexSearchBuilder.getSearch().toString();
//        logger.debug(LogUtil.compositionLogCurl(curl,bodys));
        if(debugInfo()){
            logger.info(LogUtil.compositionLogCurl(curl,bodys) );
        }
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
        if( Validator.check(indexSearchBuilder.getScrollTime()) ){
            curl =curlSymbol(curlSymbol(curlSymbol(curl,QUESTION,PRETTY),AND,SCROLL),EQUAL,indexSearchBuilder.getScrollTime());
        }
        String bodys = os(QUERY,indexSearchBuilder.getQuery().toString());
//        logger.debug(LogUtil.compositionLogCurl(curl,bodys) );
        if(debugInfo()){
            logger.info(LogUtil.compositionLogCurl(curl,bodys) );
        }
        String resultStr = new CasiaHttpUtil().post(curl,indexParmsStatus.getHeards(),null,bodys);
        return ExecuteResult.executeQueryTotal(o(resultStr));
    }
    /**
     * execute query
     */
    @Override
    @Deprecated
    public SearchResult executeAggsInfo() {
        if( !Validator.check( indexSearchBuilder.getAggs() ) ){
            logger.warn(LogUtil.compositionLogEmpty("aggs parms"));
            SearchResult searchResult= new SearchResult();
            searchResult.setStatus(false);
            return searchResult;
        }
        String curl=curl(indexParmsStatus.getUrl(),indexParmsStatus.getIndexName(),indexParmsStatus.getIndexType(),_SEARCH);
//        logger.debug(LogUtil.compositionLogCurl(curl,indexSearchBuilder.getCount().toString() ) );
        if( Validator.check(indexSearchBuilder.getScrollTime()) ){
            curl =curlSymbol(curlSymbol(curlSymbol(curl,QUESTION,PRETTY),AND,SCROLL),EQUAL,indexSearchBuilder.getScrollTime());
        }
        if(debugInfo()){
            logger.info(LogUtil.compositionLogCurl(curl,indexSearchBuilder.getCount().toString()) );
        }
        String resultStr = new CasiaHttpUtil().post(curl,indexParmsStatus.getHeards(),null,indexSearchBuilder.getCount().toString());
        return ExecuteResult.executeQueryResult(o(resultStr));
    }
}
