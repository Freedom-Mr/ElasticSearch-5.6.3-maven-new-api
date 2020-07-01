package casia.isiteam.api.elasticsearch.common.enums;

/**
 * ClassName: FieldOccurs
 * Description: 字段存在情况
 * <p>
 * Created by casia.wzy on 2020/4/22
 * Email: zhiyou_wang@foxmail.com
 */
public enum FieldOccurs {
	/**
	 * 包含
	 */
	INCLUDES("includes","must","+"),
	/**
	 * 不包含
	 */
	EXCLUDES("excludes","must_not","-");
	
	private String symbol;
	private String isMust;
	private String isExist;

	private FieldOccurs(String symbol,String isMust,String isExist) {
		this.symbol = symbol;
		this.isMust = isMust;
		this.isExist = isExist;
	}

	public String getSymbolValue() {
		return this.symbol;
	}

	public String getIsMust() {
		return isMust;
	}

	public String getIsExist() {
		return isExist;
	}}