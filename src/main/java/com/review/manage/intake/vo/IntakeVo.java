package com.review.manage.intake.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author javabage
 * @date 2022/7/20
 */
@Data
public class IntakeVo implements Serializable {
    private String id;
    private String callRecordNumber;
    private String hangUpTime;
    private String problemDescription;
    private String note;
    private String callTypeId;
    private String keyCases;
    private String companyId;
    private String companyName;
    private String dangerousSituation;
    private String branchId;
    private String caseUrgency;
    private String callName;
    private String usedEAP;
    private String employeeName;
    private String maritalStatus;
    private String clientName;
    private String eapCognitionId;
    private String clientPhone;
    private String employeeBirthday;
    private String employeeJobNumber;
    private String jobPhone;
    private String clientGender;
    private String familyPhone;
    private String age;
    private String email;
    private String callerId;
    private String workTypeId;
    private String consultantId;
    private String employeePosition;
    private String clientArea;
    private String station;
    private String referralSource;
    private String expectAppointmentTime;
    private String problemTypeId;
    private String expectationsForConsultants;
}
