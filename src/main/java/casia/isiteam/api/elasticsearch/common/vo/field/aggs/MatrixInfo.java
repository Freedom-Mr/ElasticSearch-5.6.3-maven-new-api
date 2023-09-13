package casia.isiteam.api.elasticsearch.common.vo.field.aggs;

import casia.isiteam.api.toolutil.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: MatrixInfo
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/6/30
 * Email: zhiyou_wang@foxmail.com
 */
public class MatrixInfo {
    /**
     * fields name
     */
    private List<String> fields = new ArrayList<>();
    /**
     * missing value set parm
     */
    private float missing;


    public MatrixInfo() { }
    public MatrixInfo(List<String> fields) {
        if(Validator.check(fields)){
            fields.forEach(s->{
                if( !this.fields.contains(s) ){
                    this.fields.add(s);
                }
            });
        }
    }
    public MatrixInfo(String ... field) {
        if(Validator.check(field)){
            for(String s:field){
                if( !this.fields.contains(s) ){
                    this.fields.add(s);
                }
            };
        }
    }

    public MatrixInfo(List<String> fields, float missing) {
        if(Validator.check(fields)){
            fields.forEach(s->{
                if( !this.fields.contains(s) ){
                    this.fields.add(s);
                }
            });
        }
        this.missing = missing;
    }

    public List<String> getFields() {
        return this.fields;
    }

    public MatrixInfo setFields(List<String> fields) {
        if(Validator.check(fields)){
            fields.forEach(s->{
                if( !this.fields.contains(s) ){
                    this.fields.add(s);
                }
            });
        }
        return this;
    }
    public MatrixInfo setFields(String ... fields) {
        if(Validator.check(fields)){
            for(String s:fields){
                if( !this.fields.contains(s) ){
                    this.fields.add(s);
                }
            };
        }
        return this;
    }
    public float getMissing() {
        return missing;
    }

    public MatrixInfo setMissing(float missing) {
        this.missing = missing;
        return this;
    }
}
