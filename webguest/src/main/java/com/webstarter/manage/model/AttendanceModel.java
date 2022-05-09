package com.webstarter.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttendanceModel extends TeamModel{
    private Integer attendanceId;
    private String fkSuserId;
    private Integer fkLectureId;
    private String memo;
    private Integer isAttend;
    private String attendDt;
    private String regDt;
    private Integer countAttend;
    private Integer percentAttend;

    private String lectureId;
    private String startDt;
    private String startTime;
    private String homework; // 과제제출 필요 여부
    private String zoomLink;
    private String chapter; //회차
}
