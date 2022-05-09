package com.webstarter.manage.mapper.db1.teacher;

import com.webstarter.manage.model.PreparationModel;

import java.util.HashMap;
import java.util.List;

public interface T_PreparationMapper {
    int selectPreparationCount(HashMap hashMap);
    List<PreparationModel> selectPreparationList(HashMap hashMap);
    int insertPreparation(PreparationModel preparationModel);
    PreparationModel selectPreparationDetail(Integer preparationId);
    int updatePreparation(PreparationModel preparationModel);
    int deletePreparation(Integer preparationId);
    PreparationModel getExposurePreparationId(PreparationModel preparationModel);
    int exposurePreparation(PreparationModel preparationModel);
}
