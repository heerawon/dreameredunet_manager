package com.webstarter.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ApplyCourseModel extends StudentModel{
    private Integer applyCourseId;
    private Integer fkCategoryId;
    private Integer fkTeamId;
    private String fkSuserId;
    private String regDt;
    private String allowDt;

    private Integer teamCategoryId;
    private String categoryName;
    private Integer waitingDt;
}
