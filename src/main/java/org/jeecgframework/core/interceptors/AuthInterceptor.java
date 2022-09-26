package org.jeecgframework.core.interceptors;

import com.review.common.CommonUtils;
import com.review.common.Constants;
import com.review.manage.userManage.entity.ReviewUserEntity;
import com.review.manage.userManage.service.ReviewUserService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.Client;
import org.jeecgframework.web.system.pojo.base.TSFunction;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * 权限拦截器
 *
 * @author  张代浩
 *
 */
public class AuthInterceptor implements HandlerInterceptor {

	private static final Logger logger = Logger.getLogger(AuthInterceptor.class);
	private SystemService systemService;
	private List<String> excludeUrls;
	private static List<TSFunction> functionList;

	@Autowired
	private ReviewUserService userService;

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	public SystemService getSystemService() {
		return systemService;
	}

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	/**
	 * 在controller后拦截
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {

	}

	private boolean frontNoLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (isAjaxRequest(request)) { //aja请求返回json
			JSONObject json = new JSONObject();
			json.put("code", 401);
			json.put("msg", "用户未登录");
			CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
		} else {
			response.sendRedirect("reviewFront.do?toReviewLogin");
		}
		return false;
	}

	/**
	 * 在controller前拦截
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		String requestPath = ResourceUtil.getRequestPath(request);// 用户访问的资源地址
		HttpSession session = ContextHolderUtils.getSession();

		if (requestPath.startsWith("css/") || requestPath.startsWith("images/") || requestPath.startsWith("plug-in/") || requestPath.startsWith("upload/")) {
			return true;
		}
		Client client = ClientManager.getInstance().getClient(session.getId());
		if(client == null){
			client = ClientManager.getInstance().getClient(
					request.getParameter("sessionId"));
		}
		if (excludeUrls.contains(requestPath)) {
			return true;
		} else {
			if(requestPath.indexOf("reviewFront.do") > -1 || requestPath.indexOf("reviewFront/") > -1) { //前端拦截
				if(client != null && client.getReviewUser() != null
						&& session.getAttribute(Constants.REVIEW_LOGIN_USER) != null) {
					return true;
				} else {  //登录失效跳到登陆页面
					String userId = request.getHeader("userId");
					if (StringUtils.isBlank(userId)) {
						return frontNoLogin(request, response);
					}
					ReviewUserEntity reviewUser = userService.get(ReviewUserEntity.class, userId);
					if (reviewUser != null) {
						session.setAttribute(Constants.REVIEW_LOGIN_USER, reviewUser);
						return true;
					} else {
						return frontNoLogin(request, response);
					}
				}
			} else { //后台拦截
				if (client != null && client.getUser()!=null ) {
					if(!hasMenuAuth(request)){
						response.sendRedirect("loginController.do?noAuth");
						//request.getRequestDispatcher("webpage/common/noAuth.jsp").forward(request, response);
						return false;
					}
					String functionId=oConvertUtils.getString(request.getParameter("clickFunctionId"));
					if(!oConvertUtils.isEmpty(functionId)){
						Set<String> operationCodes = systemService.getOperationCodesByUserIdAndFunctionId(client.getUser().getId(), functionId);
						request.setAttribute("operationCodes", operationCodes);

					}
					if(!oConvertUtils.isEmpty(functionId)){
						List<String> allOperation=this.systemService.findListbySql("SELECT operationcode FROM t_s_operation  WHERE functionid='"+functionId+"'");

						List<String> newall = new ArrayList<String>();
						if(allOperation.size()>0){
							for(String s:allOperation){
								s=s.replaceAll(" ", "");
								newall.add(s);
							}
							String hasOperSql="SELECT operation FROM t_s_role_function fun, t_s_role_user role WHERE  " +
									"fun.functionid='"+functionId+"' AND fun.operation!=''  AND fun.roleid=role.roleid AND role.userid='"+client.getUser().getId()+"' ";
							List<String> hasOperList = this.systemService.findListbySql(hasOperSql);
							for(String strs:hasOperList){
								for(String s:strs.split(",")){
									s=s.replaceAll(" ", "");
									newall.remove(s);
								}
							}
						}
						request.setAttribute("noauto_operationCodes", newall);
					}
					return true;
				} else {
					forward(request, response);
					return false;
				}
			}
		}
	}
	private boolean hasMenuAuth(HttpServletRequest request){
		String requestPath = ResourceUtil.getRequestPath(request);// 用户访问的资源地址
		// 是否是功能表中管理的url
		boolean bMgrUrl = false;
		if (functionList == null) {
			functionList = systemService.loadAll(TSFunction.class);
		}
		for (TSFunction function : functionList) {
			if (function.getFunctionUrl() != null && function.getFunctionUrl().startsWith(requestPath)) {
				bMgrUrl = true;
				break;
			}
		}
		if (!bMgrUrl) {
			return true;
		}

		String funcid=oConvertUtils.getString(request.getParameter("clickFunctionId"));
		if(!bMgrUrl && (requestPath.indexOf("loginController.do")!=-1||funcid.length()==0)){
			return true;
		}
		String userid = ClientManager.getInstance().getClient(
				ContextHolderUtils.getSession().getId()).getUser().getId();
		//requestPath=requestPath.substring(0, requestPath.indexOf("?")+1);
		String sql = "SELECT DISTINCT f.id FROM t_s_function f,t_s_role_function  rf,t_s_role_user ru " +
				" WHERE f.id=rf.functionid AND rf.roleid=ru.roleid AND " +
				"ru.userid='"+userid+"' AND f.functionurl like '"+requestPath+"%'";
		List list = this.systemService.findListbySql(sql);
		if(list.size()==0){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 转发
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "forword")
	public ModelAndView forword(HttpServletRequest request) {
		return new ModelAndView(new RedirectView("loginController.do?login"));
	}

	private void forward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("webpage/login/timeout.jsp").forward(request, response);
	}

	/**
	 * 是否是Ajax异步请求
	 *
	 * @param request
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String accept = request.getHeader("accept");
		if (accept != null && accept.indexOf("application/json") != -1)
		{
			return true;
		}
		String xRequestedWith = request.getHeader("X-Requested-With");
		if (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1)
		{
			return true;
		}
		String contentType = request.getHeader("content-type");
		if (contentType != null && contentType.indexOf("application/json") > -1) {
			return true;
		}
		return false;
	}
}
