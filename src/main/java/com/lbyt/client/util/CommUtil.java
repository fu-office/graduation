package com.lbyt.client.util;

/**
 * base useful tool
 * @author zhenglianfu
 *
 */
public class CommUtil {
	
	public static boolean isEmpty(String str) {
		return str == null || "".equals(str);
	}
	
	// 模糊查询过滤特殊字符, 以 '\' 转义
	public static String filterLikeString(String string) {
		return "%" + string.replaceAll("_", "\\\\_").replaceAll("%", "\\\\%").replaceAll("＿", "\\\\＿").trim() + "%";
	}
}
