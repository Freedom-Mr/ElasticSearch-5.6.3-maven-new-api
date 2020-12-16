package casia.isiteam.api.elasticsearch.controller;

import junit.framework.TestCase;

import java.util.Map;

/**
 * ClassName: CasiaIkAnalyzerTest
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/7/8
 * Email: zhiyou_wang@foxmail.com
 */
public class CasiaIkAnalyzerTest extends TestCase {

    String text = "7月6日，山东一名22岁打工小伙在工地演奏古筝的视频火了，众人纷纷惊叹不已。据悉，小伙通过网上视频自学古筝4年，背着琴走南闯北，每到一处都不忘在工棚里练琴，学会了很多高难度曲目，他最大的愿望就是成为一名古筝老师。" +
            "最近感染了新冠，在家休养，新冠肺炎严重的影响了他的生活。";

    public void testMinAnalyzer() {
        Map<String,Integer> rs = CasiaIkAnalyzer.maxAnalyzer(text,-1,1,2);
        System.out.println("共计："+rs.size());
        rs.forEach((k,v)->{
            System.out.println(k+":"+v);
        });
    }

    public void testMaxAnalyzer() {
        Map<String,Integer> rs = CasiaIkAnalyzer.minAnalyzer(text);
        System.out.println("共计："+rs.size());
        rs.forEach((k,v)->{
            System.out.println(k+":"+v);
        });
    }

}