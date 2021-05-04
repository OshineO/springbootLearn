package com.mfk.ws.utils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {
		
	/**
	 * 配置文件名
	 */
	public static final String APPLICATION="application.properties";
	public static String getPropertiesByKey(String key ,String fileName) {
		InputStream inputStream = PropertiesUtils.class.getClassLoader()
				.getResourceAsStream(fileName);
		Properties p = new Properties();
		try {
			p.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return p.getProperty(key);
	}
	public static String getPropertiesByKey(String key ) {
		InputStream inputStream = PropertiesUtils.class.getClassLoader()
				.getResourceAsStream(APPLICATION);
		Properties p = new Properties();
		try {
			p.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return p.getProperty(key);
	}
}
