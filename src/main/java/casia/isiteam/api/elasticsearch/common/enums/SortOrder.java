package casia.isiteam.api.elasticsearch.common.enums;
/**
 * ClassName: SortOrder
 * Description: 排序
 * <p>
 * Created by casia.wzy on 2020/4/22
 * Email: zhiyou_wang@foxmail.com
 */
public enum SortOrder {
	/**
	 * 正序
	 */
	ASC ( "asc" ),
	/**
	 * 倒序
	 */
	DESC ( "desc" );
	
	private String symbol;
	private SortOrder(String symbol) {
		this.symbol = symbol;
	}
	public String getSymbolValue() {
		return this.symbol;
	}
}
