package com.webstarter.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TeamModel extends ProgramModel{
    private int teamId;
    private int fkProgramId;
    private String teamName;
    private String type;
    private String regDt;
    private String fkUserId;
    private String fkTUserId;
    private Integer totalLecture;
    private String startDt;
    private String endDt;
    private int teamStatus;
    private int division;
    private String introduce;
    private String link;

    private String userName;
    private String teacherProfile;

    private int countLecture;
    private int countTeamMember;

    private Integer countMember;

    private Integer fkCategoryId;
    private Integer teamCategoryId;
    private String categoryName;
    private Integer progress; // 0: 진행중 , 1 : 완료

    private String fullEndDayTime; //화면에서 날짜비교를 위해 endDt + startTime 합쳐 데이트 형식으로 반환하는 컬럼
    private String fullTime; //화면에서 날짜비교를 위해 endDt + startTime 합쳐 데이트 형식으로 반환하는 컬럼

    private Integer homeworkId;
    private String fkSuserId;
    private String studentMbti;
    private String studentBirth;
    private String withdrawal;
    private Integer attendanceId;
    private Integer isAttend;
    private String memo;
    private String attendDt;

    private String title;
    private String title2;

    private String userRole;
}
