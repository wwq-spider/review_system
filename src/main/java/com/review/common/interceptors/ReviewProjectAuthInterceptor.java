package com.review.common.interceptors;
import com.review.common.CommonUtils;
import com.review.common.Constants;
import com.review.manage.project.entity.ReviewProjectEntity;
import com.review.manage.project.service.IReviewProjectService;
import com.review.manage.userManage.entity.ReviewUserEntity;
import com.review.manage.userManage.service.ReviewUserService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 测评项目权限拦截器
 */
public class ReviewProjectAuthInterceptor implements HandlerInterceptor {

    private List<String> excludeUrls;

    public List<String> getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(List<String> excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    @Autowired
    private ReviewUserService userService;

    @Autowired
    private IReviewProjectService reviewProjectService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        String requestPath = ResourceUtil.getRequestPath(request);// 用户访问的资源地址

        //值拦截前端请求
        if(requestPath.indexOf("reviewFront.do") > -1 || requestPath.indexOf("reviewFront/") > -1) {

            if (excludeUrls.contains(requestPath)) {
                return true;
            }

            String projectId = request.getHeader("projectId");
            if (StringUtils.isNotBlank(projectId) && !"0".equals(projectId)) {
                //获取测评用户
                ReviewUserEntity reviewUser = getReviewUser(request);
                //检查用户项目权限
                if (reviewUser != null && checkProjectAuth(projectId, reviewUser.getGroupId())) {
                    return true;
                }
                return authFailed(response);
            }
        }
        return true;
    }

    /**
     * 用户没有项目权限鉴权失败
     * @param response
     * @return
     */
    private boolean authFailed(HttpServletResponse response) {
        JSONObject json = new JSONObject();
        json.put("code", 401);
        json.put("msg", "用户没有该项目权限");
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
        return false;
    }

    private boolean checkProjectAuth(String projectId, String userGroupId) {
        ReviewProjectEntity reviewProject = reviewProjectService.get(ReviewProjectEntity.class, Long.valueOf(projectId));
        if (reviewProject == null) {
            return false;
        }
        if (userGroupId.indexOf(reviewProject.getGroupId()) > -1 && reviewProject.getIsOpen() == 2) {
            return true;
        }
        return false;
    }

    private ReviewUserEntity getReviewUser(HttpServletRequest request) {
        ReviewUserEntity reviewUser = (ReviewUserEntity)request.getSession().getAttribute(Constants.REVIEW_LOGIN_USER);
        if (reviewUser == null) {
            String userId = request.getHeader("userId");
            if (StringUtils.isNotBlank(userId)) {
                reviewUser = userService.get(ReviewUserEntity.class, userId);
            }
        }
        return reviewUser;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
