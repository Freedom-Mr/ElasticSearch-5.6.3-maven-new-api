package casia.isiteam.api.elasticsearch.xshell;

import casia.isiteam.api.elasticsearch.controller.CasiaEsCreate;
import casia.isiteam.api.elasticsearch.controller.CasiaEsDelete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName:
 * @Description: unknown
 * @Vsersion: 1.0
 * <p>
 * Created by casia.wangzhiyou on 2020/12/7
 * Email: zhiyou_wang@foxmail.com
 */
public class DeleteIndexMain {
    private static Logger logger = LoggerFactory.getLogger( DeleteIndexMain.class);
    /**
     * 删除索引
     * @param args
     *        args[0] 数据源
     *        args[1] 索引名
     */
    public static void main(String[] args) {
        if(args.length==2){
            String datasource = args[0];
            String indexname = args[1];
            CasiaEsDelete casiaEsDelete = new CasiaEsDelete(datasource);
            casiaEsDelete.setIndexName(indexname);
            boolean rs = casiaEsDelete.deleteIndexByName();
            System.out.println("delete "+indexname+" "+rs+"!");
            logger.info("delete {} {}!",indexname,rs);
        }else{
            logger.error("Incorrect parameter , The parameter size must equal 4 or 5 !");
            System.out.println("Incorrect parameter , The parameter size must equal 4 or 5 !");
        }
        logger.info("Execution completed , Bye!");
        System.out.println("Execution completed , Bye!");
    }
}
