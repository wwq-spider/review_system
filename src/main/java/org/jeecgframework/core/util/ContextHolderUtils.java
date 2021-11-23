package org.jeecgframework.core.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.review.common.Constants;
import com.review.manage.userManage.entity.ReviewUserEntity;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.Client;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
/**
* @ClassName: ContextHolderUtils 
* @Description: TODO(上下文工具类) 
* @author  张代浩 
* @date 2012-12-15 下午11:27:39 
*
 */
public class ContextHolderUtils {
	/**
	 * SpringMvc下获取request
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;

	}
	/**
	 * SpringMvc下获取session
	 * 
	 * @return
	 */
	public static HttpSession getSession() {
		HttpSession session = getRequest().getSession();
		return session;

	}

	public static String getLoginUserName() {
		Client client = ClientManager.getInstance().getClient(getSession().getId());
		if (client.getUser() != null) {
			return client.getUser().getUserName();
		}
		return null;
	}

	public static String getLoginFrontUserID() {
		ReviewUserEntity reviewUserEntity = (ReviewUserEntity)getSession().getAttribute(Constants.REVIEW_LOGIN_USER);
		if (reviewUserEntity != null) {
			return reviewUserEntity.getUserId();
		}
		return null;
	}

}
