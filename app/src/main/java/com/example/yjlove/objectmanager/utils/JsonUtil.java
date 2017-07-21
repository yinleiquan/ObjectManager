package com.example.yjlove.objectmanager.utils;

import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/** json数据解析工具 */
public class JsonUtil {
	
	/** 用于解析json的类 */
	private static Gson GSON = new Gson();
	
	/**
	 * 把json字符串转换为JavaBean
	 * @param json json字符串
	 * @param beanClass JavaBean的Class
	 * @return
	 */
	public static <T> T json2Bean(String json, Class<T> beanClass) {
		T bean = null;
		try {
			bean = GSON.fromJson(json, beanClass);
		} catch (Exception e) {
			Log.i("JsonUtil", "解析json数据时出现异常\njson = " + json, e);
		}
		return bean;
	}
	
	/**
	 * 把json字符串转换为JavaBean。如果json的根节点就是一个集合，则使用此方法<p>
	 * type参数的获取方式为：Type type = new TypeToken<集合泛型>(){}.getType();
	 * @param json json字符串
	 * @return type 指定要解析成的数据类型
	 */
	public static <T> T json2Collection(String json, Type type) {
		T bean = null;
		try {
			bean = GSON.fromJson(json, type);
		} catch (Exception e) {
			Log.i("JsonUtil", "解析json数据时出现异常\njson = " + json, e);
		}
		return bean;
	}
	
}
