package com.webstarter.manage.mapper.db1.teacher;

import com.webstarter.manage.model.QaModel;

import java.util.List;

public interface T_QaMapper {
    List<QaModel> getAllQaByTeacherLimit(QaModel qaModel);
    List<QaModel> getAllQaByTeacher(QaModel qaModel);
    QaModel getQaDetail(Integer qaId);
    int updateQaStatus(QaModel qaModel);
}
