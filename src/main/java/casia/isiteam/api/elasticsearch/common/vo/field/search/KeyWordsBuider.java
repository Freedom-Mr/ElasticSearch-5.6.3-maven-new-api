package casia.isiteam.api.elasticsearch.common.vo.field.search;

import casia.isiteam.api.elasticsearch.common.enums.FieldOccurs;
import casia.isiteam.api.elasticsearch.common.enums.GeoQueryLevel;
import casia.isiteam.api.elasticsearch.common.enums.QueriesLevel;
import casia.isiteam.api.elasticsearch.common.vo.field.search.geo.GeoQueryInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: KeyWordsBuider
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/14
 * Email: zhiyou_wang@foxmail.com
 */
public class KeyWordsBuider {
    private String field;
    private FieldOccurs fieldOccurs;

    //keyword buider
    private String keyWord;
    private QueriesLevel queriesLevel;

    //geo buider
    private GeoQueryInfo geoQueryInfo;
    private GeoQueryLevel geoQueryLevel;

    //range buider
    private String gte;
    private String lte;

    private List<KeywordsCombine> keywordsCombines = new ArrayList<>();
    /**
     * field alias
     */
    private String alias;

    public KeyWordsBuider(String field, String keyWord, FieldOccurs fieldOccurs, QueriesLevel queriesLevel) {
        this.field = field;
        this.keyWord = keyWord;
        this.fieldOccurs = fieldOccurs;
        this.queriesLevel = queriesLevel;
    }

    public KeyWordsBuider(String field, GeoQueryInfo geoQueryInfo, FieldOccurs fieldOccurs, GeoQueryLevel geoQueryLevel) {
        this.field = field;
        this.geoQueryInfo = geoQueryInfo;
        this.fieldOccurs = fieldOccurs;
        this.geoQueryLevel = geoQueryLevel;
    }

    public KeyWordsBuider(String field, String gte, String lte,FieldOccurs fieldOccurs) {
        this.field = field;
        this.gte = gte;
        this.lte = lte;
        this.fieldOccurs = fieldOccurs;
    }

    public KeyWordsBuider(List<KeywordsCombine> keywordsCombines) {
        for(KeywordsCombine keywordsCombine: keywordsCombines ){
            if( !this.keywordsCombines.contains(keywordsCombine) ){
                this.keywordsCombines.add(keywordsCombine);
            }
        }
    }
    public KeyWordsBuider(KeywordsCombine ... keywordsCombines) {
        for(KeywordsCombine keywordsCombine: keywordsCombines ){
            if( !this.keywordsCombines.contains(keywordsCombine) ){
                this.keywordsCombines.add(keywordsCombine);
            }
        }
    }

    public String getField() {
        return field;
    }

    public KeyWordsBuider setField(String field) {
        this.field = field;
        return this;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public KeyWordsBuider setKeyWordAndQueriesLevel(String keyWord,QueriesLevel queriesLevel) {
        this.keyWord = keyWord;
        this.queriesLevel = queriesLevel;
        return this;
    }

    public FieldOccurs getFieldOccurs() {
        return fieldOccurs;
    }

    public KeyWordsBuider setFieldOccurs(FieldOccurs fieldOccurs) {
        this.fieldOccurs = fieldOccurs;
        return this;
    }

    public QueriesLevel getQueriesLevel() {
        return queriesLevel;
    }


    public List<KeywordsCombine> getKeywordsCombines() {
        return keywordsCombines;
    }

    public KeyWordsBuider setKeywordsCombines(KeywordsCombine ... keywordsCombines) {
        for(KeywordsCombine keywordsCombine: keywordsCombines ){
            if( !this.keywordsCombines.contains(keywordsCombine) ){
                this.keywordsCombines.add(keywordsCombine);
            }
        }
        return this;
    }
    public KeyWordsBuider setKeywordsCombines(List<KeywordsCombine> keywordsCombines) {
        for(KeywordsCombine keywordsCombine: keywordsCombines ){
            if( !this.keywordsCombines.contains(keywordsCombine) ){
                this.keywordsCombines.add(keywordsCombine);
            }
        }
        return this;
    }
    public void setGeoQueryInfoAndGeoQueryLevel(GeoQueryInfo geoQueryInfo, GeoQueryLevel geoQueryLevel) {
        this.geoQueryInfo = geoQueryInfo;
        this.geoQueryLevel = geoQueryLevel;
    }

    public GeoQueryLevel getGeoQueryLevel() {
        return geoQueryLevel;
    }

    public GeoQueryInfo getGeoQueryInfo() {
        return geoQueryInfo;
    }

    public String getGte() {
        return gte;
    }

    public String getLte() {
        return lte;
    }
}
