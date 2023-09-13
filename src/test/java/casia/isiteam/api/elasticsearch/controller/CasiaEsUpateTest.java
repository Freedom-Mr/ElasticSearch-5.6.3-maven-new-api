package casia.isiteam.api.elasticsearch.controller;

import casia.isiteam.api.elasticsearch.common.enums.FieldOccurs;
import casia.isiteam.api.elasticsearch.common.enums.QueriesLevel;
import casia.isiteam.api.elasticsearch.common.vo.field.search.KeyWordsBuider;
import casia.isiteam.api.elasticsearch.common.vo.field.search.KeywordsCombine;
import casia.isiteam.api.toolutil.file.CasiaFileUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * ClassName: CasiaEsUpateTest
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/11
 * Email: zhiyou_wang@foxmail.com
 */
public class CasiaEsUpateTest extends TestCase {


    public void testUpdateParameterById() {
        CasiaEsUpate casiaEsQuery = new CasiaEsUpate("headlines");

        Map<String, Object> map = new HashMap<String, Object>();


//        map.put("keywords", new String[]{"正月"});
//        map.put("tel_no", new String[]{"13022150240"});
//        map.put("email", new String[]{"zhangping@foxmail.com"});
//        map.put("numbers", new String[]{"165542854"});

//        casiaEsQuery.setIndexName("demo_test","test_data");
        casiaEsQuery.setIndexName("os-headlines-news-2022-12","headlines");
//        casiaEsQuery.setIndexName("event_data_extract_result_v-202001","analysis_data");
        JSONObject json = new JSONObject();
        Set<String> a = new HashSet<>();
        a.add("http://124.205.145.232:8081/yorkshire/v1/download/doc/2022/01/15/b0c6c7a5fcdd25e757595fa684b92751.pdf");
//        a.add("http://124.205.145.232:8081/yorkshire/v1/download/doc/2021/09/17/d83842d2d63a84f6f85c8a486e05996c.pdf");
//        map.put("area_info",a);
//        map.put("play_number",1);
//        map.put("title",1);
        json.put("docs_info",a);

        JSONObject object2 = new JSONObject();
        String conm = CasiaFileUtil.readAllBytes("datas/a.d");
        object2.put("image_extraction_info",JSONObject.parseObject(conm).getJSONObject("image_extraction_info"));
//        object2.put("image_info",new String[]{"news-2022-12-03/21173516499d4b7f575f48d50ae4d713"});
//        object2.put("content","\"<img src=\"https://cn.uyghurcongress.org/wp-content/uploads/2022/12/aea19b9e-c629-427c-a6dc-e10a2bc5d35a.jpeg\" source=\"news-2022-12-03/21173516499d4b7f575f48d50ae4d713\" />中国民众反对疫情封控的”白纸运动”蔓延海外。11月30日，美国首都华盛顿地区的多族裔民众在中国驻美使馆外和乔治城大学（Georgetown University）等地分别举行声援活动，示威者要求”习近平下台”。 顶着摄氏8度的寒风，东突厥斯坦旗帜在中国驻美使馆前飘扬，也在夜色中映照着大使馆前的红色五星旗。在美国首都华盛顿，十多位维吾尔族人和华人周三晚间共同声援中国的“白纸运动”。他们摆起蜡烛，悼念“11·24乌鲁木齐火灾”的罹难者。 <img src=\"https://cn.uyghurcongress.org/wp-content/uploads/2022/12/e98cc8db-90c2-43e4-96e6-0fe4a55c3594-1024x771.jpeg\" source=\"news-2022-12-02/e7952310154e1b3ec6b963497f51f653\" /> 十多位维吾尔和华裔民运人士在中国驻美大使馆外举行抗议示威，声援中国爆发的“白纸运动”。（记者陈品洁摄影） “白纸运动”是年轻人的觉醒 活动发起人、世界维吾尔代表大会副主席伊利夏提对着中国驻美使馆朗读乌鲁木齐火灾罹难者的姓名、年纪以示哀悼，并带领示威者高喊“习近平下台”。 他在接受本台采访时说：“大家一块表达，一个是对死难者的纪念，另一个是对中国各个城市的年轻人站出来为维吾尔人发声、为自己争取权益。我觉得，我们有义务也为他们发声。这是一次觉醒，说明中国各城市的年轻人、知识分子已经开始克服恐惧。当人民克服恐惧的时候，是独裁者恐惧的开始。” 包括伊斯兰教学者马聚、旅美时政评论人士郭宝胜，民运人士杨子立，以及中国维权人士郭飞雄的儿子等人，出席了当天的活动。有示威民众高举不满中共“清零政策”的标语，部分维吾尔人则摆出乌鲁木齐火灾罹难者或被失踪的家人相片。 郭宝胜说，汉人与维族人的命运相似，彼此都是中共受害者，因此应该团结起来，抵抗中共暴行：“他们认识到清零政策、封控，所有的原因就是中共的体制、习近平独裁。新疆的大火让汉人明白，若再不奋起，下一个被烧死的就是他自己。” 郭飞雄的儿子面对镜头，则是䩄腆婉拒受访。他仅向记者表达了对于中国年轻人走上街头的敬佩：“现在很多年轻人出来反抗，我觉得这很好。因为中国人一般反抗很少，他们听规则（的人）挺多的，现在终于开始去街上反抗。现在怕政府的人更少，这对人的自由会好（改善）很多。” 旅居美国的民运人士杨子立则指出，严厉的清零封控不仅使中国人民丧失最基本的权利，也使无辜生命牺牲。对于在中国遍地开花的“白纸运动”，杨子立担心，若抗议扩大到旨在推翻共产党的革命行为，当局恐会暴力镇压。 杨子立：“我其实不希望看到流血，但人们应该有权利进行和平抗议。对于中共大规模的封锁，只要能打破这种封锁，我认为就是局部的胜利。” 中国留学生高举”习近平下台”标语 同样在美国首都华盛顿的乔治城大学，当晚也有学生自发举办悼念乌鲁木齐火灾、声援“白纸运动”的活动。来自多个族裔的数十位学生当晚集结在校园广场，他们点起蜡烛、手举白纸，为乌鲁木齐火灾罹难者默哀1分钟。 现场有学生高举“习近平下台”、“悼念乌鲁木齐火灾遇难同胞”、“独裁国贼习近平下台”等标语。 一名背着“习近平下台”牌子的中国留学生向在场示威者表示：“我们在这里是要让中国政府知道，我们不会沉默，我们不是没有声音。我们要求中国政府停止清零政策，这个政策已经牺牲许多生命，经济也大受打击，但这都只因为习近平脆弱的自尊心。习近平必须下台。” 除此之外，周三晚间美国多个城市继续出现声援中国“白纸运动”的示威活动。美国亚特兰大埃默里大学（Emory University）的学生在校园点起蜡烛，墙上贴满标语，写著“不要核酸要吃饭”、“共产党下台、习近平下台”等口号。 另据推特上流传的图片，加拿大阿尔伯特省埃德蒙顿的民众当天也冒着零下18度的低温，在雪地里点起蜡烛、摆上花束，悼念乌鲁木齐火灾遇难者以及声援中国民众。 &nbsp; 资源： https://www.rfa.org/mandarin/yataibaodao/zhengzhi/cm-12012022152008.html");
        boolean boo = casiaEsQuery.updateParameterById("d76e0e90f3f98d56a8de1b752fd97265",object2);
//        boolean boo = casiaEsQuery.updateParameterById("55f4df883637844f637310f429b5d17a",map);
        System.out.println(boo);
    }

    public void testUpdateParameterByQuery() {
        CasiaEsUpate casiaEsQuery = new CasiaEsUpate("data");
        casiaEsQuery.setIndexName("demo_test","test_data");
        casiaEsQuery.setQueryKeyWords(
            new KeywordsCombine( 1,
                new KeyWordsBuider("id","32669280", FieldOccurs.INCLUDES, QueriesLevel.Term)
            )
        );
        casiaEsQuery.setExistsFilter("title");

        Map<String,Object> rs = casiaEsQuery.updateParameterByQuery("eid",14);
        rs.forEach((k,v)->{
            System.out.println(k+"\t"+v);
        });
    }

    public void testMoveShardRoute() {
        CasiaEsUpate casiaEsUpate = new CasiaEsUpate("data");
        casiaEsUpate.setIndexName("demo_test","test_data");
        boolean rs = casiaEsUpate.moveShardRoute(0,"node-1","node-2");
        System.out.println(rs);
    }

    public void testUpdateNumberOfReplicas() {
        CasiaEsUpate casiaEsUpate = new CasiaEsUpate("jl");
        casiaEsUpate.setIndexName("m-all-forum-2021-09","monitor_all");
        boolean rs = casiaEsUpate.updateNumberOfReplicas(1);
        System.out.println(rs);
    }

    public void testAllocateShardRoute() {
        CasiaEsUpate casiaEsUpate = new CasiaEsUpate("jl");
        casiaEsUpate.setIndexName("m-all-forum-2021-09","monitor_all");

        boolean rs = casiaEsUpate.allocateShardRoute(9,"238-232");
        System.out.println(rs);
    }

}