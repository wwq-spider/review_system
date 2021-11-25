package com.review.manage.expert.service.impl;
import cn.hutool.core.date.DateUtil;
import com.review.common.CommonUtils;
import com.review.common.Constants;
import com.review.common.DateUtils;
import com.review.manage.expert.entity.ReviewExpertEntity;
import com.review.manage.expert.service.ReviewExpertServiceI;
import com.review.manage.expert.vo.ReviewExpertCalendarVO;
import com.review.manage.expert.vo.ReviewExpertVO;
import org.apache.commons.collections.CollectionUtils;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}