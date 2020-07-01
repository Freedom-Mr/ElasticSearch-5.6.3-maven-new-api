package casia.isiteam.api.elasticsearch.common.staitcParms;

/**
 * ClassName: ShareParms
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/4/28
 * Email: zhiyou_wang@foxmail.com
 */
public class ShareParms extends ElasticResultParms{
    public final static String APPLICATION = "application";
    public final static String CONFIG_APPLICATION = "config/application.properties";

    /**
     * symbol
     */
    public final static String DOT = "\\.";
    public final static String COMMA = ",";
    public final static String COLON = ":";
    public final static String SEMICOLON = ";";
    public final static String N = "\n";
    public final static String T = "\t";
    public final static String BLANK = " ";
    public final static String LINE = "|";
    public final static String NONE = "";
    public final static String SLASH = "/";
    public final static String NULL = null;
    public final static String QUESTION = "?";
    public final static String EQUAL = "=";
    public final static String RN = "\r\n";
    public final static String CROSS = "-";
    public final static String STAR = "*";
    public final static String PLUS = "+";


    public final static String COMMA_OR_SEMICOLON = COMMA+LINE+SEMICOLON;
    public final static String BLANK_OR_T = BLANK+LINE+T;



    public final static String HTTP = "http://";

    /**
     *  letter
     */
    public final static String h = "h";

    /**
     * number
     */
    public final static String MAX_1000000 = "1000000";

    /**
     * regex
     */
    protected final static String DB_KEY = "^spring.datasource.elasticsearch.\\S*.\\S*";
    protected final static String AGGS_FIELD = "^(\\?)\\S*(\\?)";

    /**
     * db
     */
    protected final static String DBNAME = "dbname";
    protected final static String IP_PORT = "ip-port";
    protected final static String USERNAME = "username";
    protected final static String PASSWORD = "password";

    /**
     * label
     */
    public final static String MARK_S = "<mark>";
    public final static String MARK_E = "</mark>";
}
