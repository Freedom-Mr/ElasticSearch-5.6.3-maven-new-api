package casia.isiteam.api.elasticsearch.controller;

import casia.isiteam.api.elasticsearch.operation.service.Analyzer.AnalyzerFactory;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * ClassName: CasiaIkAnalyzer
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/6/10
 * Email: zhiyou_wang@foxmail.com
 */
public class CasiaIkAnalyzer {

    /**
     * 对传入的字符串进行IK最大分词
     * @param text 要分词的字符串
     * @param limit 返回数量,负数时返回全部
     * @param keywordMinlength 分词的最小长度，默认为不小于1
     * @param keywordMaxlength 分词的最大长度，默认为int最大值
     * @return 返回一个{@code Map<String,Integer>}对象，存放分词后的关键词及其个数
     */
    public static Map<String,Integer> minAnalyzer(String text, Integer limit,Integer keywordMinlength,Integer keywordMaxlength) {
        return AnalyzerFactory.analyzer(false,text,limit,keywordMinlength,keywordMaxlength);
    }
    public static Map<String,Integer> minAnalyzer(String text, Integer limit) {
        return AnalyzerFactory.analyzer(false,text,limit,null,null);
    }
    public static Map<String,Integer> minAnalyzer(String text) {
        return AnalyzerFactory.analyzer(false,text,-1,null,null);
    }
    /**
     * 对传入的字符串进行IK最大分词
     * @param text 要分词的字符串
     * @param limit 返回数量,负数时返回全部
     * @param keywordMinlength 分词的最小长度，默认为不小于1
     * @param keywordMaxlength 分词的最大长度，默认为int最大值
     * @return 返回一个{@code Map<String,Integer>}对象，存放分词后的关键词及其个数
     */
    public static Map<String,Integer> maxAnalyzer(String text,Integer limit,Integer keywordMinlength,Integer keywordMaxlength) {
        return AnalyzerFactory.analyzer(true,text,limit,keywordMinlength,keywordMaxlength);
    }
    public static Map<String,Integer> maxAnalyzer(String text, Integer limit) {
        return AnalyzerFactory.analyzer(true,text,limit,null,null);
    }
    public static Map<String,Integer> maxAnalyzer(String text) {
        return AnalyzerFactory.analyzer(true,text,-1,null,null);
    }
}
