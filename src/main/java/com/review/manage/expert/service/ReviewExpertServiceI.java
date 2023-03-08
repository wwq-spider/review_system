package com.review.manage.expert.service;

import com.review.front.vo.ConsultationVO;
import com.review.manage.expert.entity.ReviewExpertReserveEntity;
import com.review.manage.expert.vo.ReviewExpertCalendarVO;
import com.review.manage.expert.vo.ReviewExpertVO;
import org.jeecgframework.core.common.service.CommonService;

import java.text.ParseException;
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
     * 处理时间数据
     * @param reviewExpertCalendarList
     */
    void handleCalendarTime(List<ReviewExpertCalendarVO> reviewExpertCalendarList);

    /**
     * 预约专家
     * @param Id
     */
    void orderExpert(Long Id);

    /**
     * 保存预约信息
     * @param reviewExpertReserveEntity
     */
    long saveOoderInfo(ReviewExpertReserveEntity[] reviewExpertReserveEntity);

    /**
     * 我的问诊列表
     * @param consultationVO
     * @return
     */
    List<ConsultationVO> getMyConsultation(ConsultationVO consultationVO);

    /**
     * 我的问诊详情
     * @param consultationVO
     * @return
     */
    List<ConsultationVO> getMyConsultationDetail(ConsultationVO consultationVO);

    /**
     * 取消预约-将预约人信息status置为3：取消预约
     * @param id
     */
    void cancelReservation(Integer id);

    /**
     *  取消预约-将专家日历状态status置为1：可预约
     * @param calendarId
     */
    void updateCalendarStatus(Integer calendarId);

    /**
     * 保存专家日历
     * @param expertCalendar
     * @return
     */
    boolean saveCalendarInfo(ReviewExpertCalendarVO expertCalendar);

    /**
     * 判断当前时间是否可以发起视频咨询
     * @param reviewExpertReserveList
     * @return
     */
    String videoConsultcondition(List<ConsultationVO> reviewExpertReserveList) throws ParseException;

    List<ConsultationVO> getMyAppointList(String expertPhone);

    void confirmAppoint(Integer id);

    String isConfirmByExpert(ConsultationVO consultationVO);

    boolean isExpert(String mobilePhone);
}
