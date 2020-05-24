package casia.isiteam.api.elasticsearch.util;

/**
 * ClassName: LogUtil
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/4/27
 * Email: zhiyou_wang@foxmail.com
 */
public class LogUtil {
    public static String compositionLogCurl(String url,String bodyParms){
        return "curl:"+url+" -d P"+bodyParms+"}";
    }
    public static String compositionLogCurl(String url,Object ... bodyParms){
        StringBuffer sb = new StringBuffer();
        sb.append("curl:").append(url).append(" -d P");
        for(Object parm:bodyParms){
            sb.append(" ").append(parm).append("}");
        }
        return sb.toString();
    }

    public static String compositionLogEmpty(String parms){
        return parms+" can not be empty !";
    }
}
