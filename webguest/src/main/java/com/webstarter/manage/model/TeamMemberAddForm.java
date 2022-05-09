package com.webstarter.manage.model;

import lombok.ToString;

import java.util.List;

@ToString
public class TeamMemberAddForm {
    private List<TeamMemberModel> members;

    public List<TeamMemberModel> getMembers() { return  members;}
    public void setMembers(List<TeamMemberModel> members) { this.members = members; }

}
