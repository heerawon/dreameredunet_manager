package com.webstarter.manage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class LectureListModel {
    private String lectureId;
    private Integer fkTeamId;
    private String startDt;
    private String startTime;
    private Integer homework;
    private String zoomLink;
    private Integer chapter;
}
