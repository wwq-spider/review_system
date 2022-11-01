package com.review.front.vo;

import java.io.Serializable;

/**
 * @author javabage
 * @date 2022/7/1
 */
public class TecentMeetingVO implements Serializable {

    private String startTime;
    private String endTime;
    private String expertName;
    private String visitDate;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getExpertName() {
        return expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }
}
