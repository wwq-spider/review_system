package com.review.manage.question.service;
import com.review.manage.question.vo.ReviewQuestionAnswerVO;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.core.common.service.CommonService;
import java.util.List;

public interface ReviewQuestionAnswerServiceI extends CommonService{

    /**
     * 封装导出数据
     * @param groupId
     * @return
     */
    Workbook getExportWorkbook(String groupId);

    /**
     * 获取答题记录列表
     * @param groupId
     * @return
     */
    List<ReviewQuestionAnswerVO> getListByGroupId(String groupId);
}
