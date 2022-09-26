package com.review.front.service.impl;

import com.review.front.entity.EvalCodeEntity;
import com.review.front.entity.TestRecord;
import com.review.front.service.DongLiangTestService;
import com.review.front.vo.SelectVO;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author javabage
 * @date 2022/8/31
 */
@Service("dongLiangTestService")
@Transactional
public class DongLiangTestServiceImpl extends CommonServiceImpl implements DongLiangTestService {
    @Override
    public List<EvalCodeEntity> verifyEvalCode(EvalCodeEntity evalCodeEntity) {
        StringBuilder sb =  new StringBuilder();
        sb.append("SELECT ");
        sb.append(" user_id as userId,");
        sb.append(" eval_code as evalCode,");
        sb.append(" operate_time as operateTime");
        sb.append(" FROM");
        sb.append(" review_eval_code");
        sb.append(" WHERE");
        sb.append(" user_id =:userId");
        sb.append(" AND eval_code =:evalCode");
        sb.append(" AND STATUS = 1");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("userId", evalCodeEntity.getUserId());
        map.put("evalCode", evalCodeEntity.getEvalCode());
        List<EvalCodeEntity> list = this.getObjectList(sb.toString(), map,EvalCodeEntity.class);
        return list;
    }

    @Override
    public void handleQuestNo(List<TestRecord> testRecordListOld, int i, List<TestRecord> testRecordList,int M,String MNtype) {
        if (testRecordListOld.get(i).getAnswer().equals("A")){
            TestRecord testRecordA = new TestRecord();
            testRecordA.setQuesNo(MNtype + M);
            testRecordA.setAnswer("A");
            testRecordA.setScoreA("3");
            testRecordA.setScoreB("0");
            TestRecord testRecordB = new TestRecord();
            testRecordB.setQuesNo(MNtype + M);
            testRecordB.setAnswer("B");
            testRecordB.setScoreA("0");
            testRecordB.setScoreB("0");
            testRecordList.add(testRecordA);
            testRecordList.add(testRecordB);
        }else if (testRecordListOld.get(i).getAnswer().equals("B")){
            TestRecord testRecordA = new TestRecord();
            testRecordA.setQuesNo(MNtype + M);
            testRecordA.setAnswer("A");
            testRecordA.setScoreA("2");
            testRecordA.setScoreB("0");
            TestRecord testRecordB = new TestRecord();
            testRecordB.setQuesNo(MNtype + M);
            testRecordB.setAnswer("B");
            testRecordB.setScoreA("1");
            testRecordB.setScoreB("0");
            testRecordList.add(testRecordA);
            testRecordList.add(testRecordB);
        }else if (testRecordListOld.get(i).getAnswer().equals("C")){
            TestRecord testRecordA = new TestRecord();
            testRecordA.setQuesNo(MNtype + M);
            testRecordA.setAnswer("A");
            testRecordA.setScoreA("0");
            testRecordA.setScoreB("0");
            TestRecord testRecordB = new TestRecord();
            testRecordB.setQuesNo(MNtype + M);
            testRecordB.setAnswer("B");
            testRecordB.setScoreA("3");
            testRecordB.setScoreB("0");
            testRecordList.add(testRecordA);
            testRecordList.add(testRecordB);
        }else if (testRecordListOld.get(i).getAnswer().equals("D")){
            TestRecord testRecordA = new TestRecord();
            testRecordA.setQuesNo(MNtype + M);
            testRecordA.setAnswer("A");
            testRecordA.setScoreA("1");
            testRecordA.setScoreB("0");
            TestRecord testRecordB = new TestRecord();
            testRecordB.setQuesNo(MNtype + M);
            testRecordB.setAnswer("B");
            testRecordB.setScoreA("2");
            testRecordB.setScoreB("0");
            testRecordList.add(testRecordA);
            testRecordList.add(testRecordB);
        }
    }
}
