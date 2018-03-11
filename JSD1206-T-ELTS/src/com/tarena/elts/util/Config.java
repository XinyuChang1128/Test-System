package com.tarena.elts.util;

import java.io.FileInputStream;
import java.util.Properties;

public class Config {
	// java�ṩ�����ڶ�ȡ��properties�ļ���API
	private Properties table = new Properties();

	/**
	 * ��������췽���У����ǶԸ����ļ����н���
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
	 * ����key��ȡ��Ӧ��int�͵�value
	 * 
	 * @param key
	 * @return
	 */
	public int getInt(String key) {
		/**
		 * ���磺��ȡ��properties�ļ���client.properties ��ô���ڹ��췽���м��ش��ļ��󣨵��ù�load()������
		 * ����ͨ��Properties��getProperty()������ȡ���������ֵ �ļ�����һ���ǣ� QuestionNumber=20
		 * ��ȡ����Ϊ�� String value=table.getProperty("QuestionNumber");
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
