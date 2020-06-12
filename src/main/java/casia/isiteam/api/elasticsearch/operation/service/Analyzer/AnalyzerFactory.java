package casia.isiteam.api.elasticsearch.operation.service.Analyzer;

import org.wltea.analyzer.lucene.IKAnalyzer;

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

    private AnalyzerFactory() {
    }

    public static IKAnalyzer getInstanceMax() {
        return analyzerMax;
    }

    public static IKAnalyzer getInstanceMin() {
        return analyzerMin;
    }
}
