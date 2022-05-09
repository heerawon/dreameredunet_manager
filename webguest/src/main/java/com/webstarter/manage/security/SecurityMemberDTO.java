package com.webstarter.manage.security;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString

@NoArgsConstructor
public class SecurityMemberDTO {

    private String userId;
    private String userName;
    private String email;
    private String userPassword;
    private Role role;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public SecurityMemberEntiry toEntity() {
        return SecurityMemberEntiry.builder()
                .userId(this.userId)
                .userName(this.userName)
                .email(this.email)
                .userPassword(this.userPassword)
                .role(Role.TEACHER)
                .build();
    }

    @Builder
    public SecurityMemberDTO(String userId, String userName, String email, String userPassword, Role role) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.userPassword = userPassword;
        this.role = role;
    }
}