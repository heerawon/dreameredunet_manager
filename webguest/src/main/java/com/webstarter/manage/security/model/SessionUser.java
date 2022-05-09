package com.webstarter.manage.security.model;


import com.webstarter.manage.security.Role;
import com.webstarter.manage.security.SecurityMemberEntiry;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


//https://sas-study.tistory.com/302
@Getter
@ToString
@Setter
public class SessionUser implements Serializable {
    private String userId;
    private String name;
    private Role role;

    public SessionUser(SecurityMemberEntiry user) {
        this.userId = user.getUserId();
        this.name = user.getUserName();
        this.role = user.getRole();
    }
}