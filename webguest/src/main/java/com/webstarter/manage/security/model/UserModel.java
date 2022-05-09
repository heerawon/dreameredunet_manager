package com.webstarter.manage.security.model;

import com.webstarter.manage.security.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserModel {

    private String userId;
    private String userPassword;
    private String userName;
    private String userRegDt;
    private int userActive;
//    private Role userRole;

    private Role role;
}
