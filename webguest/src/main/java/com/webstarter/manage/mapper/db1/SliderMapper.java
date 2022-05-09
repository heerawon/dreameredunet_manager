package com.webstarter.manage.mapper.db1;

import com.webstarter.manage.model.BoardModel;
import com.webstarter.manage.model.SliderImagesModel;

import java.util.List;
import java.util.Map;

public interface SliderMapper {
    int insertSlider(Map reqMap);
    int updateSlider(Map reqMap);
    int deleteSlider(Map reqMap);
    List<SliderImagesModel> selectListSlider(Map reqMap);
}
