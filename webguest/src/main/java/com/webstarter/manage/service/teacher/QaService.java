package com.webstarter.manage.service.teacher;

import com.webstarter.manage.mapper.db1.teacher.T_QaMapper;
import com.webstarter.manage.model.QaModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class QaService {
    @Resource
    private T_QaMapper t_qaMapper;

    public List<QaModel> getAllQaByTeacherLimit(QaModel qaModel){
        return t_qaMapper.getAllQaByTeacherLimit(qaModel);
    }

    public List<QaModel> getAllQaByTeacher(QaModel qaModel){
        return t_qaMapper.getAllQaByTeacher(qaModel);
    }

    public QaModel getQaDetail(Integer qaId){
        return t_qaMapper.getQaDetail(qaId);
    }

    public String updateQaStatus (QaModel qaModel){
        String resMsg = "";
        try{
            if(t_qaMapper.updateQaStatus(qaModel)<0)
                return "변경 된 항목이 없습니다.";
        }catch (Exception e){
            e.printStackTrace();
            return "DB 업데이트 실패";
        }
        return resMsg;
    }
}
