package com.webstarter.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReplyModel {
    private Integer replyId;
    private Integer type; // 0:homework 1:qa 2:preparation
    private Integer fkHomeworkId;
    private Integer fkQaId;
    private Integer fkPreparationId;
    private String fkSuserId;
    private String fkTuserId;
    private String content;
    private String regDt;
    private String regDt2;
    private Integer isDel;

    private String teacherProfile;
    private String studentName;
    private String studentMbti;
    private String teacherName;

    private Integer length;
    private Integer start;

    //예습 복습 페이지 반환 때문에 추가
    private Integer teamId;
    private Integer division;
}
