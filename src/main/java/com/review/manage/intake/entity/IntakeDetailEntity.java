package com.review.manage.intake.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author javabage
 * @date 2022/7/19
 */
@Entity
@Table(name = "review_intake")
public class IntakeDetailEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "calllog_num")
    private String callRecordNumber;

    @Column(name = "hangup_time")
    private String hangUpTime;

    @Column(name = "problem_description")
    private String problemDescription;

    @Column(name = "note")
    private String note;

    @Column(name = "calltype")
    private String callType;

    @Column(name = "keycases")
    private String keyCases;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "dangerous_situation")
    private String dangerousSituation;

    @Column(name = "branch")
    private String branch;

    @Column(name = "case_urgency")
    private String caseUrgency;

    @Column(name = "caller_name")
    private String callName;

    @Column(name = "used_eap")
    private String usedEAP;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "marital_status")
    private String maritalStatus;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "eap_cognition")
    private String eapCognitionId;

    @Column(name = "client_phone")
    private String clientPhone;

    @Column(name = "employee_birthday")
    private String employeeBirthday;

    @Column(name = "employee_job_number")
    private String employeeJobNumber;

    @Column(name = "job_phone")
    private String jobPhone;

    @Column(name = "client_gender")
    private String clientGender;

    @Column(name = "family_phone")
    private String familyPhone;

    @Column(name = "client_age")
    private String age;

    @Column(name = "client_email")
    private String email;

    @Column(name = "caller_relations")
    private String callerRelations;

    @Column(name = "work_type")
    private String workType;

    @Column(name = "consultant_relations")
    private String consultantRelations;

    @Column(name = "employee_position")
    private String employeePosition;

    @Column(name = "client_area")
    private String clientArea;

    @Column(name = "station")
    private String station;

    @Column(name = "referral_source")
    private String referralSource;

    @Column(name = "expect_appointment_time")
    private String expectAppointmentTime;

    @Column(name = "problem_type")
    private String problemType;

    @Column(name = "expectations_for_consultants")
    private String expectationsForConsultants;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCallRecordNumber() {
        return callRecordNumber;
    }

    public void setCallRecordNumber(String callRecordNumber) {
        this.callRecordNumber = callRecordNumber;
    }

    public String getHangUpTime() {
        return hangUpTime;
    }

    public void setHangUpTime(String hangUpTime) {
        this.hangUpTime = hangUpTime;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getKeyCases() {
        return keyCases;
    }

    public void setKeyCases(String keyCases) {
        this.keyCases = keyCases;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDangerousSituation() {
        return dangerousSituation;
    }

    public void setDangerousSituation(String dangerousSituation) {
        this.dangerousSituation = dangerousSituation;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCaseUrgency() {
        return caseUrgency;
    }

    public void setCaseUrgency(String caseUrgency) {
        this.caseUrgency = caseUrgency;
    }

    public String getCallName() {
        return callName;
    }

    public void setCallName(String callName) {
        this.callName = callName;
    }

    public String getUsedEAP() {
        return usedEAP;
    }

    public void setUsedEAP(String usedEAP) {
        this.usedEAP = usedEAP;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getEapCognitionId() {
        return eapCognitionId;
    }

    public void setEapCognitionId(String eapCognitionId) {
        this.eapCognitionId = eapCognitionId;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getEmployeeBirthday() {
        return employeeBirthday;
    }

    public void setEmployeeBirthday(String employeeBirthday) {
        this.employeeBirthday = employeeBirthday;
    }

    public String getEmployeeJobNumber() {
        return employeeJobNumber;
    }

    public void setEmployeeJobNumber(String employeeJobNumber) {
        this.employeeJobNumber = employeeJobNumber;
    }

    public String getJobPhone() {
        return jobPhone;
    }

    public void setJobPhone(String jobPhone) {
        this.jobPhone = jobPhone;
    }

    public String getClientGender() {
        return clientGender;
    }

    public void setClientGender(String clientGender) {
        this.clientGender = clientGender;
    }

    public String getFamilyPhone() {
        return familyPhone;
    }

    public void setFamilyPhone(String familyPhone) {
        this.familyPhone = familyPhone;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCallerRelations() {
        return callerRelations;
    }

    public void setCallerRelations(String callerRelations) {
        this.callerRelations = callerRelations;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getConsultantRelations() {
        return consultantRelations;
    }

    public void setConsultantRelations(String consultantRelations) {
        this.consultantRelations = consultantRelations;
    }

    public String getEmployeePosition() {
        return employeePosition;
    }

    public void setEmployeePosition(String employeePosition) {
        this.employeePosition = employeePosition;
    }

    public String getClientArea() {
        return clientArea;
    }

    public void setClientArea(String clientArea) {
        this.clientArea = clientArea;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getReferralSource() {
        return referralSource;
    }

    public void setReferralSource(String referralSource) {
        this.referralSource = referralSource;
    }

    public String getExpectAppointmentTime() {
        return expectAppointmentTime;
    }

    public void setExpectAppointmentTime(String expectAppointmentTime) {
        this.expectAppointmentTime = expectAppointmentTime;
    }

    public String getProblemType() {
        return problemType;
    }

    public void setProblemType(String problemType) {
        this.problemType = problemType;
    }

    public String getExpectationsForConsultants() {
        return expectationsForConsultants;
    }

    public void setExpectationsForConsultants(String expectationsForConsultants) {
        this.expectationsForConsultants = expectationsForConsultants;
    }
}
