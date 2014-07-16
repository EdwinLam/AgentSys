package com.edwin.agentsys.base;

import java.util.HashMap;
import java.util.Map;

public class Constant {
	public static int INDEX_PRODUCT_SIZE = 20;
	public static int LIST_PRODUCT_SIZE = 8;

	public static int CART_SIZE = 10;
	public static String USER_SESSION="USER_SESSION";
	public static int MIN_ORDER_PRICE=100;
	public static int MYORDER_PAGE_SIZE= 4;
	
	public static Map<String,String>  productType=new HashMap<String,String>();
	static{
		productType.put("1", "食品副食");
		productType.put("2", "酒水饮料");
		productType.put("3", "美容护理");
		productType.put("4", "日用百货");
		productType.put("5", " 进口商品");
	}
	
}
