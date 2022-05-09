package com.webstarter.manage.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *  userPkId PK, AI
 *  userName VARCHAR(200)
 */

@Getter
@Setter
@ToString
public class MainUserModel {
    private String userPkId;
    private String userName;
}



/**
create table MAIN_USER
(
    user_pk_id int auto_increment
    primary key,
    user_name  varchar(200) null comment '유저 이름'
);
 */