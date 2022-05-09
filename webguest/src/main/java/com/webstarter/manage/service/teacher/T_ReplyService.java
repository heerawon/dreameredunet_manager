package com.webstarter.manage.service.teacher;

import com.webstarter.manage.mapper.db1.teacher.T_QaMapper;
import com.webstarter.manage.mapper.db1.teacher.T_ReplyMapper;
import com.webstarter.manage.model.QaModel;
import com.webstarter.manage.model.ReplyModel;
import com.webstarter.manage.model.ResponseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Log4j2
@Service
public class T_ReplyService {
    @Resource
    private T_ReplyMapper t_replyMapper;

    @Resource
    private T_QaMapper t_qaMapper;

    /**
     * 댓글 개수 반환
     * @param pramId 댓글이 등록된 기준글의 id
     * @param type 0: 과제, 1: QA, 2: 예습복습
     */
    public int getCountReply(Integer pramId, Integer type){
        HashMap<String,Integer> reqMap = new HashMap<>();
        switch (type){
            case 0:
                reqMap.put("fkHomeworkId",pramId);
                break;
            case 1:
                reqMap.put("fkQaId",pramId);
                break;
            case 2:
                reqMap.put("fkPreparationId",pramId);
                break;
        }
        return t_replyMapper.getCountReply(reqMap);
    }

    /**
     * 댓글 목록 중 상대방의 마지막 입력시간 (이 시간 이후 수정할 수 없음)
     * @param fkId foreign key id (homework , qa , preparation)
     */
    public ResponseService<ReplyModel> getLastReply(Integer fkId,Integer type){
        String resMsg = "";
        ReplyModel lastReply = new ReplyModel();
        try{
            HashMap<String,Integer> reqMap = new HashMap<>();
            switch (type){
                case 0:
                    reqMap.put("fkHomeworkId",fkId);
                    break;
                case 1:
                    reqMap.put("fkQaId",fkId);
                    break;
                case 2:
                    reqMap.put("fkPreparationId",fkId);
                    break;
            }
            lastReply = t_replyMapper.getLastReply(reqMap);
        }catch (Exception e){
            resMsg = "DB 업데이트 실패";
        }

        return new ResponseService<ReplyModel>(resMsg, lastReply);
    }

    /**
     * 댓글 목록 반환
     * @param pramId 댓글이 달린 목록 아이디 (fk)
     * @param length 기존 댓글 개수
     */
    public ResponseService<List<ReplyModel>> getReplyList(Integer pramId, Integer type, Integer length){
        String resMsg = "";

        log.info("length >>"+length);
        int setStart =0;
        int setLength=10;
        int start = 0;

        HashMap<String,Integer> hashMap1 = new HashMap<>();
        switch (type){
            case 0:
                hashMap1.put("fkHomeworkId",pramId);
                break;
            case 1:
                hashMap1.put("fkQaId",pramId);
                break;
            case 2:
                hashMap1.put("fkPreparationId",pramId);
                break;
        }
        int countReply = t_replyMapper.getCountReply(hashMap1);

        if(length<countReply){
            if(length>0){ //더보기 요청
                if(countReply>length){
                    start = countReply - length;
                    if(start>10){
                        setStart = start-10;
                    }else{
                        setStart = 0;
                        setLength = start;
                    }
                }
                log.info("더보기");
                log.info("setStart >> "+setStart);
                log.info("setLength >> "+setLength);
                log.info("start >> "+start);
            }else{ //최초
                if(countReply>10) {
                    setStart = countReply - 10;
                }
                setLength = 10;
                log.info("최초");
                log.info("setStart >> "+setStart);
                log.info("setLength >> "+setLength);
                log.info("start >> "+start);
            }
        }else{
            resMsg = "댓글목록을 더 불러올 수 없습니다.";
        }


        List<ReplyModel> replyList = new ArrayList<ReplyModel>();
        try{
            HashMap<String,Integer> hashMap = new HashMap<>();
            switch (type){
                case 0:
                    hashMap.put("fkHomeworkId",pramId);
                    break;
                case 1:
                    hashMap.put("fkQaId",pramId);
                    break;
                case 2:
                    hashMap.put("fkPreparationId",pramId);
                    break;
            }

            hashMap.put("length",setLength);
            hashMap.put("start",setStart);
            replyList = t_replyMapper.getReplyList(hashMap);
        }catch (Exception e){
            resMsg = "댓글 목록 조회 실패";
        }
        return new ResponseService<List<ReplyModel>>(resMsg, replyList);
    }

    /**
     * 댓글 입력
     * @param replyModel
     */
    public ResponseService<String> insertReply(ReplyModel replyModel) throws Exception {
        String resMsg = "";
        try {
            if (t_replyMapper.insertReply(replyModel) < 1) {
                resMsg = "새로운 댓글 등록 실패";
            }
        } catch (Exception e) {
            e.printStackTrace();
            resMsg = "댓글 등록 중 DB에러 발생";
        }
        return new ResponseService<String>(resMsg, "");
    }

    /**
     * 댓글 업데이트
     * @param replyModel
     */
    public ResponseService<String> updateReply(ReplyModel replyModel){
        String resMsg = "";
        try{
            if(t_replyMapper.updateReply(replyModel)<0)
                resMsg = "변경 된 항목이 없습니다.";
        }catch (Exception e){
            resMsg = "DB 업데이트 실패";
        }
        return new ResponseService<String>(resMsg, "");
    }

    /**
     * 댓글 삭제
     * @param replyModel
     */
    public ResponseService<String> deleteReply(ReplyModel replyModel){
        String resMsg = "";
        try{
            if(t_replyMapper.deleteReply(replyModel)<1)
                resMsg = "변경 된 항목이 없습니다.";
        }catch (Exception e){
            e.printStackTrace();
            resMsg = "DB 업데이트 실패";
        }
        return new ResponseService<String>(resMsg, "");
    }

    /**
     * QA 댓글 등록 후 답변/미답변 변경
     */
    @Transactional
    public String insertReplyWithQa(ReplyModel replyModel, QaModel qaModel){
        String resMsg = "";
        try{
            t_replyMapper.insertReply(replyModel);

            if(t_qaMapper.updateQaStatus(qaModel)<0)
                return "QA 변경된 항목이 없습니다.";
        }catch (Exception e){
            return "DB 업데이트 실패";
        }
        return resMsg;
    }

    /**
     * QA 댓글 삭제 후 답변/미답변 변경
     */
    @Transactional
    public String deleteReplyWithQa(ReplyModel replyModel, QaModel qaModel){
        String resMsg = "";
        try{
            if(t_replyMapper.deleteReply(replyModel)<0)
                return "댓글 변경 된 항목이 없습니다.";
            if(t_qaMapper.updateQaStatus(qaModel)<0)
                return "QA 변경된 항목이 없습니다.";
        }catch (Exception e){
            e.printStackTrace();
            return "DB 업데이트 실패";
        }
        return resMsg;
    }

}
