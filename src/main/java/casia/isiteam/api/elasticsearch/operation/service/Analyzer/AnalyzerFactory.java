package casia.isiteam.api.elasticsearch.operation.service.Analyzer;

import casia.isiteam.api.toolutil.Validator;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ClassName: AnalyzerFactory
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/6/10
 * Email: zhiyou_wang@foxmail.com
 */
public class AnalyzerFactory {

    private static IKAnalyzer analyzerMax = new IKAnalyzer(true);
    private static IKAnalyzer analyzerMin = new IKAnalyzer(false);

    /**
     * 对传入的字符串进行IK最大分词
     * @param isMax true最大分词，false最小分词
     * @param text 要分词的字符串
     * @param keywordMinlength 分词的最小长度，默认为不小于1
     * @param keywordMaxlength 分词的最大长度，默认为不小于1
     * @param limit 返回数量,负数时返回全部
     * @return 返回一个{@code Set<String>}对象，存放分词后的关键词
     */
    public static Map<String,Integer> analyzer(boolean isMax,String text,int limit, Integer keywordMinlength, Integer keywordMaxlength) {
        Map<String,Integer> map = new HashMap<>();
        if(!Validator.check(text)){
            return map;
        }
        int minlength=!Validator.check(keywordMinlength)?2:keywordMinlength;
        int maxlength=!Validator.check(keywordMaxlength)?Integer.MAX_VALUE:keywordMaxlength;
        StringReader stringReader = new StringReader(text);
        TokenStream ts = (isMax?analyzerMax:analyzerMin).tokenStream("dict.dic", stringReader);
        try {
            ts.reset();
            if (ts == null)
                return null;
            CharTermAttribute attribute = ts.getAttribute(CharTermAttribute.class);
            // 分词，将分词得到的词加到查询中
            while (ts.incrementToken()) {
                String word = new String(attribute.buffer(), 0, attribute.length());
                if( minlength > word.length() || word.length() > maxlength)
                    continue;
                Integer count = map.get(word);
                if(count == null || count == 0)
                    count = 1;
                else
                    count = count + 1;
                map.put(word, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(ts!=null){
                    ts.close();
                }
                if(stringReader!=null){
                    stringReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Map<String, Integer> sorted = limit>=0?map.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).limit(limit).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new)):
                map.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        return sorted;
    }

}
