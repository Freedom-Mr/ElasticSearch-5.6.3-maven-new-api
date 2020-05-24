package casia.isiteam.api.elasticsearch.common.vo.field.search;

import casia.isiteam.api.elasticsearch.common.enums.FieldOccurs;
import casia.isiteam.api.elasticsearch.common.enums.QueriesLevel;

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
    private String keyWord;
    private FieldOccurs fieldOccurs;
    private QueriesLevel queriesLevel;
    private List<KeywordsCombine> keywordsCombines = new ArrayList<>();

    public KeyWordsBuider(String field, String keyWord, FieldOccurs fieldOccurs, QueriesLevel queriesLevel) {
        this.field = field;
        this.keyWord = keyWord;
        this.fieldOccurs = fieldOccurs;
        this.queriesLevel = queriesLevel;
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

    public KeyWordsBuider setKeyWord(String keyWord) {
        this.keyWord = keyWord;
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

    public KeyWordsBuider setQueriesLevel(QueriesLevel queriesLevel) {
        this.queriesLevel = queriesLevel;
        return this;
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
}
