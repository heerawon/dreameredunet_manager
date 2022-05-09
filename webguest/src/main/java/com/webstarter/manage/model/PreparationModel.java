package com.webstarter.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PreparationModel extends LectureListModel{
    private Integer preparationId;
    private Integer fkTeamId;
    private Integer appearance; //노출여부 (0:노출안함/ 1:노출)
    private Integer division; //구분(0:복습/1:예습)
    private String link; //강의링크(유투브)
    private String content;
    private String regDt;
    private Integer isDel;

    private String teacherId;
    private Integer countReply; //댓글 개수
}
