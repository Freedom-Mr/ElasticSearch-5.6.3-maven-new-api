package casia.isiteam.test.search;

import casia.isiteam.api.elasticsearch.common.vo.result.SearchResult;
import casia.isiteam.api.elasticsearch.controller.CasiaEsSearch;
import casia.isiteam.api.elasticsearch.util.OutInfo;

/**
 * @ClassName: Scroll_Test
 * @Description: unknown
 * @Vsersion: 1.0
 * <p>
 * Created by casia.wangzhiyou on 2021/4/23
 * Email: zhiyou_wang@foxmail.com
 */
public class Scroll_Test {
    /**
     * 设置游标
     * @param args
     */
    public static void main(String[] args) {

        CasiaEsSearch casiaEsSearch = new CasiaEsSearch("web");
        casiaEsSearch.setIndexName("test","test_data");
        casiaEsSearch.setSize(20);
        //激活时间
        casiaEsSearch.setScroll("2m");

        SearchResult searchResult = casiaEsSearch.executeQueryInfo();

        //结果打印
        OutInfo.out(searchResult);

        //再次请求携带游标，即可以上次条件再次查询结果
        SearchResult searchResult2 = casiaEsSearch.executeScrollInfo("2m",searchResult.getScrollId());
        OutInfo.out(searchResult2);
    }
}
