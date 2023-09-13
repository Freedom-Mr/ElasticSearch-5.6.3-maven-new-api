package casia.isiteam.api.elasticsearch.common.enums;

/**
 * ClassName: QueriesLevel
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/14
 * Email: zhiyou_wang@foxmail.com
 */
public enum QueriesLevel {
    /**
     * 查询全部
     */
    All("match_all"),
    /**
     * 会进行分词(词组)，查询的时候，只要字段内容分词结果中 包含 词组中的任意一个 即可命中
     * 例如： elasticsearch 会对字段内容进行分词，"hello world"会被分成 hello 和 world ，
     * 查询"hello"时，会命中。
     */
    Keywords("match"),
    /**
     * 会进行分词(词组)，查询的时候，字段内容分词结果中 完全包含 所有词组，并且顺序一致不能打乱
     * 例如： elasticsearch 会对字段内容进行分词，"hello world"会被分成 hello 和 world ，
     * 查询"hello      world"时，会命中。
     * 查询"hello java world"时，不会命中。
     */
    Phrase("match_phrase"),
    /**
     * 不会进行分词，查询的时候，字段内容分词结果中 是否有"hello world"的短语，而不是查询字段中包含"hello world"的字样，
     * 例如： elasticsearch 会对字段内容进行分词，"hello world"会被分成 hello 和 world ，
     * 查询"hello world"时，不存在"hello world"，因此这里的查询结果会为空。
     */
    Term("term"),
    /**
     *  通配符查询，（使用不当可能会遇到很大性能问题）
     * 例如：字符串类型字段（其它类型字段意义大不） ，内容为"hello world"时，
     * 查询"hello*"时，会命中。
     */
    Wildcard("wildcard"),
    /**
     *  正则查询，（使用不当可能会遇到很大性能问题）
     * 例如：字符串类型字段（其它类型字段意义大不） ，内容为"无线新闻"时，
     * 通过正则表达式查询
     * 查询"无.*新.*"时，会命中。
     */
    Regexp("regexp"),
    /**
     *  前缀查询，（使用不当可能会遇到很大性能问题）
     * 例如：字符串类型字段（其它类型字段意义大不） ，内容为"无线新闻"时，
     * 根据内容头部起始查询，
     * 查询"无"时，即可命中。
     * 查询"线"时，不会命中。
     */
    Prefix("prefix");

    private String level;
    private QueriesLevel(String level) {
        this.level = level;
    }
    public String getLevel() {
        return level;
    }

}
