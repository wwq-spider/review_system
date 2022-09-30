package com.review.front.service;

import com.review.front.entity.DongliangTestQuestionVO;
import com.review.front.entity.EvalCodeEntity;
import com.review.front.entity.TestRecord;
import com.review.manage.userManage.entity.ReviewUserEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.util.List;

/**
 * @author javabage
 * @date 2022/8/31
 */
public interface DongLiangTestService extends CommonService {

    List<EvalCodeEntity> verifyEvalCode(EvalCodeEntity evalCodeEntity);

    void handleData(DongliangTestQuestionVO[] dongliangTestQuestionVO);

    void handleBusinessData(DongliangTestQuestionVO[] dongliangTestQuestionVO, ReviewUserEntity reviewUser);
}
