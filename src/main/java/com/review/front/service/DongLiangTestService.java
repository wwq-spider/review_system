package com.review.front.service;

import com.review.front.entity.EvalCodeEntity;
import com.review.front.entity.TestRecord;
import org.jeecgframework.core.common.service.CommonService;

import java.util.List;

/**
 * @author javabage
 * @date 2022/8/31
 */
public interface DongLiangTestService extends CommonService {

    List<EvalCodeEntity> verifyEvalCode(EvalCodeEntity evalCodeEntity);

    void handleQuestNo(List<TestRecord> testRecordListOld, int i, List<TestRecord> testRecordList,int type,String MNtype);
}
