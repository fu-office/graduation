package com.lbyt.client.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggerService{
	
	private final Map<Class<?>, Logger> map = new HashMap<Class<?>, Logger>();
	
	public Logger getLogger(Class<?> clazz){
		if (!map.containsKey(clazz)) {
			synchronized(clazz.getName()){
				if (!map.containsKey(clazz)) {
					map.put(clazz, LoggerFactory.getLogger(clazz));
				}
			}
		}
		return map.get(clazz);
	}
	
}