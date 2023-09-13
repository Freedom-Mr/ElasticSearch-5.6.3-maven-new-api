package casia.isiteam.api.elasticsearch.common.vo.field.aggs;

import casia.isiteam.api.elasticsearch.common.enums.FieldOccurs;
import casia.isiteam.api.elasticsearch.common.enums.SortOrder;
import casia.isiteam.api.elasticsearch.common.vo.field.SortField;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: TopData
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/23
 * Email: zhiyou_wang@foxmail.com
 */
public class TopData {

    /**
     * return top info，default values is 3
     */
    private Integer size;
    /**
     * sort by field doc_total
     */
    private List<SortField> sortFields = new ArrayList<>();
    /**
     * return field
     */
    private List<String> includesFields = new ArrayList<>();
    /**
     * not return field
     */
    private List<String> excludesFields = new ArrayList<>();
    /**
     * field alias
     */
    private String alias;

    public TopData() {
    }

    public TopData(String alias) {
        this.alias = alias;
    }

    public TopData(Integer size) {
        this.size = size;
    }

    public TopData(Integer size, String alias) {
        this.size = size;
        this.alias = alias;
    }

    public TopData(Integer size, List<SortField> sortFields) {
        this.size = size;
        this.sortFields = sortFields;
    }

    public TopData(Integer size, List<SortField> sortFields, String alias) {
        this.size = size;
        this.sortFields = sortFields;
        this.alias = alias;
    }

    public TopData(Integer size, List<SortField> sortFields, FieldOccurs fieldOccurs, String ... fields ) {
        this.size = size;
        this.sortFields = sortFields;
        setReturnField(fieldOccurs,fields);
    }

    public TopData setReturnField(FieldOccurs fieldOccurs, String ... fields) {
        if( fieldOccurs.getSymbolValue() .equals( FieldOccurs.INCLUDES.getSymbolValue() ) ){
            for(String field:fields){
                if(!includesFields.contains(field)){
                    includesFields.add(field);
                }
            }
        }else if( fieldOccurs.getSymbolValue() .equals( FieldOccurs.EXCLUDES.getSymbolValue() ) ){
            for(String field:fields){
                if(!excludesFields.contains(field)){
                    excludesFields.add(field);
                }
            }
        }
        return this;
    }

    public String getAlias() {
        return alias;
    }

    public TopData setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public List<String> getIncludesFields() {
        return includesFields;
    }

    public List<String> getExcludesFields() {
        return excludesFields;
    }

    public Integer getSize() {
        return size;
    }

    public TopData setSize(Integer size) {
        this.size = size;
        return this;
    }

    public List<SortField> getSortFields() {
        return sortFields;
    }

    public TopData setSortFields(List<SortField> sortFields) {
        this.sortFields = sortFields;
        return this;
    }
}
