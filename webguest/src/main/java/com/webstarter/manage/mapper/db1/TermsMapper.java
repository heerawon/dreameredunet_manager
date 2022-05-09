package com.webstarter.manage.mapper.db1;

import com.webstarter.manage.model.TermModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TermsMapper {

    String getTerm(String type);  // type : private  개인정보 type : term 이용약관
    int updateTerm(Map<String, Object> reqMap); //
}
