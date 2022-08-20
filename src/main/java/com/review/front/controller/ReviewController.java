package com.review.front.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.review.common.AliYunSmsUtils;
import com.review.common.CommonUtils;
import com.review.common.Constants;
import com.review.common.WxAppletsUtils;
import com.review.front.entity.ReviewReportResultEntity;
import com.review.front.entity.ReviewResultEntity;
import com.review.front.service.ReviewFrontService;
import com.review.front.vo.ReviewResultVO;
import com.review.manage.order.service.ReviewOrderServiceI;
import com.review.manage.order.vo.ReviewOrderVO;
import com.review.manage.project.entity.ReviewProjectEntity;
import com.review.manage.project.service.IReviewProjectService;
import com.review.manage.project.vo.ReviewProjectVO;
import com.review.manage.question.vo.QuestionVO;
import com.review.manage.report.service.ReportService;
import com.review.manage.reviewClass.entity.ReviewClassEntity;
import com.review.manage.reviewClass.service.ReviewClassService;
import com.review.manage.reviewClass.vo.ReviewClassVO;
import com.review.manage.subject.service.ReviewSubjectServiceI;
import com.review.manage.subject.vo.ReviewSubjectVO;
import com.review.manage.userManage.entity.ReviewUserEntity;
import com.review.manage.userManage.service.ReviewUserService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.IpUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/reviewFront")
public class ReviewController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ReviewFrontService reviewFrontService;
	
	@Autowired
	private ReviewUserService reviewUserService;

	@Autowired
	private ReviewClassService reviewClassService;

	@Autowired
	private ReportService reportService;

	@Autowired
	private ReviewSubjectServiceI reviewSubjectServiceI;

	@Autowired
	private ReviewOrderServiceI reviewOrderService;

	@Autowired
	private IReviewProjectService reviewProjectService;
	
	/**
	 * 跳到登录页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="toReviewLogin")
	public ModelAndView toReviewLogin(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView("review/front/reviewLogin");
		return model;
	}
	
	/**
	 * 登录
	 * @param request
	 * @param response
	 */
	@RequestMapping(params="login")
	public void login(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		ReviewUserEntity reviewUser = reviewUserService.getUserByUserName(userName);
		String result = "";
		if(reviewUser == null) {
			result = "0";
		} else {
			if(password.equals(reviewUser.getPassword())) {
				result = "1";
				Client client = new Client();
				client.setIp(IpUtil.getIpAddr(request));
				client.setReviewUser(reviewUser);
				ClientManager.getInstance().addClinet(session.getId(), client);
				session.setAttribute(Constants.REVIEW_LOGIN_USER, reviewUser);
				request.getSession().setAttribute("reviewUser", reviewUser);
			} else {
				result = "0";
			}
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);
		CommonUtils.responseDatagrid(response, jsonObject);
	}
	
	/**
	 * 跳到输入年龄页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="toAgeAndSex")
	public ModelAndView toAgeAndSex(HttpServletRequest request, HttpServletResponse response) {
		String idCard = request.getParameter("idCard");
		request.setAttribute("idCard", idCard);
		ModelAndView model = new ModelAndView("review/front/agesexpage");
		return model;
	}
	
	/**
	 * 跳到题库选择页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="toQuestionStore")
	public ModelAndView toQuestionStore(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView("review/front/questionStore");
		List<ReviewClassEntity> list = reviewFrontService.findByQueryString("from ReviewClassEntity where status=1 ORDER BY createTime");
		model.addObject("classList", list);
		return model;
	}
	
	/**
	 * 跳到指导语页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="toGuide")
	public ModelAndView toGuide(HttpServletRequest request,
			HttpServletResponse response) {
		String classId = request.getParameter("classId");
		ReviewClassEntity reviewClass = reviewFrontService.get(ReviewClassEntity.class, classId);
		ModelAndView model = new ModelAndView("review/front/guidePage");
		model.addObject("classId", classId);
		model.addObject("reviewClass", reviewClass);
		return model;
	}
	
	/**
	 * 开始测试
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="beginTest")
	public ModelAndView beginTest(HttpServletRequest request,
			HttpServletResponse response) {
		String classId = request.getParameter("classId");
		String questioinNum = request.getParameter("questionNum");
		ModelAndView model = new ModelAndView("review/front/testPage");
		model.addObject("classId", classId);
		model.addObject("questionNum", questioinNum);
		return model;
	}

	/**
	 * 根据分类ID查询分类下的问题及选项
	 * @param response
	 * @param question
	 */
	@RequestMapping(value = "getQuestionsByClassID", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void getQuestionsByClassID(HttpServletResponse response, @RequestBody QuestionVO question) {
		JSONObject json = new JSONObject();
		if (StringUtils.isBlank(question.getClassId())) {
			json.put("code", 300);
			json.put("msg", "classID不能为空");
			CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
		}

		List<QuestionVO> questionVOList = reviewFrontService.getQuestionVOList(question.getClassId());
		for (int i=0; i<questionVOList.size(); i++) {
			QuestionVO questionVO = questionVOList.get(i);
			questionVO.setSelectList(reviewFrontService.getSelectVOList(questionVO.getQuestionId()));
		}
		json.put("code", 200);
		json.put("msg", "查询成功");
		json.put("rows", questionVOList);

		CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
	}

	/**
	 * 初始化题目信息
	 * @param request
	 * @param response
	 * @param question
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params="nextQuestion")
	public ModelAndView nextQuestion(HttpServletRequest request,
			HttpServletResponse response, QuestionVO question) throws Exception {
		String classId = request.getParameter("classId");
		String questionNum = request.getParameter("questionNum");
		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession();
		List<QuestionVO> list = null;
		if(!"".equals(StringUtils.trimToEmpty(questionNum))) {
			if("0".equals(questionNum)) {
				session.removeAttribute("resultList");
				session.setAttribute("resultList", new ArrayList<QuestionVO>());
			} else {
				list = (List<QuestionVO>) session.getAttribute("resultList");
				if(list.size() > 0) {
					if(list.get(list.size() - 1).getQuestionNum() < Integer.parseInt(questionNum)) {
						/*if(question.getVariateId().indexOf(",") > -1) {
							String[] variateIdArr = question.getVariateId().split(",");
							QuestionVO questionVO = null;
							for(int i=0; i<variateIdArr.length; i++) {
								questionVO = new QuestionVO();
								MyBeanUtils.copyBean2Bean(questionVO, question);
								questionVO.setVariateId(variateIdArr[i]);
								list.add(questionVO);

							}
						} else {
							list.add(question);
						}*/
						list.add(question);
					}
				} else {
					list.add(question);
					/*if(question.getVariateId().indexOf(",") > -1) {
						String[] variateIdArr = question.getVariateId().split(",");
						QuestionVO questionVO = null;
						for(int i=0; i<variateIdArr.length; i++) {
							questionVO = new QuestionVO();
							MyBeanUtils.copyBean2Bean(questionVO, question);
							questionVO.setVariateId(variateIdArr[i]);
							list.add(questionVO);

						}
					} else {
						list.add(question);
					}*/
				}
			}
			
			if(!"Y".equals(StringUtils.trimToEmpty(question.getIsLastQuestion()))) {
				QuestionVO questionVO = reviewFrontService.getQuestionDetail(classId, Integer.parseInt(questionNum),1);
				questionVO.setQuestionNum(Integer.parseInt(questionNum));
				model.setViewName("review/front/testPage");
				model.addObject("question", questionVO);
			} else {
				model.setViewName("review/front/testEndPage");
				
				model.addObject("question", question);
			}
		}
		return model;
	}
	
	/**
	 * 完成测试
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params="complete")
	public void commplete(HttpServletRequest request, HttpServletResponse response) {
		String classId = request.getParameter("classId");
		HttpSession session = request.getSession();
		//List<QuestionVO> list = reviewFrontService.getQuestionVOList(classId, 0, 99999999);
		
		List<QuestionVO> resultList = (List<QuestionVO>) session.getAttribute("resultList");
		ReviewUserEntity user = (ReviewUserEntity) session.getAttribute(Constants.REVIEW_LOGIN_USER);
		
		String result = "";
		if(resultList != null) {
			reviewFrontService.completeReview(resultList, classId, user);
			result = "1";
		} else {
			result = "0";
		}
		JSONObject json = new JSONObject();
		json.put("result", result);
		CommonUtils.responseDatagrid(response, json);
	}

	/**
	 * 完成测试
	 * @param request
	 * @param response
	 * @param resultArr
	 */
	@RequestMapping(value = "completeReview", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void completeReview(HttpServletRequest request, HttpServletResponse response, @RequestBody QuestionVO[] resultArr) {
		JSONObject json = new JSONObject();
		if (ArrayUtils.isEmpty(resultArr)) {
			json.put("code", 300);
			json.put("msg", "测试问题结果为空");
			CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
			return;
		}
		ReviewUserEntity user = (ReviewUserEntity) request.getSession().getAttribute(Constants.REVIEW_LOGIN_USER);
		ReviewResultEntity reviewResultEntity = null;
		List<QuestionVO> resultList = Arrays.asList(resultArr);
		if (user == null) {
			reviewResultEntity = reviewFrontService.completeReview(resultList, resultArr[0].getClassId(), new ReviewUserEntity());
		} else {
			reviewResultEntity = reviewFrontService.completeReview(resultList, resultArr[0].getClassId(), user);
		}
		json.put("code", 200);
		reviewResultEntity.setReviewResult(null);
		json.put("result", reviewResultEntity);
		CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
	}
	
	/**
	 * 显示测评结果
	 * @param request
	 * @return
	 */
	@RequestMapping(params="viewResult")
	public ModelAndView viewResult(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("review/front/viewResult");
		ReviewUserEntity user = (ReviewUserEntity) request.getSession().getAttribute(Constants.REVIEW_LOGIN_USER);
		String classId = request.getParameter("classId");
		List<ReviewReportResultEntity> resultList = reviewFrontService.getReportResults(user.getUserId(), classId);
		model.addObject("resultList", resultList);
		return model;
	}

	/**
	 * 获取测评分类
	 * @param response
	 * @param reviewClass
	 */
	@RequestMapping(value = "getReviewClass", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void getReviewClassByProjectId(HttpServletResponse response, @RequestBody ReviewClassVO reviewClass) {
		JSONObject json = new JSONObject();
		Long projectId = reviewClass.getProjectId();
		List<ReviewClassVO> reviewClassList = reviewClassService.getReviewClassByProjectId(projectId);
		json.put("code", 200);
		json.put("rows", reviewClassList);
		json.put("msg", "查询成功");
		CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
	}

	/**
	 * 用户信息是否已经完善
	 * @param response
	 * @param reviewUser
	 */
	@RequestMapping(value = "getUserInfoByOpenid", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void getUserInfoByOpenid(HttpServletResponse response, @RequestBody ReviewUserEntity reviewUser) {
		JSONObject json = new JSONObject();
		json.put("code", 200);
		ReviewUserEntity reviewUserEntity = reviewFrontService.getUserInfo(reviewUser.getOpenid());
		json.put("result", reviewUserEntity);
		CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
	}

	/**
	 * 用户信息注册
	 * @param response
	 * @param reviewUser
	 */
	@RequestMapping(value = "register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void register(HttpServletResponse response, @RequestBody ReviewUserEntity reviewUser) {
		JSONObject json = new JSONObject();
		if (reviewUser == null) {
			json.put("code", 300);
			json.put("msg", "用户信息为空");
			CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
			return;
		}
		try {
			//check 验证码
			String msgCode = (String) ContextHolderUtils.getSession().getAttribute(reviewUser.getMobilePhone() + Constants.MSG_CODE_KEY);
			if (StringUtils.isBlank(msgCode) || !msgCode.equals(reviewUser.getMsgCode())) {
				json.put("code", 301);
				json.put("msg", "短信验证码不正确或已过期");
				CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
				return;
			}

			JSONObject jsonObject = reviewFrontService.register(reviewUser);
			if (jsonObject.getInt("code") == 200) {
				ContextHolderUtils.getSession().removeAttribute(reviewUser.getMobilePhone() + Constants.MSG_CODE_KEY);
				json.put("code", 200);
				json.put("msg", "用户信息注册成功");
				json.put("result", jsonObject.get("userId"));
			} else {
				json.put("code", 301);
				json.put("msg", jsonObject.get("msg"));
			}
		} catch (Exception e) {
			json.put("code", 302);
			json.put("msg", "用户信息注册失败，");
			logger.error("register error, ", e);
		}
		CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
	}

	/**
	 * 设置用户组
	 * @param response
	 * @param reviewUser
	 */
	@RequestMapping(value = "joinUserGroup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void joinUserGroup(HttpServletResponse response, @RequestBody ReviewUserEntity reviewUser) {
		if (reviewUser.getProjectId() == null || reviewUser.getProjectId() == 0 || StringUtils.isBlank(reviewUser.getUserId())) {
			responseJson(response, 300, "项目/用户ID为空");
			return;
		}

		ReviewUserEntity reviewUserEntity = reviewUserService.get(ReviewUserEntity.class, reviewUser.getUserId());
		if (reviewUserEntity == null) {
			responseJson(response, 301, "用户不存在");
			return;
		}

		ReviewProjectEntity reviewProject = reviewProjectService.get(ReviewProjectEntity.class, reviewUser.getProjectId());
		if (reviewProject == null) {
			responseJson(response, 302, "项目不存在");
			return;
		}

		if (reviewUserEntity.getGroupId().indexOf(reviewProject.getGroupId()) > -1) {
			responseJson(response, 200, "该用户已是项目组成员");
			return;
		}

		if (StringUtils.isBlank(reviewProject.getGroupId())) {
			responseJson(response, 303, "项目未绑定用户组");
			return;
		}

		if (reviewProject.getIsOpen() == 1) {
			responseJson(response, 304, "非开放项目，您没有测评权限");
			return;
		}

		if (StringUtils.isBlank(reviewUserEntity.getGroupId())) {
			reviewUserEntity.setGroupId(reviewProject.getGroupId());
			reviewUserService.saveOrUpdate(reviewUserEntity);
		} else if(reviewUserEntity.getGroupId().indexOf(reviewProject.getGroupId()) == -1) {
			reviewUserEntity.setGroupId(reviewUserEntity.getGroupId() + "," + reviewProject.getGroupId());
			reviewUserService.saveOrUpdate(reviewUserEntity);
		}
		responseJson(response, 200, "加入成功");
	}



	/**
	 * 获取我的测评记录/测评报告
	 * @param response
	 * @param reviewUser
	 */
	@RequestMapping(value = "getReviewRecords", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void getReviewRecords(HttpServletResponse response, @RequestBody ReviewUserEntity reviewUser) {
		JSONObject json = new JSONObject();
		if (reviewUser == null || StringUtils.isBlank(reviewUser.getUserId())) {
			json.put("code", 300);
			json.put("msg", "用户信息为空");
			CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
			return;
		}
		List<ReviewResultVO> reviewResultList = reviewFrontService.getReportResults(reviewUser.getUserId(), reviewUser.getProjectId());
		json.put("code", 200);
		json.put("rows", reviewResultList);
		CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
	}

	/**
	 * 获取测评报告
	 * @param response
	 * @param reviewUser
	 */
	@RequestMapping(value = "getReviewReports", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void getReviewReports(HttpServletResponse response, @RequestBody ReviewUserEntity reviewUser) {
		if (reviewUser == null || StringUtils.isBlank(reviewUser.getUserId())) {
			responseJson(response, 300, "用户信息为空");
			return;
		}
		List<ReviewResultVO> reviewResultList = reviewFrontService.getReviewReports(reviewUser.getUserId(), reviewUser.getProjectId());
		responseRowsJson(response, 200, reviewResultList);
	}

	/**
	 * 查询测评分类详情
	 * @param response
	 * @param reviewClass
	 */
	@RequestMapping(value = "getReviewClassDetail", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void getReviewClassDetail(HttpServletResponse response, @RequestBody ReviewClassVO reviewClass) {
		JSONObject json = new JSONObject();
		if (reviewClass == null || StringUtils.isBlank(reviewClass.getClassId())) {
			json.put("code", 300);
			json.put("msg", "分类ID为空");
			CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
			return;
		}
		ReviewClassEntity reviewClassInfo = reviewClassService.get(ReviewClassEntity.class, reviewClass.getClassId());

		if (reviewClassInfo == null) {
			json.put("code", 404);
			json.put("msg", "量表不存在");
			CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
			return;
		}

		if (reviewClassInfo.getStatus() == null || reviewClassInfo.getStatus() != 1) {
			json.put("code", 401);
			json.put("msg", "量表未发布，暂不能测评");
			CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
			return;
		}

		if (reviewClassInfo.getCharge() != null && reviewClassInfo.getCharge() == Constants.ClassCharge) {
			reviewClassInfo.setRealPrice(reviewClassInfo.getOrgPrice().subtract(reviewClassInfo.getDicountPrice()));
		}
		ReviewClassVO reviewClassVO = new ReviewClassVO();
		try {
			MyBeanUtils.copyBeanNotNull2Bean(reviewClassInfo, reviewClassVO);
			String userId = ContextHolderUtils.getLoginFrontUserID();
			if (StrUtil.isNotBlank(userId) && reviewClassInfo.getCharge() == Constants.ClassCharge) {
				//判断用户是都已经购买了课程
				reviewClassVO.setBuy(reviewOrderService.userBuy(reviewClass.getClassId(), ContextHolderUtils.getLoginFrontUserID()));
			}
			json.put("code", 200);
			json.put("result", reviewClassVO);
		} catch (Exception e) {
			logger.error("copyBeanNotNull2Bean error, ", e);
			json.put("code", 400);
			json.put("msg", "查询失败");
		}
		CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
	}

	/**
	 * 查询测评项目详情
	 * @param response
	 * @param reviewProject
	 */
	@RequestMapping(value = "getReviewProjectDetail", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void getReviewProjectDetail(HttpServletResponse response, @RequestBody ReviewProjectVO reviewProject) {
		JSONObject json = new JSONObject();
		if (reviewProject == null || reviewProject.getId() == null || reviewProject.getId() == 0) {
			json.put("code", 300);
			json.put("msg", "项目ID为空");
			CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
			return;
		}
		ReviewProjectEntity reviewProjectEntity = reviewProjectService.get(ReviewProjectEntity.class, reviewProject.getId());
		json.put("code", 200);
		json.put("result", reviewProjectEntity);
		CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
	}

	/**
	 * 查询报告详情
	 * @param response
	 * @param reviewResult
	 */
	@RequestMapping(value = "getReviewReportDetail", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void getReviewReportDetail(HttpServletResponse response, @RequestBody ReviewResultVO reviewResult) {
		JSONObject json = new JSONObject();
		if (reviewResult == null || StringUtils.isBlank(reviewResult.getResultId())) {
			json.put("code", 300);
			json.put("msg", "报告id为空");
			CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
			return;
		}
		ReviewResultEntity result = reportService.get(ReviewResultEntity.class, reviewResult.getResultId());
		json.put("code", 200);
		json.put("result", result);
		CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
	}

	/**
	 * 获取openid
	 * @param response
	 * @param paramJson
	 */
	@RequestMapping(value = "getOpenid", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void getOpenid(HttpServletResponse response, @RequestBody JSONObject paramJson) {
		JSONObject json = new JSONObject();
		if (paramJson == null || paramJson.isEmpty() || !paramJson.containsKey("code")) {
			json.put("code", 300);
			json.put("msg", "code为空");
			CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
			return;
		}

		String code = paramJson.getString("code");
		String openid = WxAppletsUtils.getOpenid(code);
		if (openid == null) {
			json.put("code", 301);
			json.put("msg", "openid获取失败");
		} else {
			json.put("code", 200);
			json.put("msg", "openid获取成功");
			json.put("result", openid);
		}
		CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
	}

	/**
	 * 获取测评专题分类
	 * @param response
	 * @param reviewSubject
	 */
	@RequestMapping(value = "getReviewSubjectClass", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void getReviewSubjectClass(HttpServletResponse response, @RequestBody ReviewSubjectVO reviewSubject) {
		JSONObject json = new JSONObject();
		List<ReviewSubjectVO> reviewSubjectList = reviewSubjectServiceI.getReviewSubjectClass(reviewSubject);
		json.put("code", 200);
		json.put("rows", reviewSubjectList);
		json.put("msg", "查询成功");
		CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
	}

	/**
	 * 发送验证码
	 * @param response
	 * @param reviewUser
	 */
	@RequestMapping(value = "sendMsg", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void sendMsg(HttpServletResponse response, @RequestBody ReviewUserEntity reviewUser) {
		JSONObject json = new JSONObject();
		if (StrUtil.isBlank(reviewUser.getMobilePhone())) {
			json.put("code", 300);
			json.put("msg", "手机号不能为空");
		} else {
			String code = RandomUtil.randomNumbers(4);
			try {
				SendSmsResponseBody body = AliYunSmsUtils.sendMsg(code, reviewUser.getMobilePhone());
				if (body != null && "ok".equalsIgnoreCase(body.getCode())) {
					ContextHolderUtils.getSession().setAttribute(reviewUser.getMobilePhone() + Constants.MSG_CODE_KEY, code);
					json.put("code", 200);
					json.put("msg", "验证码发送成功");
				} else {
					json.put("code", 400);
					json.put("msg", "验证码发送失败");
				}
			} catch (Exception e) {
				logger.error("sendMsg error, ", e);
				json.put("code", 500);
				json.put("msg", "验证码发送异常，请联系管理员");
			}
		}
		CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
	}
}