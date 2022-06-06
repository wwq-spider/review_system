package com.review.manage.expert.service.impl;
import cn.hutool.core.date.DateUtil;
import com.review.common.CommonUtils;
import com.review.common.Constants;
import com.review.front.vo.ConsultationVO;
import com.review.manage.expert.entity.ReviewExpertCalendarEntity;
import com.review.manage.expert.entity.ReviewExpertEntity;
import com.review.manage.expert.entity.ReviewExpertReserveEntity;
import com.review.manage.expert.service.ReviewExpertServiceI;
import com.review.manage.expert.vo.ReviewExpertCalendarVO;
import com.review.manage.expert.vo.ReviewExpertVO;
import org.apache.commons.collections.CollectionUtils;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("reviewExpertService")
@Transactional
public class ReviewExpertServiceImpl extends CommonServiceImpl implements ReviewExpertServiceI {
    @Override
    public void addExpert(ReviewExpertVO reviewExpertVO) {
        ReviewExpertEntity reviewExpertEntity = new ReviewExpertEntity();
        try {
            MyBeanUtils.copyBeanNotNull2Bean(reviewExpertVO, reviewExpertEntity);
        } catch (Exception e) {
            throw new RuntimeException("addExpert error,", e);
        }
        reviewExpertEntity.setCreateTime(new Date());
        reviewExpertEntity.setUpdateTime(reviewExpertEntity.getCreateTime());
        //设置头像
        reviewExpertEntity.setAvatar(CommonUtils.saveCoverImg(reviewExpertVO.getContentImg(), Constants.ReviewExpertDir));

        this.save(reviewExpertEntity);
    }

    @Override
    public void updateExpert(ReviewExpertVO reviewExpertVO) {
        if (reviewExpertVO.getId() == null || reviewExpertVO.getId() == 0) {
            throw new RuntimeException("updateExpert error, id can not null");
        }

        ReviewExpertEntity reviewExpertEntity = this.get(ReviewExpertEntity.class, reviewExpertVO.getId());

        try {
            MyBeanUtils.copyBeanNotNull2Bean(reviewExpertVO, reviewExpertEntity);
        } catch (Exception e) {
            throw new RuntimeException("updateExpert error,", e);
        }

        String path = CommonUtils.saveCoverImg(reviewExpertVO.getContentImg(), Constants.ReviewExpertDir);
        if (StringUtil.isNotEmpty(path)) {
            reviewExpertEntity.setAvatar(path);
        }
        reviewExpertEntity.setUpdateTime(new Date());

        this.saveOrUpdate(reviewExpertEntity);
    }

    @Override
    public List<ReviewExpertVO> getReviewExperts(ReviewExpertVO reviewExpertVO) {
        StringBuilder sql = new StringBuilder("select e.id,\n" +
                "       e.status,\n" +
                "       e.age,\n" +
                "       e.avatar,\n" +
                "       e.sex,\n" +
                "       e.mobile_phone mobilePhone,\n" +
                "       DATE_FORMAT(e.`create_time`, '%Y-%m-%e %H:%i:%S') AS createTime,\n" +
                "       e.introduction,\n" +
                "       e.expert_name  expertName,\n" +
                "       e.job_title    jobTitle,\n" +
                "       e.work_org_name workOrgName,\n" +
                "       e.label\n" +
                "from review_expert e\n" +
                "where e.status = 1");
        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtil.isNotEmpty(reviewExpertVO.getExpertName())) {
            sql.append(" and e.expert_name like ':expertName%'");
            paramMap.put("expertName", reviewExpertVO.getExpertName());

        }
        sql.append(" order by e.sort_num asc, e.update_time desc");
        if (reviewExpertVO.getDataGrid() != null) {
            sql.append(" limit ").append((reviewExpertVO.getDataGrid().getPage() - 1) * reviewExpertVO.getDataGrid().getRows())
                    .append(", ")
                    .append(reviewExpertVO.getDataGrid().getRows());
        }
        return this.getObjectList(sql.toString(), paramMap, ReviewExpertVO.class);
    }

    @Override
    public boolean createOrUpdCalendar(ReviewExpertCalendarVO expertCalendar) {
        //检查时间是否 合理
        boolean flag = checkExpertCalendar(expertCalendar);
        if (!flag) {
            return false;
        }

        if(expertCalendar.getId() != null && expertCalendar.getId() > 0) {
            ReviewExpertEntity reviewExpertEntity = this.get(ReviewExpertEntity.class, expertCalendar.getId());
            if (reviewExpertEntity == null) {
                return false;
            }
            try {
                MyBeanUtils.copyBeanNotNull2Bean(expertCalendar, reviewExpertEntity);
            } catch (Exception e) {
                throw new RuntimeException("createOrUpdCalendar error, ", e);
            }
            reviewExpertEntity.setUpdateTime(new Date());
            this.saveOrUpdate(reviewExpertEntity);
        } else { //新增
            ReviewExpertEntity reviewExpertEntity = new ReviewExpertEntity();
            try {
                MyBeanUtils.copyBeanNotNull2Bean(expertCalendar, reviewExpertEntity);
            } catch (Exception e) {
                throw new RuntimeException("createOrUpdCalendar error, ", e);
            }
            reviewExpertEntity.setCreateTime(new Date());
            this.save(reviewExpertEntity);
        }
        return true;
    }

    private boolean checkExpertCalendar(ReviewExpertCalendarVO expertCalendarVO) {

        if (StringUtil.isEmpty(expertCalendarVO.getBeginTime()) || StringUtil.isEmpty(expertCalendarVO.getEndTime())) {
            return false;
        }
        //开始时间
        Date beginDate = DateUtil.parseDate(expertCalendarVO.getBeginTime());
        //结束时间
        Date endDate = DateUtil.parseDate(expertCalendarVO.getEndTime());

        if (beginDate.after(endDate)) { //开始时间晚于结束时间
            return false;
        }

        if (!DateUtil.isSameDay(beginDate, endDate)) { //不是同一天
            return false;
        }

        //和已有日历做时间diff 看是否有时间交叉
        List<ReviewExpertCalendarVO> calendarList = getReviewExpertCalendars(expertCalendarVO);
        if (CollectionUtils.isEmpty(calendarList)) {
            return true;
        }
        for (ReviewExpertCalendarVO expertCalendar : calendarList) {
            if (expertCalendarVO.getId() != null && expertCalendarVO.getId() == expertCalendar.getId()) { //跳过自己
                continue;
            }

            Date bDate = DateUtil.parseDate(expertCalendar.getBeginTime());
            Date eDate = DateUtil.parseDate(expertCalendar.getEndTime());
            if (DateUtil.isIn(beginDate, bDate, eDate) || DateUtil.isIn(endDate, bDate, eDate)
                    || DateUtil.isIn(bDate, beginDate, endDate)
                    || DateUtil.isIn(eDate, beginDate, endDate)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<ReviewExpertCalendarVO> getReviewExpertCalendars(ReviewExpertCalendarVO reviewExpertCalendar) {
        if (reviewExpertCalendar.getExpertId() == null || reviewExpertCalendar.getExpertId() == 0) {
            return null;
        }
        StringBuilder sql = new StringBuilder("select c.id,\n" +
                "       c.expert_id          expertId,\n" +
                "       c.week_day           weekDay,\n" +
                "       c.status,\n" +
                "       DATE_FORMAT(c.`begin_time`, '%Y-%m-%e') AS visitDate,\n" +
                "       DATE_FORMAT(c.`begin_time`, '%Y-%m-%e %H:%i') AS beginTime,\n" +
                "       DATE_FORMAT(c.`end_time`, '%Y-%m-%e %H:%i') AS endTime,\n" +
                "       DATE_FORMAT(c.`create_time`, '%Y-%m-%e %H:%i:%S') AS createTime\n" +
                "from review_expert_calendar c\n" +
                "where c.expert_id = :expertId and c.status=1 order by c.`begin_time` asc");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("expertId", reviewExpertCalendar.getExpertId());
        return this.getObjectList(sql.toString(), paramMap, ReviewExpertCalendarVO.class);
    }

    /**
     * 保存日历
     * @param allTime
     * @param expertCalendar
     */
    @Override
    public boolean datahandle(String id,String allTime, ReviewExpertCalendarVO expertCalendar) {
        //对时间进行处理
        System.out.println(allTime);
        String[] allTimeSplitTemp = allTime.split("-");
        String allTimeSplit =null;
        String[] timeSplit = null;
        int week_day = 0;
        String beginTime = "";
        String endTime = "";
        Time beginTimeMysql = null;
        Time endTimeMysql = null;
        Timestamp timestamp = new Timestamp(new Date().getTime());
        DateFormat dateFormat = new SimpleDateFormat("\"hh:mm:ss\"");
        if (!"".equals(allTimeSplitTemp[0])){
            for (int i = 0; i < allTimeSplitTemp.length; i++) {
                ReviewExpertCalendarEntity reviewExpertEntity = new ReviewExpertCalendarEntity();
                allTimeSplit = allTimeSplitTemp[i];
                timeSplit = allTimeSplit.split(",");
                week_day = Integer.parseInt(timeSplit[0]);
                beginTime = timeSplit[1]+":00";
                endTime = timeSplit[2]+":00";
                beginTimeMysql = Time.valueOf(beginTime);
                endTimeMysql = Time.valueOf(endTime);
                //保存
                reviewExpertEntity.setExpertId(Long.valueOf(id));
                reviewExpertEntity.setWeekDay(week_day);
                reviewExpertEntity.setBeginTime(beginTimeMysql);
                reviewExpertEntity.setEndTime(endTimeMysql);
                reviewExpertEntity.setStatus(1);
                reviewExpertEntity.setCreateTime(new Date());
                this.delete(reviewExpertEntity);
                this.save(reviewExpertEntity);
            }
        }
        return true;
    }

    /**
     * 处理专家日历-周几&开始时间&结束时间
     * @param reviewExpertCalendarList
     */
    @Override
    public void handleCalendarTime(List<ReviewExpertCalendarVO> reviewExpertCalendarList) {
        String beginTime = "";
        String endTime = "";
        for (ReviewExpertCalendarVO reviewExpertCalendarVO : reviewExpertCalendarList){
            //处理开始时间和结束时间
            beginTime = reviewExpertCalendarVO.getBeginTime();
            String[] beginTimeArray = beginTime.split(" ");
            beginTime = beginTimeArray[1];
            endTime = reviewExpertCalendarVO.getEndTime();
            String[] endTimeArray = endTime.split(" ");
            endTime = endTimeArray[1];
            reviewExpertCalendarVO.setBeginTime(beginTime);
            reviewExpertCalendarVO.setEndTime(endTime);
            //处理周几
            Integer weekDay = reviewExpertCalendarVO.getWeekDay();
            if (weekDay == 1){
                reviewExpertCalendarVO.setWeekDayName("周一");
            }else if (weekDay == 2){
                reviewExpertCalendarVO.setWeekDayName("周二");
            }else if (weekDay == 3){
                reviewExpertCalendarVO.setWeekDayName("周三");
            }else if (weekDay == 4){
                reviewExpertCalendarVO.setWeekDayName("周四");
            }else if (weekDay == 5){
                reviewExpertCalendarVO.setWeekDayName("周五");
            }else if (weekDay == 6){
                reviewExpertCalendarVO.setWeekDayName("周六");
            }else if (weekDay == 7){
                reviewExpertCalendarVO.setWeekDayName("周日");
            }
        }
    }

    /**
     * 预约专家
     * @param Id
     */
    @Override
    public void orderExpert(Long Id) {
        String sql = "update review_expert_calendar set status=2 where id = ?";
        executeSql(sql,new Object[]{Id});
    }

    /**
     * 保存预约信息
     * @param reviewExpertReserveEntity
     */
    @Override
    public void saveOoderInfo(ReviewExpertReserveEntity[] reviewExpertReserveEntity) {
        ReviewExpertReserveEntity reserveEntity = new ReviewExpertReserveEntity();
        reserveEntity.setExpertId(reviewExpertReserveEntity[0].getExpertId());
        reserveEntity.setUserId(reviewExpertReserveEntity[0].getUserId());
        reserveEntity.setCalendarId(reviewExpertReserveEntity[0].getCalendarId());
        reserveEntity.setStatus(reviewExpertReserveEntity[0].getStatus());
        reserveEntity.setType(reviewExpertReserveEntity[0].getType());
        reserveEntity.setPatientName(reviewExpertReserveEntity[0].getPatientName());
        reserveEntity.setPatientSex(reviewExpertReserveEntity[0].getPatientSex());
        reserveEntity.setPatientAge(reviewExpertReserveEntity[0].getPatientAge());
        reserveEntity.setDelFlag(1);
        reserveEntity.setCreateTime(new Date());
        this.save(reserveEntity);
    }

    /**
     * 我的问诊列表
     * @param consultationVO
     * @return
     */
    @Override
    public List<ConsultationVO> getMyConsultation(ConsultationVO consultationVO) {
        if (consultationVO.getUserId() == null) {
            return null;
        }
        StringBuilder sql = new StringBuilder(
            "select rer.id,\n" +
                    "rec.id calendarId,\n"+
                    "re.expert_name expertName,\n"+
                    "re.sex expertSex,\n"+
                    "re.job_title expertJobTitle,\n"+
                    "re.introduction expertIntroduction,\n"+
                    "re.label expertLable,\n"+
                    "rer.patient_name patientName,\n"+
                    "rer.patient_sex patientSex,\n"+
                    "rer.patient_age patientAge,\n"+
                    "ru.mobile_phone userPhone,\n"+
                    "ru.id_card userIdCard,\n"+
                    "rec.begin_time beginTime,\n"+
                    "rec.end_time endTime,\n"+
                    "rec.week_day weekDay,\n"+
                    "rer.status status,\n"+
                    "case rer.status\n"+
                    "when 1 then '待问诊' when 2 then '问诊结束' when 3 then '已取消'\n"+
                    "end statusName,\n"+
                    "case rec.week_day\n"+
                    "when 1 then '周一' when 2 then '周二' when 3 then '周三' when 4 then '周四' when 5 then '周五' when 6 then '周六' when 7 then '周日'\n"+
                    "end weekDayName\n"+
                    "from review_expert_reserve rer \n"+
                    "left join review_expert_calendar rec ON rer.calendar_id = rec.id\n"+
                    "left join review_expert re ON rer.expert_id = re.id\n"+
                    "left join review_user ru ON rer.user_id = ru.user_id\n"+
                    "where rer.user_id = :userId and rer.del_flag = 1 group by rer.id order by rer.status"
            );
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", consultationVO.getUserId());
        List<ConsultationVO> list = null;
        list = this.getObjectList(sql.toString(), paramMap, ConsultationVO.class);
        return this.beginAndEndTimehandle(list);
    }

    /**
     * 我的问诊详情
     * @param consultationVO
     * @return
     */
    @Override
    public List<ConsultationVO> getMyConsultationDetail(ConsultationVO consultationVO) {
        if (consultationVO.getId() == null) {
            return null;
        }
        StringBuilder sql = new StringBuilder(
                "select rer.id,\n" +
                        "rec.id calendarId,\n"+
                        "re.expert_name expertName,\n"+
                        "re.sex expertSex,\n"+
                        "re.job_title expertJobTitle,\n"+
                        "re.introduction expertIntroduction,\n"+
                        "re.label expertLable,\n"+
                        "rer.patient_name patientName,\n"+
                        "rer.patient_sex patientSex,\n"+
                        "rer.patient_age patientAge,\n"+
                        "ru.mobile_phone userPhone,\n"+
                        "ru.id_card userIdCard,\n"+
                        "rec.begin_time beginTime,\n"+
                        "rec.end_time endTime,\n"+
                        "rec.week_day weekDay,\n"+
                        "rer.status status,\n"+
                        "case rer.status\n"+
                        "when 1 then '待问诊' when 2 then '问诊结束' when 3 then '已取消'\n"+
                        "end statusName,\n"+
                        "case rec.week_day\n"+
                        "when 1 then '周一' when 2 then '周二' when 3 then '周三' when 4 then '周四' when 5 then '周五' when 6 then '周六' when 7 then '周日'\n"+
                        "end weekDayName\n"+
                        "from review_expert_reserve rer \n"+
                        "left join review_expert_calendar rec ON rer.calendar_id = rec.id\n"+
                        "left join review_expert re ON rer.expert_id = re.id\n"+
                        "left join review_user ru ON rer.user_id = ru.user_id\n"+
                        "where rer.id = :id and rer.del_flag = 1 group by rer.id order by rer.create_time desc"
        );
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", consultationVO.getId());
        List<ConsultationVO> list = null;
        list = this.getObjectList(sql.toString(), paramMap, ConsultationVO.class);
        return this.beginAndEndTimehandle(list);
    }

    /**
     * 取消预约-将预约人信息status置为3：取消预约
     * @param id
     */
    @Override
    public void cancelReservation(Integer id) {
        String sql = "update review_expert_reserve set status=3 where id = ?";
        executeSql(sql,new Object[]{id});
    }

    /**
     * 取消预约-将专家日历状态status置为1：可预约
     * @param calendarId
     */
    @Override
    public void updateCalendarStatus(Integer calendarId) {
        String sql = "update review_expert_calendar set status=1 where id = ?";
        executeSql(sql,new Object[]{calendarId});
    }

    /**
     * 时间处理
     * @param list
     */
    private List<ConsultationVO> beginAndEndTimehandle(List<ConsultationVO> list) {
        String beginTime = "";
        String endTime = "";
        for (int i = 0; i < list.size(); i++) {
            //处理就诊开始时间和结束时间
            beginTime = list.get(i).getBeginTime();
            endTime = list.get(i).getEndTime();
            beginTime = beginTime.split(":")[0]+":"+beginTime.split(":")[1];
            endTime = endTime.split(":")[0]+":"+endTime.split(":")[1];
            list.get(i).setBeginTime(beginTime);
            list.get(i).setEndTime(endTime);
        }
        return list;
    }
}