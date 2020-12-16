package casia.isiteam.api.elasticsearch.xshell;

import casia.isiteam.api.elasticsearch.controller.CasiaEsCreate;
import casia.isiteam.api.toolutil.file.CasiaFileUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName:
 * @Description: unknown
 * @Vsersion: 1.0
 * <p>
 * Created by casia.wangzhiyou on 2020/12/8
 * Email: zhiyou_wang@foxmail.com
 */
public class CreateIndexMain {
    private static Logger logger = LoggerFactory.getLogger( CreateIndexMain.class);
    /**
     * 创建索引
     * @param args
     *        args[0] 数据源
     *        args[1] mapping文件夹路径
     */
    public static void main(String[] args) {
        String datasource = args[0];
        CasiaEsCreate casiaEsCreate = new CasiaEsCreate(datasource);
        if(args.length==2) {
            String filePathDir = args[1];
            List<String> filePaths = CasiaFileUtil.readAllFilePath(filePathDir);
            filePaths.forEach(filePath -> {
                File file = new File(filePath);
                String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
                String mapping = CasiaFileUtil.readAllBytes(filePath);
                boolean bs = casiaEsCreate.createIndex(fileName, mapping);
                System.out.println("create index "+fileName+" "+bs+" !");
                logger.info("create index {} {} !", fileName, bs);
            });
        }else{
            logger.error("Incorrect parameter , The parameter size must equal 4 or 5 !");
            System.out.println("Incorrect parameter , The parameter size must equal 4 or 5 !");
        }
        logger.info("Execution completed , Bye!");
        System.out.println("Execution completed , Bye!");
    }
}
