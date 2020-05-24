package casia.isiteam.api.elasticsearch.common.vo;

/**
 * ClassName: _Entity_Db
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/4/28
 * Email: zhiyou_wang@foxmail.com
 */
public class _Entity_Db {
    private String dbname;
    private String ipPort;
    private String username;
    private String password;

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getIpPort() {
        return ipPort;
    }

    public void setIpPort(String ipPort) {
        this.ipPort = ipPort;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public _Entity_Db(String dbname, String ipPort, String username, String password) {
        this.dbname = dbname;
        this.ipPort = ipPort;
        this.username = username;
        this.password = password;
    }
    public _Entity_Db() {
    }
}
