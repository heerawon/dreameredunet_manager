package com.webstarter.manage.service;

import com.webstarter.manage.mapper.db1.MainMapper;
import com.webstarter.manage.model.MainUserModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("MainService")
public class MainServiceImp implements MainService {
//    @Autowired
//    MainMapper mainMapper;

    @Resource(name = "mainMapper")
    MainMapper mainMapper;

    @Override
    public String getDataMap(Map<String, Object> reqMap) {
        String resStr = "";
        resStr = mainMapper.isLoginHashMap(reqMap);
        return resStr;
    }

    @Override
    public String getDataString(String reqStr) {
        String resStr = "";
        resStr = mainMapper.isLoginString(reqStr);
        return resStr;
    }

    @Override
    public String getDataModel(MainUserModel reqModel) {
        String resStr = "";
        resStr = mainMapper.isLoginModel(reqModel);
        return resStr;
    }

    @Override
    public List<MainUserModel> getDataUserList() {
        List<MainUserModel> resList = new ArrayList<MainUserModel>();
        resList = mainMapper.getUserList();
        return resList;
    }
}
