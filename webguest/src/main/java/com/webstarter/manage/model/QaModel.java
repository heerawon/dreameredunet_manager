package com.webstarter.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QaModel {
    private Integer qaId;
    private Integer fkLectureId;
    private String fkSuserId;
    private String regDt;
    private Integer onlyMe;
    private Integer qaStatus;
    private String updateDt;
    private String title;
    private String content;
    private Integer isDel;

    private String userName; //학생
    private Integer teamId;
    private String teamName;
    private String startDt;
    private String startTime;

    private String teacherId; //선생
    private Integer countReply;
}
