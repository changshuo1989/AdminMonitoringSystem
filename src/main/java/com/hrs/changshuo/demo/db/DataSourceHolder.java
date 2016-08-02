package com.hrs.changshuo.demo.db;

public class DataSourceHolder {
	private static final ThreadLocal<String> dataSource =new ThreadLocal<String>();
	
	public static void setDataSource(String type){
		dataSource.set(type);
	}
	public static String getDataSource(){
		return (String) dataSource.get();
	}
	public static void clearDataSource(){
		dataSource.remove();
	}
}
