package com.review.manage.intake.service.impl;

import com.review.manage.intake.constants.Constants;
import com.review.manage.intake.entity.*;
import com.review.manage.intake.service.IntakeService;
import com.review.manage.intake.vo.IntakeVo;
import com.review.manage.order.vo.ReviewOrderVO;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author javabage
 * @date 2022/7/18
 */
@Service("intakeService")
@Transactional
public class IntakeServiceImpl extends CommonServiceImpl implements IntakeService {

    @Autowired
    private SystemService systemService;

    @Override
    public void handleOptions(ModelAndView modelAndView) {
        CriteriaQuery cq = new CriteriaQuery(TSTypegroup.class);
        List<TSTypegroup> typeGroupList = systemService.getListByCriteriaQuery(cq, false);
        for (int i = 0; i < typeGroupList.size(); i++) {
            if (typeGroupList.get(i).getTypegroupcode().equals(Constants.Options.callType.getValue())){//来电类型
                modelAndView.addObject("callTypeList",typeGroupList.get(i).getTSTypes());
            }
            if (typeGroupList.get(i).getTypegroupcode().equals(Constants.Options.company.getValue())){
                modelAndView.addObject("companyList",typeGroupList.get(i).getTSTypes());//公司名称
            }
            if (typeGroupList.get(i).getTypegroupcode().equals(Constants.Options.branch.getValue())){
                modelAndView.addObject("branchEntityList",typeGroupList.get(i).getTSTypes());//分支机构
            }
            if (typeGroupList.get(i).getTypegroupcode().equals(Constants.Options.callRelations.getValue())){
                modelAndView.addObject("callerRelationsEntityList",typeGroupList.get(i).getTSTypes());//来电者与员工关系
            }
            if (typeGroupList.get(i).getTypegroupcode().equals(Constants.Options.consultantRelations.getValue())){
                modelAndView.addObject("consultantRelationsEntityList",typeGroupList.get(i).getTSTypes());//咨客与员工关系
            }
            if (typeGroupList.get(i).getTypegroupcode().equals(Constants.Options.contactType.getValue())){
                modelAndView.addObject("contactTypeEntityList",typeGroupList.get(i).getTSTypes());//联系类型
            }
            if (typeGroupList.get(i).getTypegroupcode().equals(Constants.Options.eapCognition.getValue())){
                modelAndView.addObject("eapCognitionEntityList",typeGroupList.get(i).getTSTypes());//对EAP认识
            }
            if (typeGroupList.get(i).getTypegroupcode().equals(Constants.Options.problemType.getValue())){
                modelAndView.addObject("problemTypeEntityList",typeGroupList.get(i).getTSTypes());//呈现问题类型
            }
            if (typeGroupList.get(i).getTypegroupcode().equals(Constants.Options.referralSource.getValue())){
                modelAndView.addObject("referralSourceEntityList",typeGroupList.get(i).getTSTypes());//转介来源
            }
            if (typeGroupList.get(i).getTypegroupcode().equals(Constants.Options.workType.getValue())){
                modelAndView.addObject("workTypeEntityList",typeGroupList.get(i).getTSTypes());//转介来源
            }
            if (typeGroupList.get(i).getTypegroupcode().equals(Constants.Options.assessMproblem.getValue())){
                modelAndView.addObject("assessMproblemList",typeGroupList.get(i).getTSTypes());//评估主要问题
            }
            if (typeGroupList.get(i).getTypegroupcode().equals(Constants.Options.assessSproblem.getValue())){
                modelAndView.addObject("assessSproblemList",typeGroupList.get(i).getTSTypes());//评估次要问题
            }
            if (typeGroupList.get(i).getTypegroupcode().equals(Constants.Options.serviceType.getValue())){
                modelAndView.addObject("serviceTypeList",typeGroupList.get(i).getTSTypes());//服务类型
            }
            if (typeGroupList.get(i).getTypegroupcode().equals(Constants.Options.caseStatus.getValue())){
                modelAndView.addObject("caseStatusList",typeGroupList.get(i).getTSTypes());//个案状态
            }
            if (typeGroupList.get(i).getTypegroupcode().equals(Constants.Options.crisisAssess.getValue())){
                modelAndView.addObject("crisisAssessList",typeGroupList.get(i).getTSTypes());//危机评估
            }
            if (typeGroupList.get(i).getTypegroupcode().equals(Constants.Options.crisisLevel.getValue())){
                modelAndView.addObject("crisisLevelList",typeGroupList.get(i).getTSTypes());//危机程度
            }
            if (typeGroupList.get(i).getTypegroupcode().equals(Constants.Options.clientRelation.getValue())){
                modelAndView.addObject("clientRelationList",typeGroupList.get(i).getTSTypes());//与咨客关系
            }
        }
    }

    @Override
    public List<IntakeVo> getIntakeInfo(IntakeVo intakeVo) {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT");
        sql.append(" id AS id,");
        sql.append(" company_name AS companyName,");
        sql.append(" branch AS branchId,");
        sql.append(" employee_name AS employeeName,");
        sql.append(" employee_job_number AS employeeJobNumber,");
        sql.append(" station AS station,");
        sql.append(" employee_position AS employeePosition,");
        sql.append(" client_area AS clientArea");
        sql.append(" FROM review_intake");
        sql.append(" WHERE 1=1");
        Map<String, Object> paramMap = new HashMap<>();
        if (intakeVo.getEmployeeJobNumber() != null && !"".equals(intakeVo.getEmployeeJobNumber())){
            sql.append(" and employee_job_number =:employeeJobNumber");
            paramMap.put("employeeJobNumber",intakeVo.getEmployeeJobNumber());
        }
        if (intakeVo.getEmployeeName() != null && !"".equals(intakeVo.getEmployeeName())){
            sql.append(" and employee_name =:employeeName");
            paramMap.put("employeeName",intakeVo.getEmployeeName());
        }
        return this.getObjectList(sql.toString(),paramMap,IntakeVo.class);
    }
}
