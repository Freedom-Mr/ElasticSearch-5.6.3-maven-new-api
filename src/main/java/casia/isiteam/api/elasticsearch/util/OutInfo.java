package casia.isiteam.api.elasticsearch.util;

import casia.isiteam.api.elasticsearch.common.vo.result.AggsInfo;
import casia.isiteam.api.elasticsearch.common.vo.result.LonLatInfo;
import casia.isiteam.api.elasticsearch.common.vo.result.QueryInfo;
import casia.isiteam.api.elasticsearch.common.vo.result.SearchResult;
import casia.isiteam.api.toolutil.Validator;
import com.alibaba.fastjson.JSON;

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
        System.out.println("查询状态："+searchResult.status());
        System.out.println("Total_doc："+searchResult.getTotal_Doc());
        System.out.println("ScrollId："+searchResult.getScrollId());
        System.out.println("JsonString："+ JSON.toJSONString(searchResult) );
        System.out.println("Info List：↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ size:"+searchResult.getQueryInfos().size());
        if( Validator.check(searchResult.getQueryInfos()) ){
            outInfo( searchResult.getQueryInfos(),0 );
        }
        System.out.println("Aggs List：↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ size:"+searchResult.getAggsInfos().size());
        if( Validator.check(searchResult.getAggsInfos()) ){
            outAggs( searchResult.getAggsInfos(),0 );
        }
    }


    private static void outInfo( List<QueryInfo> aggsInfos,int childrens){
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<childrens;i++ ){
            sb.append("\t");
        }
        aggsInfos.forEach(s->{
            System.out.print(sb+(childrens>0?"┗━":"")+"{id："+s.getId());
            System.out.print("}，{index_name："+s.getIndexName());
            System.out.print("}，{index_type："+s.getIndexType());
            System.out.print("}，{score："+s.getScore());
            System.out.print("}，{Total_Type："+s.getTotal_Operation());
            System.out.print("}，{field："+s.getField());
            System.out.println("}");
        });
    }
    private static void outAggs(List<AggsInfo> aggsInfos, int childrens){
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<childrens;i++ ){
            sb.append("\t");
        }
        for(AggsInfo s:aggsInfos){
            System.out.print(sb+(childrens>0?"┗━":"")+"{field："+s.getField());
            System.out.print("}，{type："+s.getType());
            System.out.print("}，{total_doc："+s.getTotal_Doc());
            System.out.print("}，{total_Operation："+s.getTotal_Operation());
            System.out.println("}");
            if(Validator.check(s.getChildren())){
                outAggs(s.getChildren(),childrens+1);
            }
            if( Validator.check(s.getQueryInfos()) ){
                outInfo( s.getQueryInfos(), childrens+1);
            }if( Validator.check(s.getLonLatInfos()) ){
                outLaLInfo( childrens+1,s.getLonLatInfos());
            }
        }
    }
    private static void outLaLInfo( int childrens,List<LonLatInfo> aggsInfos){
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<childrens;i++ ){
            sb.append("\t");
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
