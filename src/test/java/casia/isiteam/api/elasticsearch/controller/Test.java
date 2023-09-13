package casia.isiteam.api.elasticsearch.controller;

import casia.isiteam.api.elasticsearch.common.enums.FieldOccurs;
import casia.isiteam.api.elasticsearch.common.enums.QueriesLevel;
import casia.isiteam.api.elasticsearch.common.enums.SortOrder;
import casia.isiteam.api.elasticsearch.common.vo.field.RangeField;
import casia.isiteam.api.elasticsearch.common.vo.field.SortField;
import casia.isiteam.api.elasticsearch.common.vo.field.aggs.AggsFieldBuider;
import casia.isiteam.api.elasticsearch.common.vo.field.aggs.DateInfo;
import casia.isiteam.api.elasticsearch.common.vo.field.aggs.TermInfo;
import casia.isiteam.api.elasticsearch.common.vo.field.aggs.TopData;
import casia.isiteam.api.elasticsearch.common.vo.field.search.KeyWordsBuider;
import casia.isiteam.api.elasticsearch.common.vo.field.search.KeywordsCombine;
import casia.isiteam.api.elasticsearch.common.vo.result.SearchResult;
import casia.isiteam.api.elasticsearch.controller.api.CasiaEsApi;
import casia.isiteam.api.elasticsearch.util.OutInfo;
import casia.isiteam.api.toolutil.file.CasiaFileUtil;
import com.alibaba.fastjson.JSONObject;
import junit.framework.TestCase;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @ClassName: Test
 * @Description: unknown
 * @Vsersion: 1.0
 * <p>
 * Created by casia.wangzhiyou on 2021/5/18
 * Email: zhiyou_wang@foxmail.com
 */
public class Test extends TestCase {

    public void test() {
        CasiaEsApi casiaEsApi= new CasiaEsApi("os1");
        casiaEsApi.create().setIndexName("toutiao-*","monitor_all");

        Map<String, String> map = new HashMap<>();
//        map.put("type", "integer");
        map.put("type", "keyword");
//                map.put("analyzer", "standard");
        map.put("index", "not_analyzed");
//                map.put("format", "yyyy-MM-dd HH:mm:ss");
//        map.put("store", "true");
//        boolean rs = casiaEsApi.create().insertField("user_md5",map);

//        String mapping = CasiaFileUtil.readAllBytes("mapping/all.json");
//        boolean rs = casiaEsApi.create().createIndex("test",mapping);

//        List<String> list = CasiaFileUtil.readAllLines("datas/test_data_list.d", StandardCharsets.UTF_8);
//        List<JSONObject> datas = list.stream().map(x -> JSONObject.parseObject(x)).collect( Collectors.toList());
//        casiaEsApi.create().setIndexName("test","all");
//        List<JSONObject> c= new ArrayList<>();
//        c.add(datas.get(0));
//        Map<String,Object> rss =casiaEsApi.create().writeDatas( c,"id");
//        rss.forEach((k,v)->{
//            System.out.println(k+"\t"+v);
//        });

//        casiaEsApi.delete().setIndexName("test");
//        boolean rs = casiaEsApi.delete().deleteIndexByName();
//        System.out.println(rs);



        CasiaEsSearch casiaEsSearch = new CasiaEsSearch("all");
        casiaEsSearch.reset();
        casiaEsSearch.setIndexName("os-headlines-news-2021-12*","headlines");
//        casiaEsSearch.addSort(new SortField("pubtime",SortOrder.DESC));
        casiaEsSearch.setRange(new RangeField(FieldOccurs.INCLUDES,"tendency_level",1,3).setIncludeLower(false).setIncludeUpper(true)).setReturnField("tendency_level");
        SortField sortField = new SortField("pubtime",SortOrder.DESC);
        casiaEsSearch.setAggregations(
                new AggsFieldBuider(
                        new TermInfo("site_channel_model_name",500, 1L , SortOrder.DESC ,
                                new AggsFieldBuider(
                                        new TopData(1, Arrays.asList(sortField)).setReturnField(FieldOccurs.INCLUDES,"pubtime")
                                )
                        )
                )
        ).setSize(0);
        SearchResult searchResult = casiaEsSearch.executeQueryInfo();

        OutInfo.out(searchResult);
    }
    public void test2(){
        CasiaEsSearch casiaEsSearch = new CasiaEsSearch("all");
        casiaEsSearch.setIndexName("all-p-facebook-*", "person");
//        casiaEsSearch.setIndexName("os-headlines-*", "headlines");
        casiaEsSearch.setSize(0);
        AggsFieldBuider aggsFieldBuider = new AggsFieldBuider();
       /* casiaEsSearch.setQueryKeyWords(
               new KeywordsCombine( 1,
                       new KeyWordsBuider("repeat_data","false",FieldOccurs.INCLUDES, QueriesLevel.Term)
               )
       );*/
        String keys = "疫苗 疫情 全民检测 限聚令 新冠 病毒 抗疫 隔离 防疫 染疫 动态清零 核酸 肺炎";
        String[] kk =keys.split(" ");
        List<KeywordsCombine> li = new ArrayList<>();
        List<KeyWordsBuider> li2 = new ArrayList<>();
        for(String key :kk){
            li2.add(new KeyWordsBuider("title",key,FieldOccurs.INCLUDES, QueriesLevel.Phrase));
                li2.add(new KeyWordsBuider("title_translation",key,FieldOccurs.INCLUDES, QueriesLevel.Phrase));
            li2.add(new KeyWordsBuider("content",key,FieldOccurs.INCLUDES, QueriesLevel.Phrase));
                li2.add(new KeyWordsBuider("content_translation",key,FieldOccurs.INCLUDES, QueriesLevel.Phrase));
        }
        casiaEsSearch.setQueryKeyWords(
                new KeywordsCombine( 1,li2                )
        );
        casiaEsSearch.setQueryKeyWords( new KeywordsCombine( 1,
                new KeyWordsBuider("publish_author_id","489724987885159",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                new KeyWordsBuider("publish_author_id","15666129583",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                new KeyWordsBuider("publish_author_id","200954406608272",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                new KeyWordsBuider("publish_author_id","142534885783608",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                new KeyWordsBuider("publish_author_id","1511612119105024",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                new KeyWordsBuider("publish_author_id","518758878276908",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                new KeyWordsBuider("publish_author_id","454004001340790",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                new KeyWordsBuider("publish_author_id","104318651932201",FieldOccurs.EXCLUDES, QueriesLevel.Phrase)
        ));
        SearchResult searchResult = casiaEsSearch
                .setRange(new RangeField(FieldOccurs.INCLUDES,"pubtime","2022-02-01 00:00:00","2022-06-07 23:59:59"))
                .setAggregations(
                        new AggsFieldBuider(
                                new DateInfo("pubtime","yyyy-MM-dd","1d",0L,"2022-02-01","2022-06-07")
                        )
                ).executeQueryInfo();


        searchResult.getAggsInfos().forEach(s->{
            s.getChildren().forEach(c->{
                System.out.println(c.getField()+"\t"+c.getTotal_Doc());

            });
        });
    }
    public static List<String> getsAllDatesInTheDateRange(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> localDateList = new ArrayList<>();
        // 开始时间必须小于结束时间
        if (startDate.isAfter(endDate)) {
            return null;
        }
        while (startDate.isBefore(endDate)) {
            localDateList.add(startDate);
            startDate = startDate.plusDays(1);
        }
        localDateList.add(endDate);
        List<String> rs = new ArrayList<>();
        localDateList.forEach(s->{
            rs.add(s.toString());
        });
        return rs;
    }



    //        String keys = "疫苗 疫情 全民检测 限聚令 新冠 病毒 抗疫 隔离 防疫 染疫 动态清零 核酸 肺炎";
    String keys = "七一 习近平 习主席 总书记 韩正 香港回归 7月1号 回归25周年 香港25周年 成立25周年 一国两制";
    String pkeys = "七一|习近平|习主席|总书记|韩正|香港回归|7月1号|回归25周年|香港25周年|成立25周年|一国两制";
//    String so = "美国之音;香港电台;东网;明报;BBC;信报;台湾中央社中文;商业电台;多维新闻网中文;德国之声中文;新加坡联合早报中文;正报;法国国际广播电台中文;澳门日报;澳门电台;立场新闻;自由亚洲电台中文;讯报;路透社中文;香港01;香港独立媒体;星岛日报;众新闻;台湾苹果日报;中评社;香港经济日报;now新闻;澳广视;力报;捷报;濠江日报;联合早报;大纪元时报;台湾中央广播电台;SBS中文;纽约时报中文网;美国之音;门徒媒体;端传媒;论尽媒体;香港商报网;阿波罗新闻网;倍可亲;博闻社;博讯;大纪元;动态网;独立中文笔会;对华援助协会;多维新闻网;法网恢恢;华夏文摘;欢呼中国茉莉花革命;开放;看中国;刻新闻;零八宪章;六四档案;六四天网;蒙古新闻;蒙古自由联盟党;民生观察;民主中国;明慧网;明镜时报;明镜新闻网;茉莉花;南蒙古人权论坛;南蒙古时事评论;权利运动;人权观察;世界之门;万维读者网;维权网;文革受难者;希望之声;新纪元;新生;新唐人电视台;亚太正悟网;一点评网;争鸣动向;正见网;中国禁闻网;中国茉莉花革命;中国茉莉花行动部落;中国评论新闻网;中国人权;中国人权问责中心;中国事务;中国数字时代;中华评述;主场新闻;自由亚洲电台;自由中国;blogspot;透明国际;北京之春;北京之春;博谈网;藏青会;达赖藏文;达赖喇嘛西藏宗教基金会;达赖中文;德国西藏倡议组织;德国之声;东突厥斯坦流亡政府;番薯藤;反共黑客;公民议报;郭媒体;国际西藏邮报;黄花岗杂刊;联合新闻网 ;瞭望西藏;美国西藏之家网站;蒙古之声广播;南蒙古人权信息中心;苹果日报;神韵艺术团;生命季刊;头条新闻（视频爱分享）;维吾尔人权项目;维吾尔之声;西藏故乡网;西藏政策研究所;西藏之声;希望之声;新纪元周刊;新三才;政客;中国茉莉花革命;中国人权民运信息中心;中评网;中时电子报;中央通讯社;自由西藏网";
     String so = "阿波罗新闻网;倍可亲;博闻社;博讯;大纪元;动态网;独立中文笔会;对华援助协会;多维新闻网;法网恢恢;华夏文摘;欢呼中国茉莉花革命;开放;看中国;刻新闻;零八宪章;六四档案;六四天网;蒙古新闻;蒙古自由联盟党;民生观察;民主中国;明慧网;明镜时报;明镜新闻网;茉莉花;南蒙古人权论坛;南蒙古时事评论;权利运动;人权观察;世界之门;万维读者网;维权网;文革受难者;希望之声;新纪元;新生;新唐人电视台;亚太正悟网;一点评网;争鸣动向;正见网;中国禁闻网;中国茉莉花革命;中国茉莉花行动部落;中国评论新闻网;中国人权;中国人权问责中心;中国事务;中国数字时代;中华评述;主场新闻;自由亚洲电台;自由中国;blogspot;透明国际;北京之春;北京之春;博谈网;藏青会;达赖藏文;达赖喇嘛西藏宗教基金会;达赖中文;德国西藏倡议组织;德国之声;东突厥斯坦流亡政府;番薯藤;反共黑客;公民议报;郭媒体;国际西藏邮报;黄花岗杂刊;联合新闻网 ;瞭望西藏;美国西藏之家网站;蒙古之声广播;南蒙古人权信息中心;苹果日报;神韵艺术团;生命季刊;头条新闻（视频爱分享）;维吾尔人权项目;维吾尔之声;西藏故乡网;西藏政策研究所;西藏之声;希望之声;新纪元周刊;新三才;政客;中国茉莉花革命;中国人权民运信息中心;中评网;中时电子报;中央通讯社;自由西藏网";

    //总
    public void test3() {
        CasiaEsSearch casiaEsSearch = new CasiaEsSearch("all");
        casiaEsSearch.setIndexName("all-p-facebook-*", "person");
//        casiaEsSearch.setIndexName("os-headlines-*", "headlines");
        LinkedHashMap<String,String> maps = new LinkedHashMap<>();
        //
        String[] kk =keys.split(" ");
        List<KeywordsCombine> li = new ArrayList<>();
        List<KeyWordsBuider> li2 = new ArrayList<>();
        for(String key :kk){
            li2.add(new KeyWordsBuider("title",key,FieldOccurs.INCLUDES, QueriesLevel.Phrase));
                li2.add(new KeyWordsBuider("title_translation",key,FieldOccurs.INCLUDES, QueriesLevel.Phrase));
            li2.add(new KeyWordsBuider("content",key,FieldOccurs.INCLUDES, QueriesLevel.Phrase));
                li2.add(new KeyWordsBuider("content_translation",key,FieldOccurs.INCLUDES, QueriesLevel.Phrase));
        }
        String[] ss = so.split(";");
        List<KeyWordsBuider> lis = new ArrayList<>();
        for(String key :ss){
            lis.add(new KeyWordsBuider("site_name",key,FieldOccurs.EXCLUDES, QueriesLevel.Phrase));
        }

        boolean isfirst = false;
        String city = "上海";
        String city1 = "北京";
        String stime = "2022-02-01";
        String etime = "2022-06-15";

        List<String> localDateList = getsAllDatesInTheDateRange(LocalDate.of(2022, 2, 1), LocalDate.of(2022, 6, 15));
        casiaEsSearch.reset();
//            casiaEsSearch.setIndexName("all-p-facebook-*", "person");
//        casiaEsSearch.setIndexName("m-all-news-2022-02,m-all-news-2022-03,m-all-news-2022-04,m-all-news-2022-05,m-all-news-2022-06", "monitor_all");
           casiaEsSearch.setIndexName("os-headlines-*", "headlines");
        casiaEsSearch.setSize(0);

        /*casiaEsSearch.setQueryKeyWords( new KeywordsCombine( 1,
                new KeyWordsBuider("title",city,FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                    new KeyWordsBuider("title_translation",city,FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                new KeyWordsBuider("content",city,FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                    new KeyWordsBuider("content_translation",city,FieldOccurs.EXCLUDES, QueriesLevel.Phrase)
        ));
        casiaEsSearch.setQueryKeyWords( new KeywordsCombine( 1,
                new KeyWordsBuider("title",city1,FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                    new KeyWordsBuider("title_translation",city1,FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                new KeyWordsBuider("content",city1,FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                    new KeyWordsBuider("content_translation",city1,FieldOccurs.EXCLUDES, QueriesLevel.Phrase)
        ));*/

         /* casiaEsSearch.setQueryKeyWords(
                   new KeywordsCombine( 1,
                           new KeyWordsBuider("nation_category","1",FieldOccurs.INCLUDES, QueriesLevel.Term)
                   )
           );*/

       /* casiaEsSearch.setQueryKeyWords( new KeywordsCombine( 1,
                new KeyWordsBuider("publish_author_id","489724987885159",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                new KeyWordsBuider("publish_author_id","15666129583",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                new KeyWordsBuider("publish_author_id","200954406608272",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                new KeyWordsBuider("publish_author_id","142534885783608",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                new KeyWordsBuider("publish_author_id","1511612119105024",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                new KeyWordsBuider("publish_author_id","518758878276908",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                new KeyWordsBuider("publish_author_id","454004001340790",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                new KeyWordsBuider("publish_author_id","104318651932201",FieldOccurs.EXCLUDES, QueriesLevel.Phrase)
        ));*/
        casiaEsSearch.setQueryKeyWords(
                new KeywordsCombine( 1,
                        new KeyWordsBuider("repeat_data","false",FieldOccurs.INCLUDES, QueriesLevel.Term)
                )
        );
        //排除敌媒
      casiaEsSearch.setQueryKeyWords(new KeywordsCombine( 1,lis ) );

        casiaEsSearch.setQueryKeyWords(
//                new KeywordsCombine( 2,new KeyWordsBuider(li))
                new KeywordsCombine( 1,li2                )
        );

        SearchResult searchResult = casiaEsSearch
                .setRange(new RangeField(FieldOccurs.INCLUDES,"pubtime",stime+" 00:00:00",etime+" 23:59:59"))
                .setAggregations(
                        new AggsFieldBuider(
                                new DateInfo("pubtime","yyyy-MM-dd","1d",0L,stime,stime)
                                        .setAggsFieldBuider(
                                                new AggsFieldBuider(
//                                                            new TermInfo("publish_author_name",100, SortOrder.DESC)
                                                        new TermInfo("site_name",100, SortOrder.DESC)
                                                )
                                        )
                        )
                ).executeQueryInfo();
        StringBuffer all_text = new StringBuffer();
        searchResult.getAggsInfos().forEach(s->{
//               System.out.println(key);
            s.getChildren().forEach(c->{
                localDateList.remove(c.getField());
                StringBuffer sb = new StringBuffer();
                //作者
                Map<String,Integer> numbers = new HashMap<>();
                numbers.put("c",0);
                c.getChildren().forEach(t->{
                    t.getChildren().forEach(o->{
                        numbers.put("c",numbers.get("c")+1);
                        sb.append(sb.length()>0?",":"").append("\""+o.getField().replaceAll("\r\n","")+"\""+":"+o.getTotal_Doc());
                    });

//                        System.out.println();
                });

                //词频
                    StringBuffer sc = new StringBuffer();
                    CasiaEsSearch casiaEsSearch2 = new CasiaEsSearch("all");
                    sc.setLength(0);
                    casiaEsSearch2.reset();
                casiaEsSearch2.setIndexName("os-headlines-*", "headlines");
//                    casiaEsSearch2.setIndexName("all-p-facebook-*", "person");
                    casiaEsSearch2.setSize(9000);
                   /* casiaEsSearch2.setQueryKeyWords( new KeywordsCombine( 1,
                            new KeyWordsBuider("title",city,FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                            new KeyWordsBuider("title_translation",city,FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                            new KeyWordsBuider("content",city,FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                            new KeyWordsBuider("content_translation",city,FieldOccurs.EXCLUDES, QueriesLevel.Phrase)
                    ));
                    casiaEsSearch2.setQueryKeyWords( new KeywordsCombine( 1,
                            new KeyWordsBuider("title",city1,FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                            new KeyWordsBuider("title_translation",city1,FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                            new KeyWordsBuider("content",city1,FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                            new KeyWordsBuider("content_translation",city1,FieldOccurs.EXCLUDES, QueriesLevel.Phrase)
                    ));*/
                   /* casiaEsSearch2.setQueryKeyWords( new KeywordsCombine( 1,
                            new KeyWordsBuider("publish_author_id","489724987885159",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                            new KeyWordsBuider("publish_author_id","15666129583",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                            new KeyWordsBuider("publish_author_id","200954406608272",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                            new KeyWordsBuider("publish_author_id","142534885783608",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                            new KeyWordsBuider("publish_author_id","1511612119105024",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                            new KeyWordsBuider("publish_author_id","518758878276908",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                            new KeyWordsBuider("publish_author_id","454004001340790",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                            new KeyWordsBuider("publish_author_id","104318651932201",FieldOccurs.EXCLUDES, QueriesLevel.Phrase)
                    ));*/
                     casiaEsSearch2.setQueryKeyWords(
                               new KeywordsCombine( 1,
                                       new KeyWordsBuider("repeat_data","false",FieldOccurs.INCLUDES, QueriesLevel.Term)
                               )
                     );
                     //排除敌媒
                    casiaEsSearch2.setQueryKeyWords(new KeywordsCombine( 1,lis ) );

                    casiaEsSearch2.setQueryKeyWords(
//                                new KeywordsCombine( 2,new KeyWordsBuider(li))
                            new KeywordsCombine( 1,li2 )
                    );
                    SearchResult searchResult2 = casiaEsSearch2
                            .setRange(new RangeField(FieldOccurs.INCLUDES,"pubtime",c.getField()+" 00:00:00",c.getField()+" 23:59:59"))
                            .setReturnField("title","title_translation","content","content_translation")
                            .executeQueryInfo();
                    searchResult2.getQueryInfos().forEach(p->{
                        sc.append(p.getField().get("title")).append(p.getField().get("title_translation")).append(p.getField().get("content")).append(p.getField().get("content_translation"));
                    });
                System.out.println(searchResult2.getTotal_Doc());
//                    Pattern p = Pattern.compile(key);
                  Pattern p = Pattern.compile(pkeys);
                    Matcher m =p.matcher(sc);
                int a = 0;
                    Map<String,Integer> cm = new HashMap<>();
                    while(m.find()){a++;
                        String group = m.group();
                        if(cm.containsKey(group)){
                            cm.put(group,cm.get(group)+1);
                        }else{
                            cm.put(group,1);
                        }
                    }
                StringBuffer mmmm = new StringBuffer();
                    cm.forEach((k,v)->{
                        mmmm.append(k).append(":").append(v).append(",");
                    });

                String rs = numbers.get("c")+( numbers.get("c")>=100?"+\t":"\t")+"{"+sb.toString().replaceAll("\r\n|\n| |\t","")+"}"+"\t"+a+"\t"+mmmm+"\t"+c.getTotal_Doc()+"\t";
//                    String rs = numbers.get("c")+( numbers.get("c")>=100?"+\t":"\t")+"{"+sb.toString()+"}"+"\t"+a+"\t"+c.getTotal_Doc()+"\t";
                if( maps.containsKey(c.getField()) ){
                    maps.put(c.getField(),maps.get(c.getField())+rs);
                }else{
                    maps.put(c.getField(),rs);
                }
            });
            System.out.println("---------------------------------------");

        });
        localDateList.forEach(s->{
            if( maps.containsKey(s) ){
                maps.put(s,maps.get(s)+"0\t{}\t0\t0\t");
            }else{
                maps.put(s,"0\t{}\t0\t0\t");
            }
        });
//       }
        maps.forEach((k,v)->{
            System.out.println(v);
        });

    }
    //单词
    public void test4() {
        CasiaEsSearch casiaEsSearch = new CasiaEsSearch("all");
//        casiaEsSearch.setIndexName("all-p-facebook-*", "person");
        casiaEsSearch.setIndexName("os-headlines-*", "headlines");
        LinkedHashMap<String,String> maps = new LinkedHashMap<>();
        //
        String[] ss = so.split(";");
        List<KeyWordsBuider> lis = new ArrayList<>();
        for(String keyn :ss){
            lis.add(new KeyWordsBuider("site_name",keyn,FieldOccurs.EXCLUDES, QueriesLevel.Phrase));
        }

        String[] kk =keys.split(" ");
        boolean isfirst = false;
        String city = "上海";
        String city1 = "北京";
        String stime = "2022-02-01";
        String etime = "2022-06-15";

       for(String key :kk){
            List<String> localDateList = getsAllDatesInTheDateRange(LocalDate.of(2022, 2, 1), LocalDate.of(2022, 6, 15));
            casiaEsSearch.reset();
//            casiaEsSearch.setIndexName("all-p-facebook-*", "person");
//        casiaEsSearch.setIndexName("m-all-news-2022-02,m-all-news-2022-03,m-all-news-2022-04,m-all-news-2022-05,m-all-news-2022-06", "monitor_all");
           casiaEsSearch.setIndexName("os-headlines-*", "headlines");
            casiaEsSearch.setSize(0);
           casiaEsSearch.setQueryKeyWords( new KeywordsCombine( 1,
                   new KeyWordsBuider("title",key,FieldOccurs.INCLUDES, QueriesLevel.Phrase),
                   new KeyWordsBuider("title_translation",key,FieldOccurs.INCLUDES, QueriesLevel.Phrase),
                   new KeyWordsBuider("content",key,FieldOccurs.INCLUDES, QueriesLevel.Phrase),
                   new KeyWordsBuider("content_translation",key,FieldOccurs.INCLUDES, QueriesLevel.Phrase)
           ));

        /*casiaEsSearch.setQueryKeyWords( new KeywordsCombine( 1,
                new KeyWordsBuider("title",city,FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                    new KeyWordsBuider("title_translation",city,FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                new KeyWordsBuider("content",city,FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                    new KeyWordsBuider("content_translation",city,FieldOccurs.EXCLUDES, QueriesLevel.Phrase)
        ));
        casiaEsSearch.setQueryKeyWords( new KeywordsCombine( 1,
                new KeyWordsBuider("title",city1,FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                    new KeyWordsBuider("title_translation",city1,FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                new KeyWordsBuider("content",city1,FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                    new KeyWordsBuider("content_translation",city1,FieldOccurs.EXCLUDES, QueriesLevel.Phrase)
        ));*/


         /* casiaEsSearch.setQueryKeyWords(
                   new KeywordsCombine( 1,
                           new KeyWordsBuider("nation_category","1",FieldOccurs.INCLUDES, QueriesLevel.Term)
                   )
           );*/
       /* casiaEsSearch.setQueryKeyWords( new KeywordsCombine( 1,
                new KeyWordsBuider("publish_author_id","489724987885159",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                new KeyWordsBuider("publish_author_id","15666129583",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                new KeyWordsBuider("publish_author_id","200954406608272",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                new KeyWordsBuider("publish_author_id","142534885783608",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                new KeyWordsBuider("publish_author_id","1511612119105024",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                new KeyWordsBuider("publish_author_id","518758878276908",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                new KeyWordsBuider("publish_author_id","454004001340790",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                new KeyWordsBuider("publish_author_id","104318651932201",FieldOccurs.EXCLUDES, QueriesLevel.Phrase)
        ));*/
       casiaEsSearch.setQueryKeyWords(
                   new KeywordsCombine( 1,
                           new KeyWordsBuider("repeat_data","false",FieldOccurs.INCLUDES, QueriesLevel.Term)
                   )
         );
       //敌媒
//       casiaEsSearch.setQueryKeyWords(new KeywordsCombine( 1,lis ) );

        SearchResult searchResult = casiaEsSearch
                .setRange(new RangeField(FieldOccurs.INCLUDES,"pubtime",stime+" 00:00:00",etime+" 23:59:59"))
                .setAggregations(
                        new AggsFieldBuider(
                                new DateInfo("pubtime","yyyy-MM-dd","1d",0L,stime,stime)
                                        .setAggsFieldBuider(
                                                new AggsFieldBuider(
//                                                        new TermInfo("publish_author_name",100, SortOrder.DESC)
                                                        new TermInfo("site_name",100, SortOrder.DESC)
                                                )
                                        )
                        )
                ).executeQueryInfo();
        StringBuffer all_text = new StringBuffer();
        searchResult.getAggsInfos().forEach(s->{
               System.out.println(key);
            s.getChildren().forEach(c->{
                localDateList.remove(c.getField());
                StringBuffer sb = new StringBuffer();
                //作者
                Map<String,Integer> numbers = new HashMap<>();
                numbers.put("c",0);
                c.getChildren().forEach(t->{
                    t.getChildren().forEach(o->{
                        numbers.put("c",numbers.get("c")+1);
                        sb.append(sb.length()>0?",":"").append("\""+o.getField().replaceAll("\r\n","")+"\""+":"+o.getTotal_Doc());
                    });
//                        System.out.println();
                });

                //词频
                StringBuffer sc = new StringBuffer();
                CasiaEsSearch casiaEsSearch2 = new CasiaEsSearch("all");
                sc.setLength(0);
                casiaEsSearch2.reset();
                casiaEsSearch2.setIndexName("os-headlines-*", "headlines");
//                casiaEsSearch2.setIndexName("all-p-facebook-*", "person");
                casiaEsSearch2.setSize(9000);
                    casiaEsSearch2.setQueryKeyWords( new KeywordsCombine( 1,
                            new KeyWordsBuider("title",key,FieldOccurs.INCLUDES, QueriesLevel.Phrase),
                            new KeyWordsBuider("title_translation",key,FieldOccurs.INCLUDES, QueriesLevel.Phrase),
                            new KeyWordsBuider("content",key,FieldOccurs.INCLUDES, QueriesLevel.Phrase),
                            new KeyWordsBuider("content_translation",key,FieldOccurs.INCLUDES, QueriesLevel.Phrase)
                    ));
                   /* casiaEsSearch2.setQueryKeyWords( new KeywordsCombine( 1,
                            new KeyWordsBuider("title",city,FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                            new KeyWordsBuider("title_translation",city,FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                            new KeyWordsBuider("content",city,FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                            new KeyWordsBuider("content_translation",city,FieldOccurs.EXCLUDES, QueriesLevel.Phrase)
                    ));
                    casiaEsSearch2.setQueryKeyWords( new KeywordsCombine( 1,
                            new KeyWordsBuider("title",city1,FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                            new KeyWordsBuider("title_translation",city1,FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                            new KeyWordsBuider("content",city1,FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                            new KeyWordsBuider("content_translation",city1,FieldOccurs.EXCLUDES, QueriesLevel.Phrase)
                    ));*/
               /* casiaEsSearch2.setQueryKeyWords( new KeywordsCombine( 1,
                        new KeyWordsBuider("publish_author_id","489724987885159",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                        new KeyWordsBuider("publish_author_id","15666129583",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                        new KeyWordsBuider("publish_author_id","200954406608272",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                        new KeyWordsBuider("publish_author_id","142534885783608",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                        new KeyWordsBuider("publish_author_id","1511612119105024",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                        new KeyWordsBuider("publish_author_id","518758878276908",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                        new KeyWordsBuider("publish_author_id","454004001340790",FieldOccurs.EXCLUDES, QueriesLevel.Phrase),
                        new KeyWordsBuider("publish_author_id","104318651932201",FieldOccurs.EXCLUDES, QueriesLevel.Phrase)
                ));*/
                casiaEsSearch2.setQueryKeyWords(
                        new KeywordsCombine( 1,
                                new KeyWordsBuider("repeat_data","false",FieldOccurs.INCLUDES, QueriesLevel.Term)
                        )
                );
                //敌媒
//              casiaEsSearch2.setQueryKeyWords(new KeywordsCombine( 1,lis ) );

                SearchResult searchResult2 = casiaEsSearch2
                        .setRange(new RangeField(FieldOccurs.INCLUDES,"pubtime",c.getField()+" 00:00:00",c.getField()+" 23:59:59"))
                        .setReturnField("title","title_translation","content","content_translation")
                        .executeQueryInfo();
                searchResult2.getQueryInfos().forEach(p->{
                    sc.append(p.getField().get("title")).append(p.getField().get("title_translation")).append(p.getField().get("content")).append(p.getField().get("content_translation"));
                });
                System.out.println(searchResult2.getTotal_Doc());
                Pattern p = Pattern.compile(key);
                Matcher m =p.matcher(sc);
                int a = 0;
                Map<String,Integer> cm = new HashMap<>();
                while(m.find()){a++;
                    String group = m.group();
                    if(cm.containsKey(group)){
                        cm.put(group,cm.get(group)+1);
                    }else{
                        cm.put(group,1);
                    }
                }
                StringBuffer mmmm = new StringBuffer();
                cm.forEach((k,v)->{
                    mmmm.append(k).append(":").append(v).append(",");
                });

//                String rs = numbers.get("c")+( numbers.get("c")>=100?"+\t":"\t")+"{"+sb.toString().replaceAll("\r\n|\n| |\t","")+"}"+"\t"+a+"\t"+mmmm+"\t"+c.getTotal_Doc()+"\t";
                    String rs = numbers.get("c")+( numbers.get("c")>=100?"+\t":"\t")+"{"+sb.toString()+"}"+"\t"+a+"\t"+c.getTotal_Doc()+"\t";
                if( maps.containsKey(c.getField()) ){
                    maps.put(c.getField(),maps.get(c.getField())+rs);
                }else{
                    maps.put(c.getField(),rs);
                }
            });
            System.out.println("---------------------------------------");

        });
        localDateList.forEach(s->{
            if( maps.containsKey(s) ){
                maps.put(s,maps.get(s)+"0\t{}\t0\t0\t");
            }else{
                maps.put(s,"0\t{}\t0\t0\t");
            }
        });
       }
        maps.forEach((k,v)->{
            System.out.println(v);
        });

    }
}
