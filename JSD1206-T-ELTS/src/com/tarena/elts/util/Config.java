package com.tarena.elts.util;

import java.io.FileInputStream;
import java.util.Properties;

public class Config {
	// java提供的用于读取，properties文件的API
	private Properties table = new Properties();

	/**
	 * 在这个构造方法中，我们对给定文件进行解析
	 * 
	 * @param filename
	 */
	public Config(String file) {
		try {
			table.load(new FileInputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * 根据key获取对应的int型的value
	 * 
	 * @param key
	 * @return
	 */
	public int getInt(String key) {
		/**
		 * 例如：读取的properties文件是client.properties 那么当在构造方法中加载此文件后（调用过load()方法）
		 * 可以通过Properties的getProperty()方法获取下面这项的值 文件中有一项是： QuestionNumber=20
		 * 获取方法为： String value=table.getProperty("QuestionNumber");
		 */
		return Integer.parseInt(table.getProperty(key));

	}
	public double getDouble(String key){
		return Double.parseDouble(table.getProperty(key));
	}
	public String getString(String key){
		return table.getProperty(key);
	}


}
