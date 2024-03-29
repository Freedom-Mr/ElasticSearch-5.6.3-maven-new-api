package casia.isiteam.api.elasticsearch.common.vo.field.aggs;

import casia.isiteam.api.toolutil.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: IpRangeInfo
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/6/24
 * Email: zhiyou_wang@foxmail.com
 */
public class IpRangeInfo {

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
    public IpRangeInfo(String field, List<String> ranges) {
        this.field = field;
        if(Validator.check(ranges)){
            ranges.forEach(s->{
                if( !this.ranges.contains(s) ){
                    this.ranges.add(s);
                }
            });
        }
    }
    public IpRangeInfo(String field, List<String> ranges, AggsFieldBuider aggsFieldBuider) {
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
    public IpRangeInfo(String field, String ... range) {
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

    public IpRangeInfo setField(String field) {
        this.field = field;
        return this;
    }

    public List<String> getRanges() {
        return ranges;
    }

    public IpRangeInfo setRanges(List<String> ranges) {
        if(Validator.check(ranges)){
            ranges.forEach(s->{
                if( !this.ranges.contains(s) ){
                    this.ranges.add(s);
                }
            });
        }
        return this;
    }
    public IpRangeInfo setRanges(String ... range) {
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

    public IpRangeInfo setAggsFieldBuider(AggsFieldBuider aggsFieldBuider) {
        this.aggsFieldBuider = aggsFieldBuider;
        return this;
    }

    public boolean getKeyed() {
        return keyed;
    }
}
