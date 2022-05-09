package com.webstarter.manage.security.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserRoleModel {
    private String fkUserId;
    private String role;
}
