package com.webstarter.manage.service;

import com.webstarter.manage.mapper.db1.TermMapper;
import com.webstarter.manage.model.TermModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TermService {
    @Resource
    private TermMapper termMapper;

    public List<TermModel> getTermList(){
        return termMapper.selectTermList();
    }

    public TermModel getTermDetail(Integer termId){
        TermModel term = termMapper.selectTermDetail(termId);
        return term;
    }

    public TermModel selectLatestTerm(){
        TermModel term = termMapper.selectLatestTerm();
        return term;
    }

    public int insertTerm(TermModel termModel){
        this.termMapper.insertTerm(termModel);
        return termModel.getTermId();
    }
}
