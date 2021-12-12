package com.review.common.interceptors;
import cn.hutool.core.util.StrUtil;
import com.review.common.CommonUtils;
import com.review.common.Constants;
import com.review.manage.order.service.ReviewOrderServiceI;
import com.review.manage.reviewClass.entity.ReviewClassEntity;
import com.review.manage.reviewClass.service.ReviewClassService;
import com.review.manage.userManage.entity.ReviewUserEntity;
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
 * 支付拦截逻辑
 */
public class ReviewPayInterceptor implements HandlerInterceptor {

    private List<String> includeUrls;

    public List<String> getIncludeUrls() {
        return includeUrls;
    }

    public void setIncludeUrls(List<String> includeUrls) {
        this.includeUrls = includeUrls;
    }
    @Autowired
    private ReviewClassService reviewClassService;

    @Autowired
    private ReviewOrderServiceI reviewOrderServiceI;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        // 用户访问的资源地址
        String requestPath = ResourceUtil.getRequestPath(request);
        //值拦截前端请求
        if (includeUrls.contains(requestPath)) {
            String classId = request.getHeader("classId");
            if (StringUtils.isNotBlank(classId)) {
                ReviewClassEntity reviewClass = reviewClassService.get(ReviewClassEntity.class, classId);
                if (reviewClass == null) {
                    return true;
                }
                if (reviewClass != null && reviewClass.getCharge() == Constants.ClassFree) {
                    return true;
                }
                String userId = getReviewUserId(request);
                //检查用户是否已经购买
                if (StrUtil.isNotBlank(userId) && reviewOrderServiceI.userBuy(classId, userId)) {
                    return true;
                }

                //项目测评权限
                String projectId = request.getHeader("projectId");
                if (StringUtils.isNotBlank(projectId) && !"0".equals(projectId)) {
                    //检查量表中是否包含该项目
                    boolean contains = reviewClassService.projectContainsClass(Long.valueOf(projectId), classId);
                    if (contains) {
                        return true;
                    }
                    return authFailed(response);
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
        json.put("msg", "用户还未购买改量表");
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
        return false;
    }

    private String getReviewUserId(HttpServletRequest request) {
        ReviewUserEntity reviewUser = (ReviewUserEntity)request.getSession().getAttribute(Constants.REVIEW_LOGIN_USER);
        if (reviewUser == null) {
            String userId = request.getHeader("userId");
            if (StringUtils.isNotBlank(userId)) {
                return userId;
            }
        } else {
            return reviewUser.getUserId();
        }
        return null;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
