package com.webstarter.manage.security;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum Role {
    ADMIN("ROLE_ADMIN", "관리자"),
    TEACHER("ROLE_TEACHER", "선생님");

    private String key;
    private String title;
}