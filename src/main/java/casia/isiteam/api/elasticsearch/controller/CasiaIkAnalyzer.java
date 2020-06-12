package casia.isiteam.api.elasticsearch.controller;

import casia.isiteam.api.elasticsearch.operation.service.Analyzer.AnalyzerFactory;
import jdk.nashorn.internal.parser.TokenStream;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: CasiaIkAnalyzer
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/6/10
 * Email: zhiyou_wang@foxmail.com
 */
public class CasiaIkAnalyzer {

    private static IKAnalyzer analyzer = AnalyzerFactory.getInstanceMax();
    /**
     * 工具类：对传入的字符串进行分词
     *
     * @param //keywords 要分词的字符串
     * @param size       返回数量,负数时返回全部
     * @return 返回一个{@code Set<String>}对象，存放分词后的关键词
     */
    public static List<String> extractKeywords(String text, int size) {

        Map<String, Integer> map = new HashMap<>();
        StringReader stringReader = new StringReader(text);

        return null;
    }
}
