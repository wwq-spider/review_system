package com.review.manage.reviewClass.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.review.common.OssUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.UUIDGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.review.manage.reviewClass.entity.ReviewClassEntity;
import com.review.manage.reviewClass.service.ReviewClassService;
import com.review.manage.reviewClass.vo.ReviewClassVO;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Service("reviewClassService")
@Transactional
public class ReviewClassServiceImpl extends CommonServiceImpl implements ReviewClassService{

	@Override
	public void addReviewClass(ReviewClassVO reviewClass) throws Exception {
		ReviewClassEntity reviewClassEntity = new ReviewClassEntity();
		MyBeanUtils.copyBean2Bean(reviewClassEntity, reviewClass);
		this.saveBannerImg(reviewClass.getContentImg(), reviewClassEntity);
		this.save(reviewClassEntity);
	}

	/**
	 * 保存封面图片
	 * @param contentImg
	 * @param reviewClassEntity
	 * @throws IOException
	 */
	private void saveBannerImg(CommonsMultipartFile contentImg, ReviewClassEntity reviewClassEntity) throws IOException {
		//上传封面图片
		if (contentImg != null && !contentImg.isEmpty()) {
			String path = OssUtils.uploadFile("review-class/%s/" + UUIDGenerator.generate() + ".jpg", contentImg.getBytes());
			if (StringUtils.isNotBlank(path)) {
				reviewClassEntity.setBannerImg(path);
			}
			//生成二维码
//			String rootPath = ContextHolderUtils.getSession().getServletContext().getRealPath("/");
//			String filePath = "/upload/banner/" + UUIDGenerator.generate() + ".jpg";
//			File dir = new File(rootPath + filePath).getParentFile();
//			if (!dir.exists()) dir.mkdir();
//			FileUtils.writeByteArrayToFile(new File(rootPath + filePath), contentImg.getBytes());
//			reviewClassEntity.setBannerImg(filePath);
		}
	}

	@Override
	public void updateReviewClass(ReviewClassVO reviewClass) throws Exception {
		
		ReviewClassEntity reviewClassEntity = this.get(ReviewClassEntity.class, reviewClass.getClassId());
		MyBeanUtils.copyBean2Bean(reviewClassEntity, reviewClass);

		this.saveBannerImg(reviewClass.getContentImg(), reviewClassEntity);
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
		sb.append("   c.`guide` ");
		sb.append(" FROM  ");
		HashMap<String, String> paramMap = new HashMap<>();
		if (projectId == null || projectId == 0) {
			sb.append(" review_class c");
			sb.append(" WHERE c.`status`=1");
		} else {
			paramMap.put("projectId", projectId+"");
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
}
