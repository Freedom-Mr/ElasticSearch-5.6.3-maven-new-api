package casia.isiteam.api.elasticsearch.common.staitcParms;

public enum HttpHeader {
	USER_AGENT_FIREFOX("User_Agent_Firefox","Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0"),
	ACCEPT("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"),
	ACCEPT_LANGUAGE("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2"),
	ACCEPT_ENCODING("Accept-Encoding","gzip, deflate"),
	CONNECTION("Connection","keep-alive"),
	UPGRADE_INSECURE_REQUESTS("Upgrade-Insecure-Requests","1"),
	AUTHORIZATION("Authorization","Basic "),
	ENCODING_UTF_8("UTF-8","utf-8"),
	ENCODING_GBK("GBK","gbk");

	private String name;
	private String value;
	private HttpHeader(String name,String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}
	public String getValue() {
		return value;
	}

}
