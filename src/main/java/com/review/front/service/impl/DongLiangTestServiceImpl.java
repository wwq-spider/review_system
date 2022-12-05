package com.review.front.service.impl;

import cn.hutool.core.date.DateUtil;
import com.review.common.Constants;
import com.review.front.entity.DongliangTestQuestionVO;
import com.review.front.entity.EvalCodeEntity;
import com.review.front.entity.ReviewResultEntity;
import com.review.front.entity.TestRecord;
import com.review.front.service.DongLiangTestService;
import com.review.front.vo.SelectVO;
import com.review.manage.project.entity.ReviewProjectEntity;
import com.review.manage.project.service.IReviewProjectService;
import com.review.manage.userManage.entity.ReviewUserEntity;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author javabage
 * @date 2022/8/31
 */
@Service("dongLiangTestService")
@Transactional
public class DongLiangTestServiceImpl extends CommonServiceImpl implements DongLiangTestService {

    //封面背景图地址
    private static final String coverUrl = "https://pcapi.dilighthealth.com/profile/upload/2022/10/09/9b1ff86c-6a28-4496-8b5f-6a2a21570ad1.png";
    //logo 背景图地址
    //private static final String logoUrl = "https://pcapi.dilighthealth.com/profile/upload/2022/10/09/9b1ff86c-6a28-4496-8b5f-6a2a21570ad1.png";
    private static final String logoUrl = "https://www.xinzhaitongxing.com/review/plug-in/login/images/logo5.png";
    //公司名称
    private static final String companyName = "北京心宅同行管理咨询有限公司";
    //二维码地址
    //private static final String qrCodeUrl = "https://pcapi.dilighthealth.com/profile/upload/2022/10/10/b69f7e67-65bb-43dc-b02b-8cb5b8e042c6.png";
    private static final String qrCodeUrl =  "https://www.xinzhaitongxing.com/review/plug-in/login/images/appLogo.jpg";
    //封面标题
    private static final String indexTitle = "生涯发展评估报告";
    //报告撰写
    private static final String reportWriting = "北京心宅同行管理咨询有限公司";
    //联系方式
    private static final String contactPhone = "18510801311";
    //联系人
    private static final String contactPeople = "北京心宅同行管理咨询有限公司";
    //联系地址
    private static final String contactAddress = "北京市石景山区";

    @Autowired
    private IReviewProjectService reviewProjectService;
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
        /*sb.append(" user_id =:userId");
        sb.append(" AND eval_code =:evalCode");*/
        sb.append(" eval_code =:evalCode");
        sb.append(" AND STATUS IN (1,3)");
        Map<String,Object> map = new HashMap<String, Object>();
        //map.put("userId", evalCodeEntity.getUserId());
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
        dongliangTestQuestionVO[0].setTestCode(dongliangTestQuestionVO[0].getTestCode() + "==");
        dongliangTestQuestionVO[0].setTestRecord(testRecordList);
        dongliangTestQuestionVO[0].getUserInfo().setSex(dongliangTestQuestionVO[0].getUserInfo().getSex().equals("1") ? "男" : "女");
        String select = dongliangTestQuestionVO[0].getUserInfo().getSelect();
        String[] provinceAndArea = select.split("--");
        dongliangTestQuestionVO[0].getUserInfo().setProvince(provinceAndArea[0]);
        dongliangTestQuestionVO[0].getUserInfo().setCity(provinceAndArea[1]);
        dongliangTestQuestionVO[0].getUserInfo().setArea(provinceAndArea[2]);
        //报告封面元素
        dongliangTestQuestionVO[0].setCoverUrl(coverUrl);
        dongliangTestQuestionVO[0].setLogoUrl(logoUrl);
        dongliangTestQuestionVO[0].setCompanyName(companyName);
        dongliangTestQuestionVO[0].setQrCodeUrl(qrCodeUrl);
        dongliangTestQuestionVO[0].setIndexTitle(indexTitle);
        dongliangTestQuestionVO[0].setReportWriting(reportWriting);
        dongliangTestQuestionVO[0].setContactPhone(contactPhone);
        dongliangTestQuestionVO[0].setContactPeople(contactPeople);
        dongliangTestQuestionVO[0].setContactAddress(contactAddress);
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

    /**
     * 业务数据处理：1、测评码置为已使用状态 2、添加测评结果
     * @param dongliangTestQuestionVO
     */
    @Override
    public void handleBusinessData(DongliangTestQuestionVO[] dongliangTestQuestionVO, ReviewUserEntity reviewUser) {
        /*String sql = "update review_eval_code set status=2 where user_id= ? and eval_code = ?";
        this.executeSql(sql, new Object[]{dongliangTestQuestionVO[0].getUserInfo().getUserId(),dongliangTestQuestionVO[0].getTestCode()});*/
        String sql = "update review_eval_code set status=2 where eval_code = ?";
        this.executeSql(sql, new Object[]{dongliangTestQuestionVO[0].getTestCode()});
        //添加测评结果
        ReviewResultEntity reviewResult = new ReviewResultEntity();
        reviewResult.setUserId(dongliangTestQuestionVO[0].getUserInfo().getUserId());
        reviewResult.setClassId(dongliangTestQuestionVO[0].getClassId());
        reviewResult.setCreateTime(new Date());
        reviewResult.setCreateBy(dongliangTestQuestionVO[0].getUserInfo().getName());
        reviewResult.setProjectId(dongliangTestQuestionVO[0].getProjectId()); //项目id
        reviewResult.setReviewResult(dongliangTestQuestionVO[0].getReportUrl());
        this.save(reviewResult);
    }

    @Override
    public List<EvalCodeEntity> getEvalCode() {
        StringBuilder sb =  new StringBuilder();
        sb.append("SELECT ");
        sb.append(" eval_code as evalCode");
        sb.append(" FROM");
            sb.append(" review_eval_code");
        sb.append(" WHERE status = 1");
        sb.append(" LIMIT 1");
        List<EvalCodeEntity> list = this.getObjectList(sb.toString(), null,EvalCodeEntity.class);
        //将该测评码暂设为已购买，如支付失败，恢复该测评码状态为可购买
        if (list.size() > 0){
            String sql = "update review_eval_code set status=3 where eval_code = ?";
            this.executeSql(sql,list.get(0).getEvalCode());
        }
        return list;
    }

    /**
     * 获取测评码价格
     * @param evalCodeEntity
     * @return
     */
    @Override
    public String getEvalPrice(EvalCodeEntity evalCodeEntity) {
        StringBuilder sb =  new StringBuilder();
        sb.append("SELECT ");
        sb.append(" price AS price");
        sb.append(" FROM");
        sb.append(" review_eval_code");
        sb.append(" WHERE status = 1");
        sb.append(" LIMIT 1");
        List<EvalCodeEntity> list = this.getObjectList(sb.toString(), null,EvalCodeEntity.class);
        if (list.size() > 0){
            return list.get(0).getPrice();
        }
        return null;
    }
}
