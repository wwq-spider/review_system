package com.review.manage.project.service.impl;

import com.review.common.WxAppletsUtils;
import com.review.manage.project.entity.ReviewProjectClassEntity;
import com.review.manage.project.entity.ReviewProjectEntity;
import com.review.manage.project.service.IReviewProjectService;
import com.review.manage.project.vo.ReviewProjectVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("reviewProjcetService")
public class ReviewProjcetServiceImpl extends CommonServiceImpl implements IReviewProjectService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ReviewProjectEntity get(Long projectId) {
        ReviewProjectEntity project = this.get(ReviewProjectEntity.class, Long.valueOf(projectId));
        HashMap paramMap = new HashMap<String, String>();
        paramMap.put("projectId", projectId);
        project.setReviewProjectClassList(this.getObjectList("select id, project_id projectId, class_id classId, org_id orgId from review_project_class where project_id=:projectId", paramMap, ReviewProjectClassEntity.class));
        return project;
    }

    @Override
    @Transactional
    public boolean add(ReviewProjectEntity reviewProjectEntity) {
        //保存项目
        this.save(reviewProjectEntity);
        //保存项目关联分类
        this.batchInsertProjectClass(reviewProjectEntity);

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
    private void batchInsertProjectClass(ReviewProjectEntity reviewProject) {
        List<ReviewProjectClassEntity> reviewProjectClassList = reviewProject.getReviewProjectClassList();
        if (CollectionUtils.isNotEmpty(reviewProjectClassList)) {
            for (ReviewProjectClassEntity reviewProjectClass : reviewProjectClassList) {
                reviewProjectClass.setProjectId(reviewProject.getId());
            }
            this.batchSave(reviewProjectClassList);
        }
    }

    @Override
    @Transactional
    public boolean update(ReviewProjectEntity reviewProject) {

        if (reviewProject == null || reviewProject.getId() == null || reviewProject.getId() ==0) {
            logger.warn("ReviewProjcet can not update, because reviewProjectEntity is null or id is null");
            return false;
        }

        ReviewProjectEntity reviewProjectEntity = this.get(ReviewProjectEntity.class, reviewProject.getId());
        BeanUtils.copyProperties(reviewProject, reviewProjectEntity);
        this.saveOrUpdate(reviewProjectEntity);

        //更新是先删除 后插入
        this.executeSql("delete from review_project_class where project_id=?", new Object[]{reviewProject.getId()});
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
        Map<String, String> paramMap = new HashMap<>();
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
