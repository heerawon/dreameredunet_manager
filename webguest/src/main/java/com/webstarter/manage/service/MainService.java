package com.webstarter.manage.service;

import com.webstarter.manage.model.MainUserModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface MainService {
    String getDataMap(Map<String,Object> reqMap);
    String getDataString(String reqStr);
    String getDataModel(MainUserModel reqModel);
    List<MainUserModel> getDataUserList();


}
