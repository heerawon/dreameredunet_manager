package com.webstarter.manage.mapper.db1;

import com.webstarter.manage.model.TermModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TermMapper {
    List<TermModel> selectTermList();
    void insertTerm(TermModel term);
    TermModel selectTermDetail(int termId);
    TermModel selectLatestTerm();
//    void updateTerm (TermModel term);
}
