package casia.isiteam.api.elasticsearch.common.vo.field.aggs;

/**
 * ClassName: DateInfo
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/19
 * Email: zhiyou_wang@foxmail.com
 */
public class DateInfo {
    /**
     * field name
     */
    private String field;
    /**
     * date format, example: yyyy-MM-dd HH
     */
    private String format;
    /**
     * date interval, example: 1H
     */
    private String interval;
    /**
     * min doc count, default values is 0
     */
    private Long minDocTotal;
    /**
     *
     */
    private AggsFieldBuider aggsFieldBuider;


    public DateInfo(String field, String format, String interval) {
        this.field = field;
        this.format = format;
        this.interval = interval;
    }

    public DateInfo(String field, String format, String interval, Long minDocTotal) {
        this.field = field;
        this.format = format;
        this.interval = interval;
        this.minDocTotal = minDocTotal;
    }

    public DateInfo(String field, String format, String interval, Long minDocTotal, AggsFieldBuider aggsFieldBuider) {
        this.field = field;
        this.format = format;
        this.interval = interval;
        this.minDocTotal = minDocTotal;
        this.aggsFieldBuider = aggsFieldBuider;
    }

    public String getField() {
        return field;
    }

    public DateInfo setField(String field) {
        this.field = field;
        return this;
    }

    public String getFormat() {
        return format;
    }

    public DateInfo setFormat(String format) {
        this.format = format;
        return this;
    }

    public String getInterval() {
        return interval;
    }

    public DateInfo setInterval(String interval) {
        this.interval = interval;
        return this;
    }

    public Long getMinDocTotal() {
        return minDocTotal;
    }

    public DateInfo setMinDocTotal(Long minDocTotal) {
        this.minDocTotal = minDocTotal;
        return this;
    }

    public AggsFieldBuider getAggsFieldBuider() {
        return aggsFieldBuider;
    }

    public DateInfo setAggsFieldBuider(AggsFieldBuider aggsFieldBuider) {
        this.aggsFieldBuider = aggsFieldBuider;
        return this;
    }
}
