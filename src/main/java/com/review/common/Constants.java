package com.review.common;

import java.util.HashMap;
import java.util.Map;

import org.antlr.grammar.v3.ANTLRParser.finallyClause_return;

/**
 * 常量类
 * @author wwq
 *
 */
public final class Constants {
	
	/**
	 * 登录用户
	 */
	public final static String REVIEW_LOGIN_USER = "reviewUser";
	
	/**
	 * 是否推送到首页 -- 是
	 */
	public final static String IS_FIRST_PAGE = "Y";
	
	/**
	 * 是否推送到首页 -- 否
	 */
	public final static String NO_FIRST_PAGE = "N";
	
	/**
	 * 声音分类--广播
	 */
	public final static String VOICE_TYPE_BROADCAST = "1";
	
	/**
	 * 声音分类--唱吧声音
	 */
	public final static String VOICE_TYPE_SING = "2";
	
	/**
	 * 声音类别
	 */
	public final static Map<String, String> VOICE_TYPE = getVoiceTypes();
	
	/**
	 * 评论类型--发出的评论
	 */
	public final static String COMMENT_SEND = "1";
	
	/**
	 * 评论类型--收到的评论
	 */
	public final static String COMMENT_RECIEVE = "2";
	
	/**
	 * 获取声音分类
	 * @return
	 */
	private static Map<String, String> getVoiceTypes() {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put(VOICE_TYPE_BROADCAST, "广播");
		map.put(VOICE_TYPE_SING, "唱吧");
		
		return map;
	}
	
	/**
	 * 声音发布状态--未发布
	 */
	public final static Integer VOICE_STATUS_NO = 0;
	
	/**
	 * 声音发布状态--已发布
	 */
	public final static Integer VOICE_STATUS_YES = 1;
	
	/**
	 * 广告位置1
	 */
	public final static String ADLOCATION_ONE = "1";
	
	/**
	 * 广告位置2
	 */
	public final static String ADLOCATION_TWO = "2";
	
	/**
	 * 广告位置3
	 */
	public final static String ADLOCATION_THREE = "3";
	
	/**
	 * 首页广播
	 */
	public final static String ADLOCATION_FOUR = "4";
	
	/**
	 * 获取广告位置
	 * @return
	 */
	public static Map<String, String> getAdLocatioins() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(ADLOCATION_ONE,"首页焦点图1");
		map.put(ADLOCATION_TWO,"首页焦点图2");
		map.put(ADLOCATION_THREE,"首页焦点图3");
		map.put(ADLOCATION_FOUR,"首页广播");
		return map;
	}
	
	/**
	 * 积分兑换-已兑换
	 */
	public final static String POINTS_EXCHANGE_YES = "Y";
	
	/**
	 * 积分兑换-未兑换
	 */
	public final static String POINTS_EXCHANGE_NO = "N";
	
	/**
	 * 积分类型--收藏
	 */
	public final static String POINTS_TYPE_COLLECTION = "1";
	
	/**
	 * 积分类型--收听
	 */
	public final static String POINTS_TYPE_LISTEN = "2";
	
	/**
	 * 积分类型--评论
	 */
	public final static String POINTS_TYPE_COMMENT = "3";
	
	/**
	 * 积分类型--K歌
	 */
	public final static String POINTS_TYPE_SING = "4";
	
	/**
	 * 积分类型--献花
	 */
	public final static String POINTS_TYPE_FLOWER = "5";
	
	/**
	 * 积分类型--扔鸡蛋
	 */
	public final static String POINTS_TYPE_THROWEGG = "6";
	/**
	 * 点赞类型-鲜花
	 */
	public final static int praise_type_flower = 1;
	/**
	 * 点赞类型-鸡蛋
	 */
	public final static int praise_type_egg = 2;
	
	
	/**
	 * 点赞类型--献花
	 */
	public final static String PRAISE_TYPE_FLOWER = "1";
	
	/**
	 * 点赞类型--扔鸡蛋
	 */
	public final static String PRAISE_TYPE_THROWEGG = "2";
	
	//积分
	public static Map<String, String> actionPoint = new HashMap<String, String>();

	static {
		actionPoint.put(Constants.POINTS_TYPE_LISTEN, "3");
		actionPoint.put(Constants.POINTS_TYPE_COLLECTION, "1");
		actionPoint.put(Constants.POINTS_TYPE_COMMENT, "5");
		actionPoint.put(Constants.POINTS_TYPE_FLOWER, "1");
	}
	
	/**
	 * 保存
	 */
	public final static String SAVE = "1";
	
	/**
	 * 发布
	 */
	public final static String PUBLISH = "2";

	/**
	 * 字母数组
	 */
	public final static String[] LETTER_ARR = new String[]{"A","B","C","D","E","F","G","H","I","J","K"}; 
}
