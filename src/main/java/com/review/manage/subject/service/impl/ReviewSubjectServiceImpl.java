package com.review.manage.subject.service.impl;

import com.review.common.Constants;
import com.review.manage.reviewClass.vo.ReviewClassVO;
import com.review.manage.subject.entity.ReviewSubjectClassEntity;
import com.review.manage.subject.entity.ReviewSubjectEntity;
import com.review.manage.subject.service.ReviewSubjectClassServiceI;
import com.review.manage.subject.service.ReviewSubjectServiceI;
import com.review.manage.subject.vo.ReviewSubjectVO;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("reviewSubjectService")
@Transactional
public class ReviewSubjectServiceImpl extends CommonServiceImpl implements ReviewSubjectServiceI {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SystemService systemService;

    @Autowired
    private ReviewSubjectClassServiceI reviewSubjectClassService;

    @Override
    public String saveOrUpdate(ReviewSubjectVO reviewSubject) {
        String message = "";
        TSUser tsUser = ClientManager.getInstance().getClient(ContextHolderUtils.getSession().getId()).getUser();
        try{
            if (reviewSubject.getId() != null && reviewSubject.getId() > 0){
                ReviewSubjectEntity t = this.get(ReviewSubjectEntity.class, reviewSubject.getId());
                MyBeanUtils.copyBeanNotNull2Bean(reviewSubject, t);
                t.setUpdateTime(new Date());
                t.setOperator(tsUser.getUserName());
                this.saveOrUpdate(t);
                reviewSubjectClassService.saveOrUpdateSubjectClass(reviewSubject.getId(), convertSubjectClass(reviewSubject.getId(), reviewSubject.getClassIds()));
                message = "测评专题修改成功";
            } else {
                ReviewSubjectEntity t = new ReviewSubjectEntity();
                MyBeanUtils.copyBeanNotNull2Bean(reviewSubject, t);
                t.setCreateTime(new Date());
                t.setUpdateTime(t.getCreateTime());
                t.setOperator(tsUser.getUserName());
                this.save(t);
                reviewSubjectClassService.saveOrUpdateSubjectClass(0l, convertSubjectClass(t.getId(), reviewSubject.getClassIds()));
                message = "测评专题新增成功";
            }
        } catch (Exception e) {
            logger.error("saveOrUpdate ReviewSubject error, ", e);
            message = "测评专题修改异常：" + e.getMessage();
        } finally {
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        }
        return message;
    }

    private List<ReviewSubjectClassEntity> convertSubjectClass(Long subjectId, String classIds) {
        if (StringUtils.isBlank(classIds)) {
            return null;
        }

        List<ReviewSubjectClassEntity> list = new ArrayList<>();
        String[] classIdArr = classIds.split(",");
        Date now = new Date();
        for (String classId : classIdArr) {
            ReviewSubjectClassEntity reviewSubjectClass = new ReviewSubjectClassEntity();
            reviewSubjectClass.setClassId(classId);
            reviewSubjectClass.setOperateTime(now);
            reviewSubjectClass.setSubjectId(subjectId);
            list.add(reviewSubjectClass);
        }
        return list;
    }

    @Override
    public List<ReviewSubjectVO> getReviewSubjectClass(ReviewSubjectVO reviewSubject) {

        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT ");
        sb.append("	  c.`class_id` classId,");
        sb.append("   c.`sort_id` sortId,");
        sb.append("   c.`banner_img` bannerImg,");
        sb.append("   c.`status`,");
        sb.append("   c.`type`,");
        sb.append("   c.`title`,");
        sb.append("   c.`charge`,");
        sb.append("   c.`org_price` orgPrice,");
        sb.append("   c.`dicount_price` dicountPrice,");
        sb.append("   (c.`org_price` - c.`dicount_price`) as realPrice,");
        sb.append("   c.`class_desc` classDesc,");
        sb.append("   c.`guide`, ");
        sb.append("   (select count(o.id) from review_order o where o.class_id=c.class_id and status in(2,3)) buyCount,");
        sb.append("   s.`subject_name` subjectName, ");
        sb.append("   s.`id` subjectId ");
        sb.append(" FROM  review_class c INNER JOIN review_subject_class sc on c.class_id=sc.class_id ");
        sb.append("   INNER JOIN review_subject s on sc.subject_id=s.id");
        sb.append(" WHERE c.status=").append(Constants.StatusPublish);
        //sb.append("   and s.status=").append(Constants.StatusPublish);

        Map<String, String> paramMap = new HashMap<>();
        if (reviewSubject.getId() != null && reviewSubject.getId() > 0) {
            sb.append(" and s.id=:subjectId").append(reviewSubject.getId());
            paramMap.put("subjectId", reviewSubject.getId().toString());
        }

        int page = reviewSubject.getDataGrid().getPage() > 0 ? reviewSubject.getDataGrid().getPage() : 1;
        int pageSize = reviewSubject.getDataGrid().getRows() > 0 ? reviewSubject.getDataGrid().getRows() : 10;
        sb.append(" limit ");
        sb.append((page-1) * pageSize);
        sb.append(", ");
        sb.append(pageSize);

        List<ReviewClassVO> resultList = this.getObjectList(sb.toString(), paramMap, ReviewClassVO.class);
        Map<Long, ReviewSubjectVO> resultMap = new HashMap<>();
        for (ReviewClassVO reviewClass : resultList) {
            ReviewSubjectVO subject = resultMap.get(reviewClass.getSubjectId());
            if (subject == null) {
                subject = new ReviewSubjectVO();
                subject.setSubjectName(reviewClass.getSubjectName());
                resultMap.put(reviewClass.getSubjectId(), subject);
            }
            subject.getClassList().add(reviewClass);
        }
        return new ArrayList<>(resultMap.values());
    }

    @Override
    public void publish(Long subjectId, String pubType) {
        String sql = "update review_subject set status=? where id=?";
        this.executeSql(sql, new Object[]{Integer.parseInt(pubType), subjectId});
    }
}