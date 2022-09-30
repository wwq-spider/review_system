package com.review.front.entity;

import lombok.Data;

import java.util.List;

/**
 * @author javabage
 * @date 2022/8/30
 */
@Data
public class DongliangTestQuestionVO {

    private Integer type;

    private Integer gaugeNum;
    /**
     * 测评码
     */
    private String testCode;
    /**
     * 测评人信息
     */
    private UserInfo userInfo;
    /**
     * 答题信息
     */
    private List<TestRecord> testRecord;
    /**
     * 测评报告地址
     */
    private String reportUrl;
    /**
     * 测评id
     */
    private String classId;
    /**
     * 项目id
     */
    private Long projectId;

}
