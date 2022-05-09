package com.webstarter.manage.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LectureModel extends TeamModel {
    private String lectureId;
    private String startDt;
    private String startTime;
    private String homework; // 과제제출 필요 여부
    private String zoomLink;
    private String chapter; //회차
    private String userName;
}
