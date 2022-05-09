package com.webstarter.manage.security;

import lombok.*;

import javax.persistence.*;


@Getter
@Entity(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class SecurityMemberEntiry extends BaseTimeEntity {

    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private String userId;

    @Column
    private String email;

    @Column
    private String userName;

    @Column(unique = true, length = 300)
    private String userPassword;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public SecurityMemberEntiry(String userId, String email, String userName, String userPassword, Role role) {
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.userPassword = userPassword;
        this.role = role;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public SecurityMemberEntiry update(String name, String email) {
        this.userName = userName;
        this.email = email;
        return this;
    }


}
