package com.webstarter.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CelebrityLectureModel extends StudentModel {
    Integer celebLectureId;
    String celebLectureName;
    String fkSuserId;
    Integer celebLectureStatus; // -1:수업종료(다시 신청 가능 상태) 0:신청 1:승인완료
    String regDt;
    String allowDt;
}

