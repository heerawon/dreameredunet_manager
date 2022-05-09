package com.webstarter.manage.service;

import com.webstarter.manage.mapper.db1.FeeMapper;
import com.webstarter.manage.model.FeeModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FeeService {
    @Resource
    private FeeMapper feeMapper;

    private List<FeeModel> selectFeeList(){
        return feeMapper.selectFeeList();
    }

    public String insertFee(FeeModel feeModel){
        String resMsg = "";
        try {
            feeMapper.insertFee(feeModel);
        }catch (Exception e){
            resMsg = e.getMessage();
        }
        return resMsg;
    }
}
