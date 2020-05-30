package casia.isiteam.api.elasticsearch.dbSource;

import casia.isiteam.api.elasticsearch.common.staitcParms.ShareParms;
import casia.isiteam.api.elasticsearch.common.status.IndexAuthorStatus;
import casia.isiteam.api.elasticsearch.common.vo._Entity_Db;
import casia.isiteam.api.elasticsearch.util.LogUtil;
import casia.isiteam.api.toolutil.regex.CasiaRegexUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * ClassName: EsDbUtil
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/4/28
 * Email: zhiyou_wang@foxmail.com
 */
public class EsDbUtil extends IndexAuthorStatus {
    private static Logger logger = LoggerFactory.getLogger( EsDbUtil.class);
    private static synchronized void configure(){
        Map<String,Map<String,Object>> extractInfos = new HashMap<>();
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(APPLICATION);
            Enumeration enumeration = resourceBundle.getKeys();
            while (enumeration.hasMoreElements()) {
                Object key = enumeration.nextElement();
                Object value =  resourceBundle.getObject(String.valueOf(key));
                extraction(key,value,extractInfos);
            }
        }catch (Exception e){
            try {
                Properties props = new Properties() ;
                props.load(new FileInputStream(new File(CONFIG_APPLICATION)));
                Enumeration enumeration = props.keys();
                while (enumeration.hasMoreElements()) {
                    Object key = enumeration.nextElement();
                    Object value =  props.getProperty(String.valueOf(key));
                    extraction(key,value,extractInfos);
                }
                props.clone();
            }catch (Exception E){
                logger.warn(LogUtil.compositionLogEmpty(APPLICATION));
            }
        }finally {
            extractInfos.forEach((k,v)->initEsDb(k,v));
        }
    }
    private static void initEsDb(String dbName,Map<String,Object> parms){
        synchronized (IndexAuthorStatus.elasticDb){
            if( !IndexAuthorStatus.elasticDb.containsKey(String.valueOf(dbName)) ){
               IndexAuthorStatus.elasticDb.put(dbName,new _Entity_Db(dbName,
                        parms.containsKey(ShareParms.IP_PORT)? String.valueOf( parms.get(ShareParms.IP_PORT) ) : null,
                        parms.containsKey(ShareParms.USERNAME)? String.valueOf( parms.get(ShareParms.USERNAME) ) : null,
                        parms.containsKey(ShareParms.PASSWORD)? String.valueOf( parms.get(ShareParms.PASSWORD) ) : null
                ));
            }
        }
    }
    private static void extraction(Object key,Object value,Map<String,Map<String,Object>> extractInfos){
        try {
            if( CasiaRegexUtil.isMatch(key.toString(),DB_KEY) ){
                String[] splits = String.valueOf(key).split(DOT);
                if( extractInfos.containsKey(splits[3]) ){
                    extractInfos.get(splits[3]).put(splits[4],value);
                }else{
                    Map<String,Object> hv = new HashMap<>();
                    hv.put(splits[4],value);
                    extractInfos.put(splits[3],hv);
                }
            };
        }catch (Exception E){
            logger.error(E.getMessage());
        }
    }
    static{configure();}
    protected static synchronized _Entity_Db getDb(String driverName){
        return IndexAuthorStatus.elasticDb.containsKey(driverName)? IndexAuthorStatus.elasticDb.get(driverName) : new _Entity_Db();
    }
}
