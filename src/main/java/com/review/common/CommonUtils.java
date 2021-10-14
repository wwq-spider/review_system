package com.review.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.util.UUIDGenerator;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 主页面datagrid表格数据处理公共方法类
 * @author guoxin
 *
 */
public class CommonUtils {

	public static String readTxtFile(MultipartFile file){
		String lineTxt = null;
		StringBuffer strBuffer = new StringBuffer();
		try {
			String encoding="GBK";
			InputStreamReader read = new InputStreamReader(file.getInputStream(),encoding);//考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);

			while((lineTxt = bufferedReader.readLine()) != null){
				strBuffer.append(lineTxt);
			}
			read.close();
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return strBuffer.toString();
	}

	/**
	 * 将Map中key value中的value由Object转成String[]
	 * @param map
	 * @return
	 */
	public static Map<String, String[]> mapStringToArray(Map<String, Object> map) {
		Map<String, String[]> mapArr = new HashMap<String, String[]>();
		for(Entry<String, Object> entry : map.entrySet()) {
			mapArr.put(entry.getKey(), new String[]{(entry.getValue().toString())});
		}
		return mapArr;
	}

	/**
	 * 通过response返回JSON数据
	 * @param response
	 * @param jObject
	 */
	public static void responseDatagrid(HttpServletResponse response, JSONObject jObject) {
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-store");
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write(jObject.toString());
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(pw);
		}
	}

	public static void responseDatagrid(HttpServletResponse response, JSONObject jObject, String contextType) {
		response.setContentType(contextType);
		response.setHeader("Cache-Control", "no-store");
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write(jObject.toString());
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(pw);
		}
	}

	// 以下各函数可以提成共用部件 (Add by Quainty)
	/**
	 * 返回easyUI的DataGrid数据格式的JSONObject对象
	 * @param mapList : 从数据库直接取得的结果集列表
	 * @param iTotalCnt : 从数据库直接取得的结果集总数据条数
	 * @param dataExchanger : 页面表示数据与数据库字段的对应关系列表
	 * @return JSONObject
	 */
	public static JSONObject getJsonDatagridEasyUI(List<Map<String, Object>> mapList, int iTotalCnt, Db2Page[] dataExchanger) {
		//easyUI的dataGrid方式  －－－－这部分可以提取成统一处理
		String jsonTemp = "{\'total\':" + iTotalCnt + ",\'rows\':[";
		for (int j = 0; j < mapList.size(); j++) {
			Map<String, Object> m = mapList.get(j);
			if (j > 0) {
				jsonTemp += ",";
			}
			jsonTemp += "{";
			for (int i = 0; i < dataExchanger.length; i++) {
				if (i > 0) {
					jsonTemp += ",";
				}
				jsonTemp += "'" + dataExchanger[i].getKey() + "'" + ":";
				Object objValue = dataExchanger[i].getData(m);
				if (objValue == null) {
					jsonTemp += "null";
				} else {
					jsonTemp += "'" + objValue + "'";
				}
			}
			jsonTemp += "}";
		}
		jsonTemp += "]}";
		JSONObject jObject = JSONObject.fromObject(jsonTemp);
		return jObject;
	}

	/**
	 * 返回jsonObject，不带总条数
	 * @param mapList
	 * @param dataExchanger
	 * @return
	 */
	public static JSONObject getJsonDatagridEasyUINoPage(List<Map<String, Object>> mapList, Db2Page[] dataExchanger) {
		DecimalFormat df = new DecimalFormat("0.00");
		String jsonTemp = "{\'rows\':[";
		for (int j = 0; j < mapList.size(); j++) {
			Map<String, Object> m = mapList.get(j);
			if (j > 0) {
				jsonTemp += ",";
			}
			jsonTemp += "{";
			for (int i = 0; i < dataExchanger.length; i++) {
				if (i > 0) {
					jsonTemp += ",";
				}
				jsonTemp += "'" + dataExchanger[i].getKey() + "'" + ":";
				Object objValue = dataExchanger[i].getData(m);
				if(objValue instanceof Double){
					String format = df.format(objValue);
					if (format == null) {
						jsonTemp += "null";
					} else {
						jsonTemp += "'" + format + "'";
					}
				}else{
					if (objValue == null) {
						jsonTemp += "null";
					} else {
						jsonTemp += "'" + objValue + "'";
					}
				}
			}
			jsonTemp += "}";
		}
		jsonTemp += "]}";
		JSONObject jObject = JSONObject.fromObject(jsonTemp);
		return jObject;
	}

	/**
	 * 生成easyui需要的json数据（可以将double类型值过大的转换为标准数字）
	 * 例如：1.63789296356e9  转换为1637892963.56
	 * @param mapList
	 * @param iTotalCnt
	 * @param dataExchanger
	 * @return
	 */
	public static JSONObject getJsongridEasyUI(List<Map<String, Object>> mapList, int iTotalCnt, Db2Page[] dataExchanger) {
		//easyUI的dataGrid方式  －－－－这部分可以提取成统一处理
		DecimalFormat df = new DecimalFormat("0.00");
		String jsonTemp = "{\'total\':" + iTotalCnt + ",\'rows\':[";
		for (int j = 0; j < mapList.size(); j++) {
			Map<String, Object> m = mapList.get(j);
			if (j > 0) {
				jsonTemp += ",";
			}
			jsonTemp += "{";
			for (int i = 0; i < dataExchanger.length; i++) {
				if (i > 0) {
					jsonTemp += ",";
				}
				jsonTemp += "'" + dataExchanger[i].getKey() + "'" + ":";
				Object objValue = dataExchanger[i].getData(m);
				if(objValue instanceof Double){
					String format = df.format(objValue);
					if (format == null) {
						jsonTemp += "null";
					} else {
						jsonTemp += "'" + format + "'";
					}
				}else{
					if (objValue == null) {
						jsonTemp += "null";
					} else {
						jsonTemp += "'" + objValue + "'";
					}
				}

			}
			jsonTemp += "}";
		}
		jsonTemp += "]}";
		JSONObject jObject = JSONObject.fromObject(jsonTemp);
		return jObject;
	}


	/**
	 * 保存封面图片
	 * @param contentImg
	 * @throws IOException
	 */
	public static String saveCoverImg(CommonsMultipartFile contentImg, String rePath) {
		//上传封面图片
		if (contentImg != null && !contentImg.isEmpty()) {
			String path = OssUtils.uploadFile(rePath + "/%s/" + UUIDGenerator.generate() + ".jpg", contentImg.getBytes());
			if (StringUtils.isNotBlank(path)) {
				return path;
			}
		}
		return null;
	}
}
