package com.review.common;

import java.util.Map;



/**
 * 将结果集转换成页面上对应的数据集
 * 注意：当页面的fieldID与数据库字段不一致，且数据要进行变换时，需要增加处理方法
 * @author guoxin
 *
 */
public class Db2Page {
	
	String fieldPage;		// 页面的fieldID
	String columnDB;		// 数据库的字段名
	IMyDataExchanger dataExchanger;		// 数据变换
	
	/**
	 * 当页面的fieldID与数据库字段一致时（数据也不用变换）
	 * @param fieldPage
	 */
	public Db2Page(String fieldPage) {
		this.fieldPage = fieldPage;
		this.columnDB = fieldPage;
		///this.dataExchanger = null;
	}
	
	/**
	 * 当页面的fieldID与数据库字段不一致时（数据不用变换）
	 * @param fieldPage
	 * @param columnDB
	 */
	public Db2Page(String fieldPage, String columnDB) {
		this.fieldPage = fieldPage;
		if (columnDB == null) {// 与fieldPage相同
			this.columnDB = fieldPage;
		} else {
			this.columnDB = columnDB;
		}
		///this.dataExchanger = null;
	}
	
	// 构造函数3：当页面的fieldID与数据库字段不一致，且数据要进行变换（当然都用这个构造函数也行）
	public Db2Page(String fieldPage, String columnDB, IMyDataExchanger dataExchanger) {
		this.fieldPage = fieldPage;
		if (columnDB == null) {// 与fieldPage相同
			this.columnDB = fieldPage;
		} else {
			this.columnDB = columnDB;
		}
		this.dataExchanger = dataExchanger;
	}
	
	/**
	 * 取页面表示绑定的fieldID
	 */
	public String getKey() {
		return fieldPage;
	}
	
	/**
	 * 取页面表示对应的值
	 * @param mapDB : 从数据库直接取得的结果集(一条数据的MAP)
	 * @return Object : 页面表示对应的值
	 */
	@SuppressWarnings("rawtypes")
	public Object getData(Map mapDB) {
		Object objValue = mapDB.get(columnDB);
		if (objValue == null) {
			return null;
		} else {
			//return objValue;
			if (dataExchanger != null) {
				return dataExchanger.exchange(objValue);
			} else {
				return objValue;
			}
		}
	}
	
}
