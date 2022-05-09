package com.webstarter.manage.model;

import lombok.ToString;

import java.util.List;
@ToString

public class LectureAddForm {
    private List<LectureListModel> lectures;

    public List<LectureListModel> getLectures() {
        return lectures;
    }
    public void setLectures(List<LectureListModel> lectures) {
        this.lectures = lectures;
    }
}
