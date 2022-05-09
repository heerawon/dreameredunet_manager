package com.webstarter.manage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ListTeamMemberModel {
    private Integer fkTeamId;
    private String fkSuserId;
    private Integer isDel;
    private String regDt;
}
