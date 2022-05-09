package com.webstarter.manage.model;

import com.webstarter.manage.security.model.UserModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TeacherModel extends UserModel {
    private String fkUserId;
    private String teacherGender;
    private String teacherBirth;
    private String teacherCell;
    private String teacherAddr;
    private String teacherAddrDetail;
    private String teacherEmail;
    private String teacherProfile;
    private int fkCategoryId;
    private String teacherHistory;
    private String teacherIntroduce;
    private String teacherAssignDt;
    private String teacherIsPresident;
    private int teacherIsMain;
    private int teacherSortNum;

    private String categoryName;
    private String teamId;
    private String teamName;
    private String specialTeamId;
    private String specialTeamName;

    private String teacherCategory;
}
