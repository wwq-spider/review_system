package com.review.manage.intake.constants;


import lombok.Getter;

/**
 * intake下拉框选项枚举类
 * @author javabage
 * @date 2022/7/19
 */
public class Constants {

    public enum Options {

        callType("callType"), //来电类型
        company("company"), //公司名称
        branch("branch"),//分支机构
        callRelations("callRela"),//来电者与员工关系
        consultantRelations("consuRela"),//咨客与员工关系
        contactType("contactT"),//联系类型
        eapCognition("eap"),//对EAP认识
        problemType("problem"),//呈现问题类型
        referralSource("refSource"),//转介来源
        workType("work"),//工作种类

        assessMproblem("assessMp"),//评估主要问题
        assessSproblem("assessSp"),//评估次要问题
        serviceType("serviceType"),//服务类型
        caseStatus("caseStatus"),//个案状态
        crisisAssess("crisisAssess"),//危机评估
        crisisLevel("crisisLevel"),//危机程度
        clientRelation("clientRelation");//紧急联系人与咨客关系

        @Getter
        private String value;

        Options(String value) {
            this.value= value;
        }
    }
}
