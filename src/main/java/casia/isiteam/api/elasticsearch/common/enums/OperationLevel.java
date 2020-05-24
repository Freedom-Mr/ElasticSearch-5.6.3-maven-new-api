package casia.isiteam.api.elasticsearch.common.enums;

/**
 * ClassName: OperationLevel
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/22
 * Email: zhiyou_wang@foxmail.com
 */
public enum  OperationLevel {
    Avg("avg"),
    Sum("sum"),
    Min("min"),
    Max("max");

    private String level;
    private OperationLevel(String level) {
        this.level = level;
    }
    public String getLevel() {
        return level;
    }
}
