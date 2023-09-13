package casia.isiteam.api.elasticsearch.xshell;

import casia.isiteam.api.elasticsearch.controller.CasiaEsCreate;
import casia.isiteam.api.elasticsearch.controller.CasiaEsDelete;
import casia.isiteam.api.toolutil.file.CasiaFileUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @ClassName:
 * @Description: unknown
 * @Vsersion: 1.0
 * <p>
 * Created by casia.wangzhiyou on 2020/12/8
 * Email: zhiyou_wang@foxmail.com
 */
public class InsertFiledMain {
    private static Logger logger = LoggerFactory.getLogger( InsertFiledMain.class);
    /**
     * 新增字段
     * @param args
     *        args[0] 数据源
     *        args[1] 索引名
     *        args[2] 索引类型
     *        args[3] 字段信息文件路径
     */
    public static void main(String[] args) {
        if(args.length==4){
            String datasource = args[0];
            String indexname = args[1];
            String indextype = args[2];
            String filepath = args[3];
            File file = new File(filepath);
            String fieldName = file.getName().substring(0,file.getName().lastIndexOf("."));
            String fieldInfo = CasiaFileUtil.readAllBytes(filepath);
            CasiaEsCreate casiaEsCreate = new CasiaEsCreate(datasource);
            casiaEsCreate.setIndexName(indexname,indextype);
            boolean rs = casiaEsCreate.insertField2(fieldName, JSONObject.parseObject(fieldInfo));
            System.out.println("insert "+fieldInfo+" "+rs+"!");
            logger.info("insert {} {}!",fieldInfo,rs);
        }else{
            logger.error("Incorrect parameter , The parameter size must equal 4 or 5 !");
            System.out.println("Incorrect parameter , The parameter size must equal 4 or 5 !");
        }
        logger.info("Execution completed , Bye!");
        System.out.println("Execution completed , Bye!");
    }
}
