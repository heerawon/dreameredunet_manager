package com.webstarter.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardModel {
    private int boardId;
    private String boardTitle;
    private String boardContent;
    private String boardImg;
    private String boardUpdated;

    private String fkUserId;
}
