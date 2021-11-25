package com.review.manage.project.service.impl;

import com.review.common.CommonUtils;
import com.review.common.Constants;
import com.review.common.OssUtils;
import com.review.common.WxAppletsUtils;
import com.review.manage.project.entity.ReviewProjectClassEntity;
import com.review.manage.project.entity.ReviewProjectEntity;
import com.review.manage.project.entity.ReviewProjectUserGroupEntity;
import com.review.manage.project.service.IReviewProjectService;
import com.review.manage.project.vo.ReviewProjectVO;
import com.review.manage.reviewClass.entity.ReviewClassEntity;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("reviewProjcetService")
public class ReviewProjcetServiceImpl extends CommonServiceImpl implements IReviewProjectService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ReviewProjectVO get(Long projectId) {
        ReviewProjectEntity project = this.get(ReviewProjectEntity.class, Long.valueOf(projectId));
        HashMap paramMap = new HashMap<String, String>();
        paramMap.put("projectId", projectId);
        ReviewProjectVO reviewProject = new ReviewProjectVO();

        try {
            MyBeanUtils.copyBean2Bean(reviewProject, project);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //关联量表
        reviewProject.setReviewProjectClassList(this.getObjectList("select id, project_id projectId, class_id classId, org_id orgId from review_project_class where project_id=:projectId", paramMap, ReviewProjectClassEntity.class));
        //关联用户组
        //reviewProject.setReviewProjectUserGroupList(this.getObjectList("select id, project_id projectId, group_id groupId from review_project_user_group where project_id=:projectId", paramMap, ReviewProjectUserGroupEntity.class));
        return reviewProject;
    }

    @Override
    @Transactional
    public boolean add(ReviewProjectVO reviewProject) {

        ReviewProjectEntity reviewProjectEntity = new ReviewProjectEntity();
        try {
            MyBeanUtils.copyBean2Bean(reviewProjectEntity, reviewProject);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        reviewProjectEntity.setCover(CommonUtils.saveCoverImg(reviewProject.getContentImg(), Constants.ReviewProjectDir));
        reviewProjectEntity.setCreateTime(new Date());
        reviewProjectEntity.setUpdateTime(reviewProjectEntity.getCreateTime());
        //保存项目
        this.save(reviewProjectEntity);
        reviewProject.setId(reviewProjectEntity.getId());
        //保存项目关联分类和用户组
        this.batchInsertProjectClass(reviewProject);

        //生成二维码
        String qrCodePath = WxAppletsUtils.geneAppletsQrCode("pages/index/index", "projectId=" + reviewProjectEntity.getId());
        reviewProjectEntity.setAppletsCrCodeLink(qrCodePath);
        this.saveOrUpdate(reviewProjectEntity);
        return true;
    }

    /**
     * 保存项目关联分类
     * @param reviewProject
     */
    private void batchInsertProjectClass(ReviewProjectVO reviewProject) {
        //保存关联量表
        List<ReviewProjectClassEntity> reviewProjectClassList = reviewProject.getReviewProjectClassList();
        if (CollectionUtils.isNotEmpty(reviewProjectClassList)) {
            for (ReviewProjectClassEntity reviewProjectClass : reviewProjectClassList) {
                reviewProjectClass.setProjectId(reviewProject.getId());
            }
            this.batchSave(reviewProjectClassList);
        }
        //保存关联用户组
//        List<ReviewProjectUserGroupEntity> reviewProjectUserGroupList = reviewProject.getReviewProjectUserGroupList();
//        if (CollectionUtils.isNotEmpty(reviewProjectUserGroupList)) {
//            for (ReviewProjectUserGroupEntity reviewProjectUserGroup : reviewProjectUserGroupList) {
//                reviewProjectUserGroup.setProjectId(reviewProject.getId());
//            }
//            this.batchSave(reviewProjectUserGroupList);
//        }
    }

    @Override
    @Transactional
    public boolean update(ReviewProjectVO reviewProject) {

        if (reviewProject == null || reviewProject.getId() == null || reviewProject.getId() ==0) {
            logger.warn("ReviewProjcet can not update, because reviewProjectEntity is null or id is null");
            return false;
        }

        ReviewProjectEntity reviewProjectEntity = this.get(ReviewProjectEntity.class, reviewProject.getId());
        try {
            MyBeanUtils.copyBean2Bean(reviewProjectEntity, reviewProject);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String coverPath = CommonUtils.saveCoverImg(reviewProject.getContentImg(), Constants.ReviewProjectDir);
        if (StringUtils.isNotBlank(coverPath)) {
            reviewProjectEntity.setCover(coverPath);
        }
        reviewProjectEntity.setUpdateTime(new Date());
        this.saveOrUpdate(reviewProjectEntity);

        Object[] params = new Object[]{reviewProject.getId()};

        //更新是先删除 后插入
        this.executeSql("delete from review_project_class where project_id=?", params);
        //this.executeSql("delete from review_project_user_group where project_id=?", params);
        this.batchInsertProjectClass(reviewProject);
        return true;
    }

    @Override
    public List<ReviewProjectVO> getReviewProjectList(ReviewProjectEntity reviewProject, DataGrid dataGrid) {
        StringBuilder sql = new StringBuilder();
        sql.append("select id, project_desc projectDesc, " +
                "project_name projectName, " +
                "applets_qr_code_link appletsCrCodeLink, " +
                "DATE_FORMAT(`create_time`,'%Y-%m-%e %H:%i:%S') createTime, " +
                "DATE_FORMAT(`update_time`,'%Y-%m-%e %H:%i:%S') updateTime, " +
                "creator from review_project where 1=1 ");
        Map<String, Object> paramMap = new HashMap<>();
        if (reviewProject != null) {
            if (StringUtils.isNotBlank(reviewProject.getProjectName())) {
                sql.append(" and project_name like :projectName");
                paramMap.put("projectName", reviewProject.getProjectName());
            }
        }
        sql.append("limit ").append((dataGrid.getPage() - 1) * dataGrid.getRows()).append(", ").append(dataGrid.getRows());

        return this.getObjectList(sql.toString(), paramMap, ReviewProjectVO.class);
    }

    @Override
    public Long getReviewProjectCount(ReviewProjectEntity reviewProject) {
        StringBuilder sql = new StringBuilder("select count(id) from review_project where 1=1 ");
        if (reviewProject != null) {
            if (StringUtils.isNotBlank(reviewProject.getProjectName())) {
                sql.append(" and project_name like '").append(reviewProject.getProjectName()).append("'");
            }
        }
        return this.getCountForJdbc(sql.toString());
    }

    @Override
    @Transactional
    public void delProject(Long projectId) {
        this.deleteEntityById(ReviewProjectEntity.class, projectId);
        this.executeSql("delete from review_project_class where project_id=?", new Object[]{projectId});
    }
}
