package com.webstarter.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HomeworkModel {
    private Integer homeworkId;
    private Integer fkLectureId;
    private String fkSuserId; //학생
    private String regDt;
    private String updateDt;
    private String title;
    private String content;
    private Integer isDel;
    private Integer fkFileId;

    private String userName; //학생
    private Integer teamId;
    private String teamName;
    private String startDt;
    private String startTime;

    private String fileUrl;
    private String fileOriginName;

    private String teacherId; //선생
    private Integer countReply;
}
