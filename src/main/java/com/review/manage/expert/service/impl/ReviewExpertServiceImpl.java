package com.review.manage.expert.service.impl;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.review.common.CommonUtils;
import com.review.common.Constants;
import com.review.front.vo.ConsultationVO;
import com.review.manage.expert.entity.ReviewExpertCalendarEntity;
import com.review.manage.expert.entity.ReviewExpertEntity;
import com.review.manage.expert.entity.ReviewExpertReserveEntity;
import com.review.manage.expert.service.ReviewExpertServiceI;
import com.review.manage.expert.vo.ReviewExpertCalendarVO;
import com.review.manage.expert.vo.ReviewExpertVO;
import com.review.manage.order.vo.ReviewOrderVO;
import org.apache.commons.collections.CollectionUtils;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("reviewExpertService")
@Transactional
public class ReviewExpertServiceImpl extends CommonServiceImpl implements ReviewExpertServiceI {

    private static final Integer dValue = 300000;

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
                "       CASE c.week_day WHEN '1' THEN '周一' " +
                "       WHEN '2' THEN '周二'\n" +
                "       WHEN '3' THEN '周三'\n" +
                "       WHEN '4' THEN '周四'\n" +
                "       WHEN '5' THEN '周五'\n" +
                "       WHEN '6' THEN '周六'\n" +
                "       WHEN '0' THEN '周日'\n" +
                "       END weekDayName,\n"    +
                "       c.status,\n" +
                "       c.`visit_date` AS visitDate,\n" +
                "       DATE_FORMAT(c.`begin_time`, '%H:%i') AS beginTime,\n" +
                "       DATE_FORMAT(c.`end_time`, '%H:%i') AS endTime,\n" +
                "       DATE_FORMAT(c.`create_time`, '%Y-%m-%e %H:%i:%S') AS createTime\n" +
                "from review_expert_calendar c\n" +
                "where c.expert_id = :expertId and c.status=1 and c.visit_date >= DATE_FORMAT(now(), '%Y-%m-%e') order by c.visit_date, c.`begin_time` asc");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("expertId", reviewExpertCalendar.getExpertId());
        return this.getObjectList(sql.toString(), paramMap, ReviewExpertCalendarVO.class);
    }

    /**
     * 处理专家日历-小于当前时间小时的日历剔除
     * @param reviewExpertCalendarList
     */
    @Override
    public void handleCalendarTime(List<ReviewExpertCalendarVO> reviewExpertCalendarList) {
        GregorianCalendar calendar = new GregorianCalendar();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < reviewExpertCalendarList.size(); i++) {
            if (reviewExpertCalendarList.get(i).getVisitDate().equals(formatter.format(new Date()))){
                int begeinTimeHour = Integer.valueOf(reviewExpertCalendarList.get(i).getBeginTime().split(":")[0]);
                if(begeinTimeHour < hour){
                    reviewExpertCalendarList.remove(i);
                    i--;
                    continue;
                }
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
    public long saveOoderInfo(ReviewExpertReserveEntity[] reviewExpertReserveEntity) {
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
        long id = (long) this.save(reserveEntity);
        return id;
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
            "select DISTINCT rer.id,\n" +
                    "rec.id calendarId,\n"+
                    "re.expert_name expertName,\n"+
                    "re.sex expertSex,\n"+
                    "re.job_title expertJobTitle,\n"+
                    "re.introduction expertIntroduction,\n"+
                    "re.label expertLable,\n"+
                    "re.mobile_phone expertPhone,\n"+
                    "rer.patient_name patientName,\n"+
                    "rer.patient_sex patientSex,\n"+
                    "rer.patient_age patientAge,\n"+
                    "ru.mobile_phone userPhone,\n"+
                    "ru.id_card userIdCard,\n"+
                    "rec.begin_time beginTime,\n"+
                    "rec.end_time endTime,\n"+
                    "rec.week_day weekDay,\n"+
                    "DATE_FORMAT(rec.visit_date, '%Y-%m-%e') AS visitDate,\n" +
                    "rer.status status,\n"+
                    "case rer.status\n"+
                    "when 1 then '待问诊' when 2 then '问诊结束' when 3 then '已取消'\n"+
                    "end statusName,\n"+
                    "case rec.week_day\n"+
                    "when 1 then '周一' when 2 then '周二' when 3 then '周三' when 4 then '周四' when 5 then '周五' when 6 then '周六' when 7 then '周日'\n"+
                    "end weekDayName,\n"+
                    "ro.status payStatus,\n"+
                    "CASE ro.status when 2 then '已支付' when 3 then '已支付' else '待支付' end payStatusName\n"+
                    "from review_expert_reserve rer \n"+
                    "left join review_expert_calendar rec ON rer.calendar_id = rec.id\n"+
                    "left join review_expert re ON rer.expert_id = re.id\n"+
                    "left join review_user ru ON rer.user_id = ru.user_id\n"+
                    "LEFT JOIN review_order ro ON rer.user_id = ro.user_id AND ro.class_id = rer.id\n"+
                    "where rer.user_id = :userId and rer.del_flag = 1 order by rec.visit_date DESC,rec.begin_time"
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
                "select DISTINCT rer.id,\n" +
                        "re.id expertId,\n"+
                        "rec.id calendarId,\n"+
                        "re.expert_name expertName,\n"+
                        "re.sex expertSex,\n"+
                        "re.job_title expertJobTitle,\n"+
                        "re.introduction expertIntroduction,\n"+
                        "re.label expertLable,\n"+
                        "re.mobile_phone expertPhone,\n"+
                        "rer.patient_name patientName,\n"+
                        "rer.patient_sex patientSex,\n"+
                        "rer.patient_age patientAge,\n"+
                        "ru.mobile_phone userPhone,\n"+
                        "ru.id_card userIdCard,\n"+
                        "ru.user_id userId,\n"+
                        "rec.begin_time beginTime,\n"+
                        "rec.end_time endTime,\n"+
                        "rec.week_day weekDay,\n"+
                        "DATE_FORMAT(rec.visit_date, '%Y-%m-%e') AS visitDate,\n" +
                        "rer.status status,\n"+
                        "case rer.status\n"+
                        "when 1 then '待问诊' when 2 then '问诊结束' when 3 then '已取消'\n"+
                        "end statusName,\n"+
                        "case rec.week_day\n"+
                        "when 1 then '周一' when 2 then '周二' when 3 then '周三' when 4 then '周四' when 5 then '周五' when 6 then '周六' when 7 then '周日'\n"+
                        "end weekDayName,\n"+
                        "re.charge charge,\n"+
                        "re.org_price orgPrice,\n"+
                        "re.dicount_price dicountPrice,\n"+
                        "(re.org_price - re.dicount_price) as realPrice\n"+
                        "from review_expert_reserve rer \n"+
                        "left join review_expert_calendar rec ON rer.calendar_id = rec.id\n"+
                        "left join review_expert re ON rer.expert_id = re.id\n"+
                        "left join review_user ru ON rer.user_id = ru.user_id\n"+
                        "where rer.id = :id and rer.del_flag = 1 order by rer.create_time desc"
        );
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", consultationVO.getId());
        List<ConsultationVO> list = null;
        list = this.getObjectList(sql.toString(), paramMap, ConsultationVO.class);

        String userId = ContextHolderUtils.getLoginFrontUserID();
        if (StrUtil.isNotBlank(list.get(0).getUserId()) && list.get(0).getCharge() == Constants.ClassCharge) {
            //判断用户是否支付了问诊费用
            list.get(0).setBuy(this.userBuy(list.get(0).getId().toString(), userId));
        }
        String consultaTime = list.get(0).getVisitDate()+ " " + list.get(0).getBeginTime();
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        try {
            long consultaTimeL = Long.valueOf(com.review.common.DateUtil.dateToStamp(consultaTime));
            long currentTimeL = Long.valueOf(com.review.common.DateUtil.dateToStamp(currentTime));
            if (consultaTimeL < currentTimeL){//判断该预约是否支付（过期不能支付）
                list.get(0).setBuy(true);
            }
        }catch (ParseException e){
            e.printStackTrace();
        }

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
     * 保存专家日历
     * @param expertCalendar
     * @return
     */
    @Override
    public boolean saveCalendarInfo(ReviewExpertCalendarVO expertCalendar) {
        String beginTime = "";
        String endTime = "";
        beginTime = expertCalendar.getBeginTime().trim() + ":00";
        endTime = expertCalendar.getEndTime().trim() + ":00";
        ReviewExpertCalendarEntity reviewExpertEntity = new ReviewExpertCalendarEntity();
        //保存
        try {
            reviewExpertEntity.setExpertId(expertCalendar.getExpertId());
            reviewExpertEntity.setVisitDate(new SimpleDateFormat("yyyy-MM-dd").parse(expertCalendar.getVisitDate()));
            reviewExpertEntity.setBeginTime(Time.valueOf(beginTime));
            reviewExpertEntity.setEndTime(Time.valueOf(endTime));
            reviewExpertEntity.setStatus(1);
            reviewExpertEntity.setCreateTime(new Date());
            //获取周几对应的日期
            int weekDay = getDay(new SimpleDateFormat("yyyy-MM-dd").parse(expertCalendar.getVisitDate()));
            reviewExpertEntity.setWeekDay(weekDay);
            String completebeginTime = expertCalendar.getVisitDate() + " " + beginTime;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date completebeginDate = format.parse(completebeginTime);
            Date currentDate = format.parse(format.format(new Date()));
            //校验该条日历是否存在，存在：不保存
            List isExit = checkThisCalendarIsExit(reviewExpertEntity);
            if (isExit.size() == 0){
                if (completebeginDate.compareTo(currentDate)>=0){
                    //this.delete(reviewExpertEntity);
                    this.save(reviewExpertEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private List<ReviewExpertCalendarEntity> checkThisCalendarIsExit(ReviewExpertCalendarEntity reviewExpertEntity) {
        StringBuilder sql = new StringBuilder("select c.id\n" +
                "from review_expert_calendar c\n" +
                "where c.expert_id = :expertId and c.begin_time = :beginTime and end_time =:endTime and visit_date =:visitDate");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("expertId", reviewExpertEntity.getExpertId());
        paramMap.put("beginTime", reviewExpertEntity.getBeginTime());
        paramMap.put("endTime", reviewExpertEntity.getEndTime());
        paramMap.put("visitDate", reviewExpertEntity.getVisitDate());
        return this.getObjectList(sql.toString(), paramMap, ReviewExpertCalendarEntity.class);
    }

    @Override
    public String videoConsultcondition(List<ConsultationVO> reviewExpertReserveList) throws ParseException {
        //判断当前时间是否可以发起视频咨询
        String completeTime = reviewExpertReserveList.get(0).getVisitDate() + " " + reviewExpertReserveList.get(0).getBeginTime()+":00";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = simpleDateFormat.format(new Date());
        long complete = Long.valueOf(com.review.common.DateUtil.dateToStamp(completeTime));
        long current = Long.valueOf(com.review.common.DateUtil.dateToStamp(currentTime));
        if (Math.abs(complete-current) <= dValue){
            return "Y";
        }
        return "N";
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

    public boolean userBuy(String classId, String userId) {
        ReviewOrderVO reviewOrder = this.findOneOrder(classId, userId);
        if (reviewOrder != null && (reviewOrder.getStatus() == Constants.OrderStatus.PRE_SUCCESS || reviewOrder.getStatus() == Constants.OrderStatus.SUCCESS)) {
            return true;
        }
        return false;
    }


    public ReviewOrderVO findOneOrder(String classId, String userId) {
        Map<String, Object> resMap = this.findOneForJdbc("select id, pay_id payId, status,DATE_FORMAT(create_time, '%Y-%m-%e %H:%i:%S') as createTime " +
                        "from review_order where class_id=? and user_id=? and status in(?,?,?,?,?)",
                new Object[]{classId, userId, Constants.OrderStatus.CREATE, Constants.OrderStatus.PRE_PAY, Constants.OrderStatus.PRE_SUCCESS,
                        Constants.OrderStatus.SUCCESS, Constants.OrderStatus.PAY_FAIL});
        if (resMap != null && !resMap.isEmpty()) {
            ReviewOrderVO orderVO = new ReviewOrderVO();
            orderVO.setId((Long)resMap.get("id"));
            orderVO.setPayId(resMap.get("payId").toString());
            orderVO.setStatus((Integer) resMap.get("status"));
            orderVO.setCreateTime((String) resMap.get("createTime"));
            return orderVO;
        }
        return null;
    }

    /**
     * 根据周几获取对应的日期
     * 1 星期日，2星期一，3星期二，4星期三，5星期四，6星期五，7星期六
     * @param date
     * @return
     */
    public static int getDay(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }
}