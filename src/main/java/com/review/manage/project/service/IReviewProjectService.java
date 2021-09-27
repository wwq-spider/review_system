package com.review.manage.project.service;

import com.review.manage.project.entity.ReviewProjectEntity;
import com.review.manage.project.vo.ReviewProjectVO;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import java.util.List;

/**
 * 测评项目管理接口
 */
public interface IReviewProjectService extends CommonService {

    /**
     * 查询详细
     * @param projectId
     * @return
     */
    ReviewProjectEntity get(Long projectId);

    /**
     * 添加项目
     * @param reviewProjectEntity
     * @return
     */
    boolean add(ReviewProjectEntity reviewProjectEntity);

    /**
     * 修改项目
     * @param reviewProjectEntity
     * @return
     */
    boolean update(ReviewProjectEntity reviewProjectEntity);

    /**
     * 查询项目列表
     * @param reviewProject
     * @return
     */
    List<ReviewProjectVO> getReviewProjectList(ReviewProjectEntity reviewProject, DataGrid dataGrid);

    /**
     * 查询项目数量
     * @param reviewProject
     * @return
     */
    Long getReviewProjectCount(ReviewProjectEntity reviewProject);

    /**
     * 删除项目及关联分类
     * @param projectId
     */
    void delProject(Long projectId);
}
