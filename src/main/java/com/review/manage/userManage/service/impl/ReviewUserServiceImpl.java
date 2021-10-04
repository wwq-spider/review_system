package com.review.manage.userManage.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.poi.excel.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.review.common.Arith;
import com.review.front.entity.ReviewResultEntity;
import com.review.front.service.ReviewFrontService;
import com.review.manage.question.vo.QuestionVO;
import com.review.manage.report.entity.ReviewReportEntity;
import com.review.manage.report.entity.ReviewReportVariateEntity;
import com.review.manage.report.vo.ReportVO;
import com.review.manage.userManage.entity.ReviewUserEntity;
import com.review.manage.userManage.service.ReviewUserService;
import com.review.manage.variate.entity.ReviewVariateEntity;
import com.review.manage.variate.vo.VariateVO;

@SuppressWarnings("deprecation")
@Service("reviewUserService")
public class ReviewUserServiceImpl extends CommonServiceImpl implements ReviewUserService {

	@Autowired
	private ReviewFrontService reviewFrontService;
	
	@Override
	public ReviewUserEntity getUserByUserName(String userName) {
		
		return this.findUniqueByProperty(ReviewUserEntity.class, "userName", userName);
	}

	@Override
	public List<Map<String, Object>> getReviewUserList(String userName, String realName, String groupId, int page, int rows) {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT  ");
		sb.append("   u.user_name userName,");
		sb.append("   u.real_Name realName,");
		sb.append("   u.user_id id, ");
		sb.append("   u.group_id groupId ");
		sb.append(" FROM review_user u  ");
		sb.append(" WHERE 1=1");
		if(!"".equals(StringUtils.trimToEmpty(userName))) {
			sb.append(" AND user_name='"+userName+"'");
		}
		if(!"".equals(StringUtils.trimToEmpty(realName))) {
			sb.append(" AND real_name='"+realName+"'");
		}
		if(StringUtils.isNotBlank(groupId)) {
			sb.append(" AND group_id='"+groupId+"'");
		}
		sb.append(" ORDER BY u.user_id desc");
		return this.findForJdbc(sb.toString(), page, rows);
	}

	@Override
	public Long getReviewUserCount(String userName,  String realName, String groupId) {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT  ");
		sb.append("   COUNT(u.user_id)");
		sb.append(" FROM review_user u  ");
		sb.append(" WHERE 1=1");
		if(!"".equals(StringUtils.trimToEmpty(userName))) {
			sb.append(" AND user_name='"+userName+"'");
		}
		if(!"".equals(StringUtils.trimToEmpty(realName))) {
			sb.append(" AND real_name='"+realName+"'");
		}
		if(StringUtils.isNotBlank(groupId)) {
			sb.append(" AND group_id='"+groupId+"'");
		}
		return this.getCountForJdbc(sb.toString());
	}

	@Override
	public void delUser(String userId) {
		this.deleteEntityById(ReviewUserEntity.class, userId);
	}

	@Override
	public void addorupdateUser(ReviewUserEntity reviewUser) {
		ReviewUserEntity user = new ReviewUserEntity();
		if(!"".equals(StringUtils.trimToEmpty(reviewUser.getUserId()))) { //修改用户信息
			user = this.get(ReviewUserEntity.class, reviewUser.getUserId());
			try {
				MyBeanUtils.copyBean2Bean(user, reviewUser);
				this.saveOrUpdate(user);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else { //新增用户
			this.save(reviewUser);
		}
	}

	@Override
	public String importUserAndAnswer(HttpServletRequest request,
			HttpServletResponse response) {
		String strTempPath = "";
		String classId = request.getParameter("classId");
		String groupId = request.getParameter("groupId");
		
		String questionNum = null;
		String titleText = "";
		String cellValue = "";

		Workbook workbook = null;
		Map<Integer, String> titleMap = new HashMap<Integer, String>();
		Sheet sheet = null;
		
		//先查询出该分类下的所有题目
		Map<String, QuestionVO> questionMap = new HashMap<String, QuestionVO>();
		
		//查询某分类下的报告
		List<ReviewReportEntity> reportList = this.findByProperty(ReviewReportEntity.class, "classId", classId);
		
		//查询分类下的题目
		List<QuestionVO> questionVOList = reviewFrontService.getQuestionVOList(classId, 0, 0);
	
		//纬度VOList
		List<ReportVO> reportVOList = null;
		
		//纬度VO
		ReportVO reportVO = null;
		
		//用户列表
		List<ReviewUserEntity> userList = new ArrayList<ReviewUserEntity>();
		
		//报告因子
		List<ReviewReportVariateEntity> reportVariateList = null;
		
		List<Map<String, Object>> selectList = null;
		Map<String, Double> gradeMap = null;
		for(QuestionVO questionVO : questionVOList) {
			selectList = this.findForJdbc("select s.grade,s.answer_code selCode from review_answer s where s.question_id=?", 
					new Object[]{questionVO.getQuestionId()});
			gradeMap = new HashMap<String, Double>();
			for(Map<String, Object> map : selectList) {
				gradeMap.put(map.get("selCode").toString(), Double.valueOf(map.get("grade").toString()));
			}
			questionVO.setGradeMap(gradeMap);
			questionMap.put(questionVO.getQuestionNum().toString(), questionVO);
		}
		
		QuestionVO question = null;
		Map<String, VariateVO> variateMap = null;
		VariateVO variateVO = null;
		ReviewVariateEntity variateEntity = null;
		Double variateGrade = null;
		Double reportGrade = null;
		ReviewUserEntity reviewUser = null;
		ReviewUserEntity userEntity = null;

		ReviewVariateEntity variate = null;
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		try {
			
			MultipartFile file = null;
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
					file = entity.getValue();// 获取上传文件对象
					workbook = WorkbookFactory.create(file.getInputStream());
					int sheetNum = workbook.getNumberOfSheets();//得到sheet总数
					for(int i=0; i < sheetNum; i++) {
						sheet = workbook.getSheetAt(i);
						if(sheet.getLastRowNum() == 0) {
							continue;
						}
						for(int k=0; k<=sheet.getLastRowNum(); k++) {
							Row sheetRow = sheet.getRow(k); //获取一行内容
							if(sheetRow == null) {
								continue;
							}
							if(k > 0) {
								reviewUser = new ReviewUserEntity();
								reviewUser.setGroupId(groupId);
								reportVOList = new ArrayList<ReportVO>();
								variateMap = new HashMap<String, VariateVO>();
							}
							for(int j=0; j < sheetRow.getLastCellNum(); j++) {
								Cell cell = sheetRow.getCell(j); //得到某行中的一列
								if(cell == null) {
									continue;
								}
								if(k == 0) { //判断是否是标题行
									if(j > 5) {
										titleMap.put(j, getNumInString(getXSSFValue(cell)));
									} else {
										titleMap.put(j, getXSSFValue(cell));
									}
								} else {
									cellValue = getXSSFValue(cell);//获取单元格中的值
									if(j > 5) {
										variateGrade = 0.0;
										questionNum = titleMap.get(j).toString();
										question = questionMap.get(questionNum);
										//判断该题目是否属于多个 因子
										if(!"".equals(StringUtils.trimToEmpty(question.getVariateId()))) {
											if(question.getVariateId().indexOf(",") > -1) {
												String[] varaiteIdArr = question.getVariateId().split(",");
												for(int t=0; t<varaiteIdArr.length; t++) {
													if(variateMap.get(varaiteIdArr[t]) != null) {
														variateGrade = variateMap.get(varaiteIdArr[t]).getGrade();
														if(question.getGradeMap().get(cellValue) != null) {
															variateMap.get(varaiteIdArr[t]).setGrade(Arith.add(variateGrade,
																	question.getGradeMap().get(cellValue)));
														}
													} else {
														variateVO = new VariateVO();
														variateVO.setVariateId(varaiteIdArr[t]);
														variateEntity = this.get(ReviewVariateEntity.class, varaiteIdArr[t]);
														variateVO.setVariateName(question.getVariateName().split(",")[t]);
														variateVO.setCalSymbol(variateEntity.getCalSymbol());
														variateVO.setCalSymbol1(variateEntity.getCalSymbol1());
														variateVO.setCalTotal(variateEntity.getCalTotal());
														variateVO.setCalTotal1(variateEntity.getCalTotal1());
														if(question.getGradeMap().get(cellValue) != null) {
															variateVO.setGrade(question.getGradeMap().get(cellValue));
														} else {
															variateVO.setGrade(0.0);
														}
														variateVO.setVariateNum(variateEntity.getSortNum());
														variateMap.put(varaiteIdArr[t], variateVO);
													}
												}
											} else {
												if(variateMap.get(question.getVariateId()) != null) {
													variateGrade = variateMap.get(question.getVariateId()).getGrade();
													if(question.getGradeMap().get(cellValue) != null) {
														variateMap.get(question.getVariateId()).setGrade(Arith.add(variateGrade,
																question.getGradeMap().get(cellValue)));
													}
												} else {
													if(!"".equals(StringUtils.trimToEmpty(question.getVariateId()))) {
														variateVO = new VariateVO();
														variateVO.setVariateId(question.getVariateId());
														
														variateEntity = this.get(ReviewVariateEntity.class, question.getVariateId());
														variateVO.setVariateName(question.getVariateName());
														variateVO.setCalSymbol(variateEntity.getCalSymbol());
														variateVO.setCalSymbol1(variateEntity.getCalSymbol1());
														variateVO.setCalTotal(variateEntity.getCalTotal());
														variateVO.setCalTotal1(variateEntity.getCalTotal1());
														if(question.getGradeMap().get(cellValue) != null) {
															variateVO.setGrade(question.getGradeMap().get(cellValue));
														} else {
															variateVO.setGrade(0.0);
														}
														
														variateVO.setVariateNum(variateEntity.getSortNum());
														variateMap.put(question.getVariateId(), variateVO);
													}
												}
											}
										}
									} else {
										titleText = titleMap.get(j);
										if("用户名".equals(titleText)) {
											reviewUser.setUserName(cellValue);
										} else if("登录密码".equals(titleText)) {
											if(cellValue.endsWith(".0")) {
												reviewUser.setPassword(cellValue.split("\\.")[0]);
											}
										} else if("身份证号".equals(titleText)) {
											reviewUser.setIdCard(cellValue);
										} else if("性别".equals(titleText)) {
											reviewUser.setSex(cellValue);
										} else if("年龄".equals(titleText)) {
											reviewUser.setAge(Integer.parseInt(cellValue.split("\\.")[0]));
										} 
									}							
								}
							}

							if(k > 0) {
								Double variateTotalGrade = null;
								//循环报告结果
								for(ReviewReportEntity report : reportList) {
									reportVO = new ReportVO();
									reportGrade = 0.0;
									variateTotalGrade = 0.0;
									reportVariateList = this.findByProperty(ReviewReportVariateEntity.class, "reportId", report.getReportId());
									for(ReviewReportVariateEntity reportVariate : reportVariateList) {
										if(variateMap.get(reportVariate.getVariateId()) != null) {
											
											//System.out.println("1："+variateMap.get(reportVariate.getVariateId()).getGrade());
											
											variate = this.get(ReviewVariateEntity.class, reportVariate.getVariateId());
											
											//根据因子的总计算条目计算 最后得分
											
											variateTotalGrade = calVariateGrade(variate.getCalSymbol1(), variateMap.get(reportVariate.getVariateId()).getGrade(), variate.getCalTotal1());
											
											//System.out.println("2："+variateTotalGrade);
											
											variateTotalGrade = calVariateGrade(variate.getCalSymbol(), variateTotalGrade, variate.getCalTotal());
											
											//System.out.println("3："+variateTotalGrade);
											reportGrade = Arith.add(reportGrade, variateTotalGrade);
										}
									}				
									reportVO.setGrade(reportGrade);
									reportVO.setReportName(report.getReportName());
									reportVOList.add(reportVO);
								}
								
								//添加用户
								if(!"".equals(StringUtils.trimToEmpty(reviewUser.getUserName()))){
									userEntity = this.findUniqueByProperty(ReviewUserEntity.class, "userName", reviewUser.getUserName());
									reviewUser.setReportVOList(reportVOList);
									reviewUser.setVariateMap(variateMap);
									userList.add(reviewUser);
									if(userEntity == null) {
										this.save(reviewUser);	
									}
								}
							}
						}
					}
				}
			strTempPath = excelExport(request, userList);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return strTempPath;
	}
	
	private String getNumInString(String str) {
		Pattern pattern = Pattern.compile("[^0-9]");
		Matcher matcher = pattern.matcher(str);
		String num = matcher.replaceAll("");
		//System.out.println("phone:" + all);
		// 2
		return num;
	}
	
	/**
	 * 计算因子得分 
	 * @param calymbol
	 * @param grade
	 * @param calTotal
	 * @return
	 */
	private Double calVariateGrade(String calymbol, Double grade,Double calTotal) {
		if(calTotal != null && calTotal > 0) {
			if("+".equals(StringUtils.trimToEmpty(calymbol))) {
				grade += calTotal;
			} else if ("-".equals(StringUtils.trimToEmpty(calymbol))) {
				grade -= calTotal;
			} else if ("*".equals(StringUtils.trimToEmpty(calymbol))) {
				grade = grade * calTotal;
			} else if ("/".equals(StringUtils.trimToEmpty(calymbol))) {
				grade = Arith.div(grade, calTotal, 2);
			}
			return grade;
		} else {
			if(grade == null) {
				return 0.0;
			} else {
				return grade;
			}
			
		}
		
	}
	
	/**
	 * 获取单元格信息
	 * @param xssfCell
	 * @return
	 */
	@SuppressWarnings("static-access")
	private String getXSSFValue(Cell xssfCell) {
		if (xssfCell.getCellType() == xssfCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(xssfCell.getBooleanCellValue());
		} else if (xssfCell.getCellType() == xssfCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(xssfCell.getNumericCellValue());
		} else {
			return String.valueOf(xssfCell.getStringCellValue());
		}
	}
	
	/**
	 * excel导出
	 */
	public String excelExport(HttpServletRequest request,
			List<ReviewUserEntity> userList){
		
		String strPathTemp = null;
		WritableSheet ws = null;
		try {
			Properties props = System.getProperties();
			String separator = props.getProperty("file.separator");// 文件分隔符
		
			// 设置文档生成的临时位置
			strPathTemp = request.getSession().getServletContext().getRealPath(separator)
					+ separator
					+ "result.xls";
			WritableWorkbook book = jxl.Workbook.createWorkbook(new File(strPathTemp));
			WritableSheet sheet = book.createSheet("Sheet1", 0);//创建一个Sheet
			ws = book.getSheet(0);
	
			//居中宋体，带边框 20号字居中
			jxl.write.WritableFont wfTextCenterTitle = new jxl.write.WritableFont(WritableFont.createFont("宋体"),20,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK);
			jxl.write.WritableCellFormat wcfTextCenterTitle = new jxl.write.WritableCellFormat(wfTextCenterTitle);
			wcfTextCenterTitle.setAlignment(jxl.format.Alignment.CENTRE);
			wcfTextCenterTitle.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			wcfTextCenterTitle.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.NONE);
			wcfTextCenterTitle.setWrap(true);
			
			//居中宋体，带边框 14号字居中
			jxl.write.WritableFont wfTextCenterDept = new jxl.write.WritableFont(WritableFont.createFont("宋体"),14,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK);
			jxl.write.WritableCellFormat wcfTextCenterDeptTitle = new jxl.write.WritableCellFormat(wfTextCenterDept);
			wcfTextCenterDeptTitle.setBackground(Colour.LIGHT_GREEN);
			wcfTextCenterDeptTitle.setAlignment(jxl.format.Alignment.CENTRE);
			wcfTextCenterDeptTitle.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			wcfTextCenterDeptTitle.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			wcfTextCenterDeptTitle.setWrap(true);
			
			// 居中宋体，带边框 14号字居中			
			jxl.write.WritableFont wfTextCenter = new jxl.write.WritableFont(WritableFont.createFont("宋体"),14,WritableFont.NO_BOLD,false,UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK);
			jxl.write.WritableCellFormat wcfTextCenter = new jxl.write.WritableCellFormat(wfTextCenter);
			wcfTextCenter.setAlignment(jxl.format.Alignment.CENTRE);
			wcfTextCenter.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			wcfTextCenter.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			wcfTextCenter.setWrap(true);
						
			//*************开始将数据写入EXCEL并嵌入样式************
			sheet.setColumnView(0, 20);//设置列宽 第0列宽20
			sheet.setColumnView(1, 20);//设置列宽 第1列宽20
			sheet.setColumnView(2, 20);//设置列宽 第2列宽20
			sheet.setColumnView(3, 30);//设置列宽 第3列宽30
			sheet.setColumnView(4, 10);//设置列宽 第4列宽20
			sheet.setColumnView(5, 10);//设置列宽 第5列宽20
			ws.insertRow(0);
			ws.addCell(new Label(0, 0, "序号", wcfTextCenterDeptTitle));//显示表格的标题信息
			ws.addCell(new Label(1, 0, "用户名", wcfTextCenterDeptTitle));//显示表格的标题信息
			ws.addCell(new Label(2, 0, "登录密码", wcfTextCenterDeptTitle));//显示表格的标题信息
			ws.addCell(new Label(3, 0, "身份证号", wcfTextCenterDeptTitle));//显示表格的标题信息
			ws.addCell(new Label(4, 0, "性别", wcfTextCenterDeptTitle));//显示表格的标题信息
			ws.addCell(new Label(5, 0, "年龄", wcfTextCenterDeptTitle));//显示表格的标题信息
		
			List<ReportVO> reportVOList = null;
			ReportVO reportVO = null;
			ReviewUserEntity user = null;

			Map<String, VariateVO> variateMap = null;
			int k=0;
			Double variateTotalGrade = null;
			List<Entry<String, VariateVO>> list = null;
			Entry<String, VariateVO> entry = null;
			for(int j = 0; j<userList.size(); j++) {
				k=0;
				user = userList.get(j);
				ws.insertRow(j+1);
				this.addCell(ws, j+1, user, wcfTextCenter);//插入用户信息
				
				//遍历因子
				variateMap = user.getVariateMap();
				String varaiteName = "";
				
				list = this.sortMap(variateMap);
				
				for(int t=0; t<list.size(); t++) {
					entry = list.get(t);
					k++;
					varaiteName = entry.getValue().getVariateName();
					if(j == 0) {
						sheet.setColumnView(k+5, 15);//设置列宽 第k+5列宽10
						ws.addCell(new Label(k+5, 0, varaiteName, wcfTextCenterDeptTitle));//插入因子名称
					} 
					
					variateTotalGrade = calVariateGrade(entry.getValue().getCalSymbol1(), entry.getValue().getGrade(), entry.getValue().getCalTotal1());
					
					//System.out.println("2："+variateTotalGrade);
					
					variateTotalGrade = calVariateGrade(entry.getValue().getCalSymbol(), variateTotalGrade, entry.getValue().getCalTotal());

					ws.addCell(new Label(k+5, j+1, variateTotalGrade.toString(), wcfTextCenter));//插入分值
				}
				
				/*for(Entry<String, VariateVO> entry : variateMap.entrySet()) {
					k++;
					varaiteName = entry.getValue().getVariateName();
					if(j == 0) {
						sheet.setColumnView(k+5, 15);//设置列宽 第k+5列宽10
						ws.addCell(new Label(k+5, 0, varaiteName, wcfTextCenterDeptTitle));//插入因子名称
					} 
					
					variateTotalGrade = calVariateGrade(entry.getValue().getCalSymbol1(), entry.getValue().getGrade(), entry.getValue().getCalTotal1());
					
					//System.out.println("2："+variateTotalGrade);
					
					variateTotalGrade = calVariateGrade(entry.getValue().getCalSymbol(), variateTotalGrade, entry.getValue().getCalTotal());

					ws.addCell(new Label(k+5, j+1, variateTotalGrade.toString(), wcfTextCenter));//插入分值
					
				}*/
				
				//遍历维度
				reportVOList = user.getReportVOList();
				for(int i=0; i<reportVOList.size(); i++) {
					
					reportVO = reportVOList.get(i);
					if(j == 0) {
						sheet.setColumnView(i+k+6, 15);//设置列宽 第i+k+6列宽10
						ws.addCell(new Label(i+k+6, 0, reportVO.getReportName(), wcfTextCenterDeptTitle));//插入维度名称
					} 
					ws.addCell(new Label(i+k+6, j+1, reportVO.getGrade().toString(), wcfTextCenter));//插入分值
				}
				
			}
			book.write();
			book.close();
		    return strPathTemp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private List<Entry<String, VariateVO>> sortMap(Map<String, VariateVO> map) {
		List<Entry<String, VariateVO>> list = new ArrayList<Entry<String,VariateVO>>(map.entrySet());
		
		Collections.sort(list, new Comparator<Entry<String, VariateVO>>() {

			@Override
			public int compare(Entry<String, VariateVO> o1,
					Entry<String, VariateVO> o2) {
				
				return o1.getValue().getVariateNum().compareTo(o2.getValue().getVariateNum());
			}
		});
		return list;
	}
	
	/**
	 * 往一行中添加数据
	 * @param ws
	 * @param row
	 * @param user
	 * @param wcfTextCenter
	 */
	public void addCell(WritableSheet ws, int row, 
			ReviewUserEntity user,jxl.write.WritableCellFormat wcfTextCenter){
		try {
			ws.addCell(new jxl.write.Number(0, row, row, wcfTextCenter));
			ws.addCell(new Label(1, row, user.getUserName(), wcfTextCenter));
			ws.addCell(new Label(2, row, user.getPassword(), wcfTextCenter));
			if(!"".equals(StringUtils.trimToEmpty(user.getIdCard()))) {
				ws.addCell(new Label(3, row, user.getIdCard().split("\\.")[0], wcfTextCenter));
			}
			ws.addCell(new Label(4, row, user.getSex(), wcfTextCenter));
			ws.addCell(new jxl.write.Number(5, row, user.getAge(), wcfTextCenter));
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	public String importUser(Map<String, MultipartFile> fileMap, String groupId) throws IOException {
		String userNames = "";
		MultipartFile file = null;
		List<ReviewUserEntity> userList;
		ReviewUserEntity userEntity = null;
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			file = entity.getValue();// 获取上传文件对象
			
			userList = (List<ReviewUserEntity>) ExcelUtil.importExcelByIs(file.getInputStream(), ReviewUserEntity.class);
			
			for(ReviewUserEntity user : userList) {
				user.setGroupId(groupId);
				userEntity= this.findUniqueByProperty(ReviewUserEntity.class, "userName", user.getUserName());
				if(userEntity == null) {
					this.save(user);
				} else {
					if("".equals(userNames)) {
						userNames = user.getUserName();
					} else {
						userNames +=  "," + user.getUserName();
					}
					continue;
				}
			}
		//break; // 不支持多个文件导入？
		}
		return userNames;
	}

	@Override
	public List<Map<String, Object>> getReviewRecordList(String userId, int page, int rows) {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT  ");
		sb.append("   r.`result_id` resultId,");
		sb.append("   c.`title` title,");
		sb.append("   DATE_FORMAT(r.`create_time`,'%Y-%m-%e %H:%i:%S') createTime,");
		sb.append("   r.`review_result` resultExplain,");
		sb.append("   r.`grade_total` totalGrade ");
		sb.append(" FROM review_result r,review_class c   ");
		sb.append(" WHERE r.`class_id` = c.`class_id`");
		sb.append("   AND r.`user_id` = ? ");
		sb.append(" ORDER BY r.`create_time` DESC");
		return this.findForJdbcParam(sb.toString(), page, rows, new Object[]{userId});
	}

	@Override
	public Long getReviewRecordCount(String userId) {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT  ");
		sb.append("   COUNT(r.`result_id`)");
		sb.append(" FROM review_result r,review_class c   ");
		sb.append(" WHERE r.`class_id` = c.`class_id`");
		sb.append("   AND r.`user_id` = ? ");
		return this.getCountForJdbcParam(sb.toString(), new Object[]{userId});
	}

	@Override
	public void delReviewRecord(String resultId) {
		String sql = "delete from review_report_result where result_id=?";
		this.executeSql(sql, new Object[]{resultId});
		this.deleteEntityById(ReviewResultEntity.class, resultId);
	}
}
