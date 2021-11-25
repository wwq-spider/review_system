package com.review.manage.reviewClass.service.impl;

import com.review.common.CommonUtils;
import com.review.common.Constants;
import com.review.manage.reviewClass.entity.ReviewClassEntity;
import com.review.manage.reviewClass.service.ReviewClassService;
import com.review.manage.reviewClass.vo.ReviewClassVO;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("reviewClassService")
@Transactional
public class ReviewClassServiceImpl extends CommonServiceImpl implements ReviewClassService{

	@Override
	public void addReviewClass(ReviewClassVO reviewClass) throws Exception {
		ReviewClassEntity reviewClassEntity = new ReviewClassEntity();
		MyBeanUtils.copyBean2Bean(reviewClassEntity, reviewClass);
		reviewClassEntity.setBannerImg(CommonUtils.saveCoverImg(reviewClass.getContentImg(), Constants.ReviewClassDir));
		this.save(reviewClassEntity);
	}

	@Override
	public void updateReviewClass(ReviewClassVO reviewClass) throws Exception {
		
		ReviewClassEntity reviewClassEntity = this.get(ReviewClassEntity.class, reviewClass.getClassId());
		MyBeanUtils.copyBean2Bean(reviewClassEntity, reviewClass);

		String path = CommonUtils.saveCoverImg(reviewClass.getContentImg(), Constants.ReviewClassDir);
		if (StringUtils.isNotBlank(path)) {
			reviewClassEntity.setBannerImg(path);
		}
		//先删除题目-分类信息
		/*delQuestionClass(reviewClass.getClassId());
		//再添加题目- 分类信息
		String[] questionIdArr = reviewClass.getQuestionIds().split(",");
		ReviewQuestionClassEntity questionClass = null;
		for(String questionId : questionIdArr) {
			questionClass = new ReviewQuestionClassEntity();
			questionClass.setClassId(reviewClass.getClassId());
			questionClass.setQuestionId(Integer.parseInt(questionId));
		}*/
		this.saveOrUpdate(reviewClassEntity);
	}

	/**
	 * 删除题目分类
	 * @param classId
	 */
	private void delQuestionClass(String classId){
		String sql = "delete from review_question_class where class_id=?";
		this.executeSql(sql, new Object[]{classId});
	}
	
	@Override
	public List<Map<String, Object>> getReviewClassList(
			ReviewClassVO reviewClass, DataGrid dataGrid) {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT ");
		sb.append("	  c.`class_id` classId,");
		sb.append("   c.`sort_id` sortId,");
		sb.append("   c.`status`,");
		sb.append("   c.`banner_img` bannerImg,");
		sb.append("   c.`type`,");
		sb.append("   c.`title`,");
		sb.append("   c.`guide` ");
		sb.append(" FROM  ");
		sb.append("   review_class c  ");
		sb.append(" ORDER BY c.`sort_id` ASC ");

		return this.findForJdbc(sb.toString(), dataGrid.getPage(), dataGrid.getRows());
	}

	@Override
	public List<ReviewClassVO> getReviewClassByProjectId(Long projectId) {
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
		if (projectId == null || projectId == 0) {
			sb.append(" (select count(o.id) from review_order o where o.class_id=c.class_id and status in(2,3)) buyCount,");
		}
		sb.append("   c.`guide` ");
		sb.append(" FROM  ");
		HashMap<String, Object> paramMap = new HashMap<>();
		if (projectId == null || projectId == 0) {
			sb.append(" review_class c");
			sb.append(" WHERE c.`status`=1");
		} else {
			paramMap.put("projectId", projectId);
			sb.append("   review_class c,review_project_class pc");
			sb.append(" WHERE c.`status`=1 and c.`class_id`=pc.class_id and pc.project_id=:projectId");
		}
		sb.append(" ORDER BY c.`sort_id` ASC ");
		return this.getObjectList(sb.toString(), paramMap, ReviewClassVO.class);
	}

	@Override
	public Long getReviewClassCount(ReviewClassVO reviewClass) {
		String sql = "select count(class_id) from review_class";
		return this.getCountForJdbc(sql);
	}

	@Override
	public void delReviewClass(String classId) {
		delQuestionClass(classId);
		this.deleteEntityById(ReviewClassEntity.class, classId);
	}

	@Override
	public void publishClass(String classId, String pubType) {
		String sql = "update review_class set status=? where class_id=?";
		this.executeSql(sql, new Object[]{Integer.parseInt(pubType),classId});
	}

	@Override
	public void setUpHot(String classId, Integer opt) {
		String sql = "update review_class set type=? where class_id=?";
		this.executeSql(sql, new Object[]{opt,classId});
	}

	@Override
	public boolean projectContainsClass(Long projectId, String classId) {
		long count = this.getCountForJdbcParam("select count(pc.id) from review_project_class pc where pc.class_id=? and pc.project_id=?", new Object[]{classId, projectId});
		return count > 0;
	}
}
