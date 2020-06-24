package casia.isiteam.api.elasticsearch.common.vo.field.aggs;

import casia.isiteam.api.toolutil.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: PriceInfo
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/6/23
 * Email: zhiyou_wang@foxmail.com
 */
public class PriceInfo {
    /**
     * field name
     */
    private String field;
    private boolean keyed = true;
    /**
     * ranges
     * Example：*-20 or 20-50 or 100-*
     */
    private List<String> ranges = new ArrayList<>();
    /**
     *
     */
    private AggsFieldBuider aggsFieldBuider;

    public PriceInfo(String field, List<String> ranges) {
        this.field = field;
        if(Validator.check(ranges)){
            ranges.forEach(s->{
                if( !this.ranges.contains(s) ){
                    this.ranges.add(s);
                }
            });
        }
    }
    public PriceInfo(String field, List<String> ranges, AggsFieldBuider aggsFieldBuider) {
        this.field = field;
        if(Validator.check(ranges)){
            ranges.forEach(s->{
                if( !this.ranges.contains(s) ){
                    this.ranges.add(s);
                }
            });
        }
        this.aggsFieldBuider=aggsFieldBuider;
    }
    public PriceInfo(String field, String ... range) {
        this.field = field;
        if(Validator.check(range)){
            for(String r:range){
                if( !this.ranges.contains(r) ){
                    this.ranges.add(r);
                }
            }
        }
    }
    public String getField() {
        return field;
    }

    public PriceInfo setField(String field) {
        this.field = field;
        return this;
    }

    public List<String> getRanges() {
        return ranges;
    }

    public PriceInfo setRanges(List<String> ranges) {
        if(Validator.check(ranges)){
            ranges.forEach(s->{
                if( !this.ranges.contains(s) ){
                    this.ranges.add(s);
                }
            });
        }
        return this;
    }
    public PriceInfo setRanges(String ... range) {
        if(Validator.check(range)){
            for(String r:range){
                if( !this.ranges.contains(r) ){
                    this.ranges.add(r);
                }
            }
        }
        return this;
    }

    public AggsFieldBuider getAggsFieldBuider() {
        return aggsFieldBuider;
    }

    public PriceInfo setAggsFieldBuider(AggsFieldBuider aggsFieldBuider) {
        this.aggsFieldBuider = aggsFieldBuider;
        return this;
    }

    public boolean getKeyed() {
        return keyed;
    }
}
