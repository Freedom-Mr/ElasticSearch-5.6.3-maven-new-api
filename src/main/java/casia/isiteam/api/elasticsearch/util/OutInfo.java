package casia.isiteam.api.elasticsearch.util;

import casia.isiteam.api.elasticsearch.common.vo.result.AggsInfo;
import casia.isiteam.api.elasticsearch.common.vo.result.LonLatInfo;
import casia.isiteam.api.elasticsearch.common.vo.result.QueryInfo;
import casia.isiteam.api.elasticsearch.common.vo.result.SearchResult;
import casia.isiteam.api.toolutil.Validator;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ClassName: OutInfo
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/6/12
 * Email: zhiyou_wang@foxmail.com
 */
public class OutInfo {
    public static void out(SearchResult searchResult){
        System.out.println("查询状态（status）："+searchResult.status());
        System.out.println("文档总量（Total_doc）："+searchResult.getTotal_Doc());
        System.out.println("任务标识（ScrollId）："+searchResult.getScrollId());
        System.out.println("结果JSON格式："+ JSON.toJSONString(searchResult) );
        System.out.println("文档信息列表（QueryInfos）：↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ size:"+searchResult.getQueryInfos().size());
        if( Validator.check(searchResult.getQueryInfos()) ){
            List<Integer> index = new ArrayList<>();
            index.add(0);
            outInfo( searchResult.getQueryInfos(),0 ,index);
        }
        System.out.println("聚合统计列表（AggsInfos） List：↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ size:"+searchResult.getAggsInfos().size());
        if( Validator.check(searchResult.getAggsInfos()) ){
            List<Integer> index = new ArrayList<>();
            index.add(0);
            outAggs( searchResult.getAggsInfos(),0 ,index);
        }
    }


    private static void outInfo( List<QueryInfo> aggsInfos,int childrens,List<Integer> index){
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<childrens;i++ ){
            sb.append(index.contains(i)?"┃\t":"\t");
        }
        aggsInfos.forEach(s->{
            System.out.print(sb+(childrens>0?"┗━":"")+"{id："+s.getId());
            System.out.print("}，{index_name："+s.getIndexName());
            System.out.print("}，{index_type："+s.getIndexType());
            System.out.print("}，{score："+s.getScore());
            System.out.print("}，{total_Operation："+s.getTotal_Operation());
            System.out.print("}，{field："+s.getField());
            System.out.println("}");
        });
    }
    private static void outAggs(List<AggsInfo> aggsInfos, int childrens ,List<Integer> index){
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<childrens;i++ ){
            sb.append(index.contains(i)?"┃\t":"\t");
        }
        for(int j=0;j<aggsInfos.size();j++ ){
            AggsInfo s =aggsInfos.get(j);
            System.out.print(sb+(childrens>0?"┗━":"")+"{field："+s.getField());
            System.out.print("}，{type："+s.getType());
            System.out.print("}，{keyAsString："+s.getKeyAsString());
            System.out.print("}，{total_doc："+s.getTotal_Doc());
            System.out.print("}，{total_Operation："+s.getTotal_Operation());
            System.out.print("}，{total_Sum_Other_Doc："+s.getTotal_Sum_Other_Doc());
            System.out.println("}");

            if(  j +1== aggsInfos.size() ){
                index.removeAll(Arrays.asList(childrens));
            }
            if(Validator.check(s.getChildren())){
                if(  childrens>0 && j +1< aggsInfos.size() ){
                    if( !index.contains(childrens) ){
                        index.add( childrens );
                    }
                }
                outAggs(s.getChildren(),childrens+1,index);
            }

            if( Validator.check(s.getQueryInfos()) ){
                outInfo( s.getQueryInfos(), childrens+1,index);
            }if( Validator.check(s.getLonLatInfos()) ){
                outLaLInfo( childrens+1,s.getLonLatInfos(),index);
            }
        }
    }
    private static void outLaLInfo( int childrens,List<LonLatInfo> aggsInfos,List<Integer> index){
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<childrens;i++ ){
            sb.append(index.contains(i)?"┃\t":"\t");
        }
        aggsInfos.forEach(s->{
            System.out.print(sb+(childrens>0?"┗━":"")+"{field："+s.getField());
            System.out.print("}，{type："+s.getType());
            System.out.print("}，{lon："+s.getLon());
            System.out.print("}，{lat："+s.getLat());
            System.out.println("}");
        });
    }
}
