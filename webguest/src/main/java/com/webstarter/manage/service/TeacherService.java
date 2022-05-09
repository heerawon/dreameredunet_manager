package com.webstarter.manage.service;

import com.webstarter.manage.mapper.db1.TeacherMapper;
import com.webstarter.manage.model.CategoryModel;
import com.webstarter.manage.model.ResponseService;
import com.webstarter.manage.model.TeacherModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Log4j2
@Service
public class TeacherService {
    @Resource
    private TeacherMapper teacherMapper;

    public List<CategoryModel> getCategoryList() {
        return teacherMapper.getCategoryList();
    }

    public int changeIsMain(TeacherModel teacherModel) {
        return teacherMapper.changeIsMain(teacherModel);
    }

    public List<TeacherModel> getTeacherList() {
        return teacherMapper.getTeacherList();
    }

    public List<TeacherModel> getTeacherTeamList() {
        return teacherMapper.getTeacherTeamList();
    }

    public TeacherModel getTeacherDetail(String fkUserId) {
        TeacherModel teacher = teacherMapper.getTeacherDetail(fkUserId);
        return teacher;
    }

    public void insertTeacher(TeacherModel teacherModel) {
        this.teacherMapper.insertTeacher(teacherModel);
    }

    public String updateTeacher(TeacherModel teacherModel) {
        String resMsg = "";
        Map<String, Object> reqMap = new HashMap<String, Object>();
        try {
            reqMap.put("userId", teacherModel.getUserId());
            reqMap.put("userName", teacherModel.getUserName());
            teacherMapper.updateTeacherUserInfo(reqMap);
            int result = teacherMapper.updateTeacher(teacherModel);
        } catch (Exception e) {
            resMsg = e.getMessage();
        }
        return resMsg;
    }

    /**
     * 아이탬 이동 최상단, 최하단
     * @param isUp
     * @return
     */
    public ResponseService<String>moveItemBothEnds(String[] fkUserId,boolean isUp){
        String errMsg ="";
        Map<String, Object> reqMap = new HashMap<String, Object>();
        log.info("moveItemBothEnds  teacherId up:::::"+fkUserId[0]);
        log.info("moveItemBothEnds  teacherId up length:::::"+fkUserId.length);

        try {
            reqMap.put("teacherId", fkUserId);
            if(isUp){
                log.info("teacherId up:::::",fkUserId[0]);
                teacherMapper.moveItemTop(reqMap);
            }else{
                log.info("teacherId down:::::",fkUserId[0]);
                teacherMapper.moveItemBottom(reqMap);
            }
        } catch (Exception e) {
            log.info("moveItemBothEnds ::::::" + e.getMessage());
            errMsg = "변경 실패 ";
        }
        return new ResponseService<String>(errMsg,"");
    }


    public ResponseService<String>moveItem(String teacherId,boolean isUp){
        String errMsg ="";
        Map<String, Object> reqMap = new HashMap<String, Object>();
        try {
            reqMap.put("teacherId", teacherId);
            if(isUp){
                teacherMapper.moveItemUp(reqMap);
            }else{
                teacherMapper.moveItemDown(reqMap);
            }
        } catch (Exception e) {
            errMsg = "변경 실패 ";
        }
        return new ResponseService<String>(errMsg,"");
    }

    /**
     * 강사 Rank 순서대로 정렬
     * @return
     */
    public int updateItemSorting() {
        return teacherMapper.updateItemSortNum();
    }



}
