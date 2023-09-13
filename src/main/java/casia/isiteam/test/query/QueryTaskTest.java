package casia.isiteam.test.query;

import casia.isiteam.api.elasticsearch.controller.api.CasiaEsApi;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: QueryTaskTest
 * @Description: 根据任务ID查询任务信息
 * @Vsersion: 1.0
 * <p>
 * Created by casia.wangzhiyou on 2021/4/23
 * Email: zhiyou_wang@foxmail.com
 */
public class QueryTaskTest {
    public static void main(String[] args) {
        CasiaEsApi casiaEsApi = new CasiaEsApi("data");

        JSONObject jsonObject =casiaEsApi.query().queryTask("g6sjsH8eT-6VKFxrHzjepQ:1094326");
        System.out.println(jsonObject);
    }
}
