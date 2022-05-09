package com.webstarter.manage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ListLectureListModel {
    private int lectureId;  //강의 아이디
    private int fkTeamId; // 팀 아이디
    private String regDt;
    private String startDt;
    private String startTime;
    private int homework;
    private String zoomLink;
    private int chapter;
}
