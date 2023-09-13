package casia.isiteam.api.elasticsearch.xshell;

import casia.isiteam.api.elasticsearch.controller.CasiaEsCreate;
import casia.isiteam.api.toolutil.Validator;
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
 * Created by casia.wangzhiyou on 2020/12/4
 * Email: zhiyou_wang@foxmail.com
 */
public class WriteDatasMain {
    private static Logger logger = LoggerFactory.getLogger( WriteDatasMain.class);

    /**
     * 写入数据
     * @param args
     *        args 参数长度等于 4 时
     *        args[0] |  args[1]  | args[2]       | args[3]
     *        数据源   |  数据主键  | 数据文件夹路径  | mapping文件路径
     *        ------------------------------------------------
     *        args 参数长度等于 5 时
     *        args[0] |  args[1]  | args[2]       | args[3]   | args[4]
     *        数据源   |  数据主键  | 数据文件夹路径  | 索引名称   |  索引类型
     */
    public static void main(String[] args) {
//        args=new String[]{"data","md5","E:\\dx\\data","E:\\dx\\mapping\\analysis_data_mapping.txt"};
        String datasource = args[0];
        String id = args[1];
        String filePathDir = args[2];
        if(args.length == 4){
            String mappingPath = args[3];
            String mapping = CasiaFileUtil.readAllBytes(mappingPath);
            File fileMapping = new File(mappingPath);
            String fileNameMapping = fileMapping.getName().substring(0,fileMapping.getName().lastIndexOf("."));

            List<String> filePaths = CasiaFileUtil.readAllFilePath(filePathDir);
            filePaths.forEach(filePath->{
                File file = new File(filePath);
                String fileName = file.getName().substring(0,file.getName().lastIndexOf("."));
                List<String> list = CasiaFileUtil.readAllLines(filePath, StandardCharsets.UTF_8);
                List<JSONObject> datas = list.stream().filter(x->Validator.check(x) && Validator.check(x.trim())).map(x -> JSONObject.parseObject(x)).collect( Collectors.toList());

                CasiaEsCreate casiaEsCreate = new CasiaEsCreate(datasource);
                casiaEsCreate.createIndex(fileName,mapping);

                casiaEsCreate.setIndexName(fileName,fileNameMapping);
                Map<String,Object> rss =casiaEsCreate.writeDatas(datas,id);
                rss.forEach((k,v)->{
                    logger.info(k+"\t"+v);
                    System.out.println(k+"\t"+v);
                });
                logger.info("send data to {}/{},size:{}",fileName,fileNameMapping,datas.size());
            });
        }else if(args.length == 5){
            String indexname = args[3];
            String indextype = args[4];
            CasiaEsCreate casiaEsCreate = new CasiaEsCreate(datasource);
            casiaEsCreate.setIndexName(indexname,indextype);
            List<String> filePaths = CasiaFileUtil.readAllFilePath(filePathDir);
            filePaths.forEach(filePath->{
                List<String> list = CasiaFileUtil.readAllLines(filePath, StandardCharsets.UTF_8);
                List<JSONObject> datas = list.stream().filter(x->Validator.check(x) && Validator.check(x.trim())).map(x -> JSONObject.parseObject(x)).collect( Collectors.toList());
                Map<String,Object> rss =casiaEsCreate.writeDatas(datas,id);
                rss.forEach((k,v)->{
                    logger.info(k+"\t"+v);
                    System.out.println(k+"\t"+v);
                });
                logger.info("send data to {}/{},size:{}",indexname,indextype,datas.size());
            });
        }else{
            logger.error("Incorrect parameter , The parameter size must equal 4 or 5 !");
            System.out.println("Incorrect parameter , The parameter size must equal 4 or 5 !");
        }
        logger.info("Execution completed , Bye!");
        System.out.println("Execution completed , Bye!");
    }
}
