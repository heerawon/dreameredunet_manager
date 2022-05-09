package com.webstarter.manage.model;

import com.webstarter.manage.security.model.UserModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@ToString
public class StudentModel extends UserModel {
    private String fkUserId;
    private String fkSUserId;
    private String studentGender;
    private String studentBirth;
    private String studentEmail;
    private String studentCell;
    private String studentSubscribe;
    private String studentMbti;
    private String studentAddress;
    private String studentOverFourteen;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expiredDt;

    private Integer cntExpiredDt;
    private Integer studentAge;

    private String teamName;
    private String teamName2;

    private String parentName;
    private String parentCell;
    private Integer attendanceId;
    private String attendDt;
    private Integer countAttend;
    private Integer countLecture;
    private Integer percentAttend;

    private Integer isDel;
    private Integer subscribeStatus;

    private String userRole;
    private String userSnsType;
    private String email;

    private Integer cntOrder;
    private Integer sumTotalPrice;
}
