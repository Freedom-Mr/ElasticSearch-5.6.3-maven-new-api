package casia.isiteam.api.elasticsearch.common.enums;

/**
 * ClassName: Symbol
 * Description: 符号
 * <p>
 * Created by casia.wzy on 2020/4/22
 * Email: zhiyou_wang@foxmail.com
 */
public enum Symbol {
	SPACE_CHARACTER("&");
	
	private String symbol;
	private Symbol(String symbol) {
		this.symbol = symbol;
	}
	public String getSymbolValue() {
		return this.symbol;
	}
}
