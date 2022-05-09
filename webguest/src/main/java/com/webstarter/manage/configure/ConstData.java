package com.webstarter.manage.configure;

import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.Map;

@Component
public class ConstData {
    //https://www.baeldung.com/java-initialize-hashmap
    final static Map<String, String> mapData = Map.ofEntries(
            new AbstractMap.SimpleEntry<String, String>("", ""),
            new AbstractMap.SimpleEntry<String, String>("insertTeacher_001", "중복 된 아이디가 있습니다."),
            new AbstractMap.SimpleEntry<String, String>("insertTeacher_002", "등록에 실패했습니다."),
            new AbstractMap.SimpleEntry<String, String>("insertBoard_001", "수정에 실패했습니다."),
            new AbstractMap.SimpleEntry<String, String>("insertBoard_002", "등록에 실패했습니다."),
            new AbstractMap.SimpleEntry<String, String>("sliderCommList_001", "올바르지 않은 요청입니다."),
            new AbstractMap.SimpleEntry<String, String>("preparationList_001", "할당 된 강의가 없습니다."),
            new AbstractMap.SimpleEntry<String, String>("TeacherChangePw_001", "비밀번호를 공백없이 8자 이상 입력하세요."),
            new AbstractMap.SimpleEntry<String, String>("sliderCommList_002", "슬라이드 목록 조회 실패"),
            new AbstractMap.SimpleEntry<String, String>("sliderCommList_003", "슬라이드 삭제 실패"),
            new AbstractMap.SimpleEntry<String, String>("deleteReference_001", "강의자료 삭제 실패"),
            new AbstractMap.SimpleEntry<String, String>("referenceInsertOrUpdate_001", "강의자료 등록 및 변경 실패"),
            new AbstractMap.SimpleEntry<String, String>("addSlider_001", "슬라이드 등록 실패"),
            new AbstractMap.SimpleEntry<String, String>("updateStudent_001", "학생 정보 수정 실패")
    );

    public static String getCode(String code){
        String str = "";
        try{
            str = mapData.get(code);
        }catch (NullPointerException e){
            str = "";
        }
        return str;
    }
}
