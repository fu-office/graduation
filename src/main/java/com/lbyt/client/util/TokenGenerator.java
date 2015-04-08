package com.lbyt.client.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.lbyt.client.bean.ClientBean;
import com.lbyt.client.constant.ClientConstants;

public class TokenGenerator{
	
	private static final int LENGTH = 64;
	
	private static Map<String, ClientBean> map = new HashMap<String, ClientBean>();
	
	private static Map<String, Date> access = new HashMap<String, Date>();
	
	private static TokenGenerator.TokenTask manager = new TokenGenerator.TokenTask();
	
	private static String generate(){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < LENGTH; i++) {
			sb.append(ClientConstants.TOKENS[(int)(Math.random() * ClientConstants.TOKENS.length)]);
		}
		return sb.toString();
	}
	
	public static String generate(ClientBean client){
		String token = generate();
		map.put(token, client);
		return token;
	}
	
	public static ClientBean getClientByToken(String token){
		return map.get(token);
	}
	
	public static ClientBean destoryClientByToken(String token){
		return map.remove(token);
	}
	
	public static ClientBean updateAccess(String token) {
		// update access date
		access.put(token, new Date());
		return map.get(token);
	}
	
	private static void clearUnableToken(){
		
	}
	
	public static class TokenTask implements Runnable{
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			TokenGenerator.clearUnableToken();
		}
		
	}
}