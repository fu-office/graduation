package com.lbyt.client.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * base useful tool
 * @author zhenglianfu
 *
 */
public class BeanUtil {
	// cache for class's field
	private static final Map<String, List<Field>> classField = new HashMap<String, List<Field>>();
	
	/**	
	 * 
	 * 	if target or src is null, return src you gived
	 *  copy fields valus from src to target
	 *  base on setter and getter
	 *  your object must under the javaBean standard
	 *  
	 * @param target
	 * @param src
	 * @return
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 */
	public static Object bulidBean(Object target, Object src) throws IllegalAccessException, SecurityException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException{
		if (target == null || src == null) {
			return target;
		}
		boolean cached = false;
		String srcClassPath = src.getClass().getName(); // class path of src
		List<Field> srcFieldList = classField.get(srcClassPath); // cache of list
		Field[] srcFields = null;
		Class<? extends Object> srcClass = Class.forName(srcClassPath);
		Class<? extends Object> targetClass = Class.forName(target.getClass().getName());
		if (srcFieldList == null) {
			srcFieldList = new ArrayList<Field>();
			srcFields = srcClass.getDeclaredFields();
		} else {
			cached = true;
		}
		int i = 0, len = srcFields == null ? srcFieldList.size() : srcFields.length;
		for (; i < len; i++) {
			Field field =  srcFields == null ? srcFieldList.get(i) : srcFields[i];
			String fieldName = field.getName();
			fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			Method setMethod, getMethod;
			if (cached) {
				setMethod = targetClass.getMethod("set" + fieldName, field.getType());
				getMethod = srcClass.getMethod("get" + fieldName);
				setMethod.invoke(target, getMethod.invoke(src, null));
			} else {
				try {
					setMethod = targetClass.getMethod("set" + fieldName, field.getType());
					getMethod = srcClass.getMethod("get" + fieldName);
					setMethod.invoke(target, getMethod.invoke(src, null));
					srcFieldList.add(field);
				}catch(Exception e){}
			}
		}
		classField.put(srcClassPath, srcFieldList);
		return target;
	}
	
}
