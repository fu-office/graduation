package com.lbyt.client.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class WebConfig {
	private static Map<String, String> config = null;

	private static final String USER_CONFIG = "user.properties";

	public enum KV {
		JDBC_URI, JDBC_USERNAME, JDBS_PASSWORD
	}

	public static String getConfig(KV name) throws IOException {
		getConfig().get(name.toString());
		return null;
	}

	private static Map<String, String> getConfig() throws IOException {
		if (null == config) {
			synchronized (WebConfig.class) {
				Properties properties = new Properties();
				properties.load(WebConfig.class.getClassLoader()
						.getResourceAsStream(USER_CONFIG));
				config = new HashMap<String, String>();
				for (Object key : properties.keySet()) {
					config.put(key.toString().trim().toUpperCase(), properties.getProperty(key.toString().trim()));
				}
			}
		}
		return config;
	}
}
