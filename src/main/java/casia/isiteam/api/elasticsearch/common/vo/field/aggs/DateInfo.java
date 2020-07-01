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
     * retun min  date
     */
    private String minDate;
    /**
     * retun max  date
     */
    private String maxDate;
    /**
     *
     */
    private AggsFieldBuider aggsFieldBuider;
    /**
     * field alias
     */
    private String alias;

    public DateInfo() {}
    public DateInfo(String field, String format, String interval) {
        this.field = field;
        this.format = format;
        this.interval = interval;
    }

    public DateInfo(String field, String format, String interval, String alias) {
        this.field = field;
        this.format = format;
        this.interval = interval;
        this.alias = alias;
    }

    public DateInfo(String field, String format, String interval, String minDate, String maxDate) {
        this.field = field;
        this.format = format;
        this.interval = interval;
        this.minDate = minDate;
        this.maxDate = maxDate;
    }

    public DateInfo(String field, String format, String interval, String minDate, String maxDate, String alias) {
        this.field = field;
        this.format = format;
        this.interval = interval;
        this.minDate = minDate;
        this.maxDate = maxDate;
        this.alias = alias;
    }

    public DateInfo(String field, String format, String interval, Long minDocTotal) {
        this.field = field;
        this.format = format;
        this.interval = interval;
        this.minDocTotal = minDocTotal;
    }

    public DateInfo(String field, String format, String interval, Long minDocTotal, String alias) {
        this.field = field;
        this.format = format;
        this.interval = interval;
        this.minDocTotal = minDocTotal;
        this.alias = alias;
    }

    public DateInfo(String field, String format, String interval, Long minDocTotal, String minDate, String maxDate) {
        this.field = field;
        this.format = format;
        this.interval = interval;
        this.minDocTotal = minDocTotal;
        this.minDate = minDate;
        this.maxDate = maxDate;
    }

    public DateInfo(String field, String format, String interval, Long minDocTotal, String minDate, String maxDate, String alias) {
        this.field = field;
        this.format = format;
        this.interval = interval;
        this.minDocTotal = minDocTotal;
        this.minDate = minDate;
        this.maxDate = maxDate;
        this.alias = alias;
    }

    public DateInfo(String field, String format, String interval, Long minDocTotal, AggsFieldBuider aggsFieldBuider) {
        this.field = field;
        this.format = format;
        this.interval = interval;
        this.minDocTotal = minDocTotal;
        this.aggsFieldBuider = aggsFieldBuider;
    }

    public DateInfo(String field, String format, String interval, Long minDocTotal, AggsFieldBuider aggsFieldBuider, String alias) {
        this.field = field;
        this.format = format;
        this.interval = interval;
        this.minDocTotal = minDocTotal;
        this.aggsFieldBuider = aggsFieldBuider;
        this.alias = alias;
    }

    public DateInfo(String field, String format, String interval, Long minDocTotal, String minDate, String maxDate, AggsFieldBuider aggsFieldBuider) {
        this.field = field;
        this.format = format;
        this.interval = interval;
        this.minDocTotal = minDocTotal;
        this.minDate = minDate;
        this.maxDate = maxDate;
        this.aggsFieldBuider = aggsFieldBuider;
    }

    public DateInfo(String field, String format, String interval, Long minDocTotal, String minDate, String maxDate, AggsFieldBuider aggsFieldBuider, String alias) {
        this.field = field;
        this.format = format;
        this.interval = interval;
        this.minDocTotal = minDocTotal;
        this.minDate = minDate;
        this.maxDate = maxDate;
        this.aggsFieldBuider = aggsFieldBuider;
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public DateInfo setAlias(String alias) {
        this.alias = alias;
        return this;
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

    public String getMinDate() {
        return minDate;
    }

    public DateInfo setMinDate(String minDate) {
        this.minDate = minDate;
        return this;
    }

    public String getMaxDate() {
        return maxDate;
    }

    public DateInfo setMaxDate(String maxDate) {
        this.maxDate = maxDate;
        return this;
    }
}
