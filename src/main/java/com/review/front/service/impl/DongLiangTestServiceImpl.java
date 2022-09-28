package com.review.front.service.impl;

import cn.hutool.core.date.DateUtil;
import com.review.front.entity.DongliangTestQuestionVO;
import com.review.front.entity.EvalCodeEntity;
import com.review.front.entity.TestRecord;
import com.review.front.service.DongLiangTestService;
import com.review.front.vo.SelectVO;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public void handleData(DongliangTestQuestionVO[] dongliangTestQuestionVO) {

        //questNo及第二套试题处理
        int I = 1;//第一套试题题号
        int M = 1;//第二套试题题号
        int N = 1;
        int A = 1;//第三套试题题号
        int P = 1;//第四套试题题号
        List<TestRecord> testRecordList = new ArrayList<>();
        List<TestRecord> testRecordListOld = dongliangTestQuestionVO[0].getTestRecord();
        for (int i = 0; i < testRecordListOld.size(); i++) {
            if (i < 162){
                TestRecord testRecord = new TestRecord();
                testRecord.setQuesNo("I" + I);
                testRecord.setAnswer(testRecordListOld.get(i).getAnswer());
                testRecord.setScoreA(testRecordListOld.get(i).getScoreA());
                testRecord.setScoreB(testRecordListOld.get(i).getScoreB());
                testRecordList.add(testRecord);
                I++;
            } else if (i >= 162 && i < 204){
                this.handleQuestNo(testRecordListOld,i,testRecordList,M,"M");
                M++;
            } else if (i >= 204 && i < 246){
                this.handleQuestNo(testRecordListOld,i,testRecordList,N,"N");
                N++;
            } else if (i >= 246 && i < 336 ){
                TestRecord testRecord = new TestRecord();
                testRecord.setQuesNo("A" + A);
                testRecord.setAnswer(testRecordListOld.get(i).getAnswer());
                testRecord.setScoreA(testRecordListOld.get(i).getScoreA());
                testRecord.setScoreB(testRecordListOld.get(i).getScoreB());
                testRecordList.add(testRecord);
                A++;
            } else if ( i >= 336 ){
                TestRecord testRecord = new TestRecord();
                testRecord.setQuesNo("P" + P);
                testRecord.setAnswer(testRecordListOld.get(i).getAnswer());
                testRecord.setScoreA(testRecordListOld.get(i).getScoreA());
                testRecord.setScoreB(testRecordListOld.get(i).getScoreB());
                testRecordList.add(testRecord);
                P++;
            }
        }
        dongliangTestQuestionVO[0].setTestRecord(testRecordList);
        dongliangTestQuestionVO[0].getUserInfo().setSex(dongliangTestQuestionVO[0].getUserInfo().getSex().equals("1") ? "男" : "女");
        String select = dongliangTestQuestionVO[0].getUserInfo().getSelect();
        String[] provinceAndArea = select.split("--");
        dongliangTestQuestionVO[0].getUserInfo().setProvince(provinceAndArea[0]);
        dongliangTestQuestionVO[0].getUserInfo().setCity(provinceAndArea[1]);
        dongliangTestQuestionVO[0].getUserInfo().setArea(provinceAndArea[2]);
    }

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

    @Override
    public void evalCodeSetInvalid(String testCode, String userId) {
        String sql = "update review_eval_code set status=2 where user_id= ? and eval_code = ?";
        this.executeSql(sql, new Object[]{userId, testCode});
    }

}
