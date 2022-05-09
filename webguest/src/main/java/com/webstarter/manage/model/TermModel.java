package com.webstarter.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TermModel {
    private int termId;
    private String termType;
    private String termName;
    private String termContent;
    private String termAgreeContent;
    private String termRegDt;
    private String termEffectiveDt;
    private int termActive;
}
