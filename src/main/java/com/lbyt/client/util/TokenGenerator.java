package com.lbyt.client.util;

import com.lbyt.client.constant.ClientConstants;

public class TokenGenerator{
	
	private static final int LENGTH = 64;
	
	public static String generate(){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < LENGTH; i++) {
			sb.append(ClientConstants.TOKENS[(int)(Math.random() * ClientConstants.TOKENS.length)]);
		}
		return sb.toString();
	}
}