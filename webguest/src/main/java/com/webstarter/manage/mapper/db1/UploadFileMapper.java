package com.webstarter.manage.mapper.db1;

import com.webstarter.manage.model.SliderImagesModel;
import com.webstarter.manage.model.UploadFile;

import java.util.List;
import java.util.Map;

public interface UploadFileMapper {
    int insertFile(UploadFile uploadFile);
    int updateFile(UploadFile uploadFile);
    int deleteFile(Integer id);
    List<UploadFile> selectListFiles();
}
