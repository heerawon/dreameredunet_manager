package com.webstarter.manage.service.teacher;

import com.webstarter.manage.mapper.db1.teacher.T_PreparationMapper;
import com.webstarter.manage.model.BoardModel;
import com.webstarter.manage.model.PreparationModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
@Log4j2
@Service
public class T_PreparationService {
    @Resource
    private T_PreparationMapper t_preparationMapper;

    public int selectPreparationCount(HashMap hashMap){
        return t_preparationMapper.selectPreparationCount(hashMap);
    }

    public List<PreparationModel> getPreparationList(HashMap hashMap){
        return t_preparationMapper.selectPreparationList(hashMap);
    }

    public PreparationModel getPreparationDetail(Integer preparationId){
        PreparationModel preparationModel = t_preparationMapper.selectPreparationDetail(preparationId);
        return preparationModel;
    }

    public int insertPreparation(PreparationModel preparationModel) throws Exception {
        try{
            this.t_preparationMapper.insertPreparation(preparationModel);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("insertBoard Err");
        }
        return preparationModel.getPreparationId();
    }

    public String updatePreparation(PreparationModel preparationModel)throws  Exception{
        String resMsg = "";
        try{
            if(t_preparationMapper.updatePreparation(preparationModel)<0)
                return "변경 된 항목이 없습니다.";
        }catch (Exception e){
            return "DB 업데이트 실패";
        }
        return resMsg;
    }

    public String deletePreparation(Integer preparationId){
        String resMsg = "";
        try{
            if(t_preparationMapper.deletePreparation(preparationId)<0)
                return "변경 된 항목이 없습니다.";
        }catch (Exception e){
            e.printStackTrace();
            return "DB 업데이트 실패";
        }
        log.info("resMsg :::::::"+resMsg);
        return resMsg;
    }

    public PreparationModel getExposurePreparationId(PreparationModel preparationModel){
        return t_preparationMapper.getExposurePreparationId(preparationModel);
    }

    public String exposurePreparation(PreparationModel preparationModel){
        String resMsg = "";
        try{
            if(t_preparationMapper.exposurePreparation(preparationModel)<0)
                return "변경 된 항목이 없습니다.";
        }catch (Exception e){
            e.printStackTrace();
            return "DB 업데이트 실패";
        }
        log.info("resMsg :::::::"+resMsg);
        return resMsg;
    }
}
