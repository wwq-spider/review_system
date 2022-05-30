package com.review.manage.expert.service;

import com.review.manage.expert.vo.ReviewExpertCalendarVO;
import com.review.manage.expert.vo.ReviewExpertVO;
import org.jeecgframework.core.common.service.CommonService;

import java.util.List;

public interface ReviewExpertServiceI extends CommonService{

    /**
     * 新增专家
     * @param reviewExpertVO
     */
    void addExpert(ReviewExpertVO reviewExpertVO);

    /**
     * 更细专家
     * @param reviewExpertVO
     */
    void updateExpert(ReviewExpertVO reviewExpertVO);

    /**
     * 查询测评专家
     * @param reviewExpertVO
     * @return
     */
    List<ReviewExpertVO> getReviewExperts(ReviewExpertVO reviewExpertVO);

    /**
     * 新增/修改日历
     * @param expertCalendar
     */
    boolean createOrUpdCalendar(ReviewExpertCalendarVO expertCalendar);

    /**
     * 获取专家日历
     * @param reviewExpertCalendar
     * @return
     */
    List<ReviewExpertCalendarVO> getReviewExpertCalendars(ReviewExpertCalendarVO reviewExpertCalendar);

    /**
     * 保存日历
     * @param allTime
     * @param expertCalendar
     */
    boolean datahandle(String id,String allTime, ReviewExpertCalendarVO expertCalendar);
}
