package com.webstarter.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProgramModel {
    private int programId;
    private String programName;
    private int programStartAge;
    private int programEndAge;
}
