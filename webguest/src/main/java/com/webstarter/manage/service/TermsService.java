package com.webstarter.manage.service;

import com.webstarter.manage.mapper.db1.TermMapper;
import com.webstarter.manage.mapper.db1.TermsMapper;
import com.webstarter.manage.model.TermModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class TermsService {
    @Resource
    private TermsMapper termsMapper;

    public String getTerm(String type){
        return termsMapper.getTerm(type);
    }


    public boolean updateTerm(HashMap reqMap){
        try{

            if(termsMapper.updateTerm(reqMap)>0){
                return true;
            }
            return false;
        }catch (Exception e){
//            e.printStackTrace();
        }
        return false;
    }
}
