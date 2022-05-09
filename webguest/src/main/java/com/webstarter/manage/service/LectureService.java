package com.webstarter.manage.service;

import com.webstarter.manage.mapper.db1.LectureMapper;
import com.webstarter.manage.mapper.db1.TeamMapper;
import com.webstarter.manage.model.LectureListModel;
import com.webstarter.manage.model.LectureModel;
import com.webstarter.manage.model.ResponseService;
import com.webstarter.manage.model.TeamModel;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class LectureService {
    @Resource
    private LectureMapper lectureMapper;

    @Resource
    private TeamMapper teamMapper;

    public List<LectureModel> selectLectureList(Integer fkTeamId) {
        return lectureMapper.selectLectureList(fkTeamId);
    }

    public String insertLecture(HashMap reqMap) {
        int res = 0;
        try {
            res = lectureMapper.insertLecture(reqMap);
            if (res < 1) {
                return "강의 추가에 실패했습니다.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "강의 추가중 오류가 발생했습니다.";
        }
        return "";//성공
    }

    public LectureModel selectNearestLecture (Integer fkTeamId){
        return lectureMapper.selectNearestLecture(fkTeamId);
    }


    /**
     * 강의에 대한 정보를 조회합니다.
     * @param lectureId 필수
     * @param fkTeacherId 필수 : 해당 선생님에 대해서만 정보를 가져옵니다.
     */
    public ResponseService<LectureModel> getLectureDetail(Integer lectureId,String fkTeacherId) {
        String resMsg="";
        Map reqMap = new HashMap<String,Object>();

        LectureModel lectureModel = new LectureModel();
        try{
            reqMap.put("lectureId",lectureId);
            reqMap.put("fkTeacherId",fkTeacherId);
            lectureModel = lectureMapper.getLectureDetail(reqMap);
        }
        catch (Exception e){
            resMsg = "선생님에게 할당 된 강의인지 확인이 필요합니다.";
        }
        return new ResponseService<LectureModel>(resMsg, lectureModel);
    }

    public String updateLecture(LectureListModel lectureModel) {
        String resMsg = "";
        try {
             lectureMapper.updateLecture(lectureModel);
        } catch (Exception e) {
            resMsg = e.getMessage();
        }
        return resMsg;
    }

    /**
     * 강의 일정 수정 (단건)
     * @param lectureModel 업데이트 할 강의 모델
     */
    public ResponseService<String> updateOneLecture(LectureListModel lectureModel) {
        String resMsg = "";
        try {
            lectureMapper.updateLecture(lectureModel);
        } catch (Exception e) {
            e.printStackTrace();
            resMsg = e.getMessage();
        }
        return new ResponseService<String>(resMsg, "");
    }

    /**
     * 강의 단건 추가 ( 강의 추가 후 강의 기간 수정 )
     * @param lectureListModel 강의 정보 모델
     */
    @Transactional
    public ResponseService<String> insertOneLecture(LectureListModel lectureListModel) {
        String resMsg = "";
        String newLectureId = "-1";
        try {
            if(lectureMapper.insertOneLecture(lectureListModel)<1){
                resMsg = "강의 추가에 실패했습니다.";
            }else{
                newLectureId = lectureListModel.getLectureId();
                log.error("newLectureId"+newLectureId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resMsg = "강의 추가중 DB오류가 발생했습니다.";
        }

        return new ResponseService<String>(resMsg, newLectureId);
    }

    /**
     * 강의 삭제 (단건)
     * @param lectureId 삭제할 강의 아이디
     */
    public ResponseService<String> deleteLecture(Integer lectureId,Integer fkTeamId) {
        String resMsg = "";
        try {
            if(lectureMapper.deleteLecture(lectureId)<1){
                resMsg = "강의 삭제에 실패했습니다.";
            }
        } catch (Exception e) {
            resMsg = "강의 삭제중 DB오류가 발생했습니다.";
        }
        return new ResponseService<String>(resMsg, "");
    }

    /**
     * 팀의 일정 목록 (강의 상세 화면)
     * @param teamId 팀 아이디
     * @param searchMonth 기준 달 (default current date)
     * @param searchYear 기준 연 (default current date)
     */
    public ResponseService<List<LectureModel>> getLectureListByTeam(Integer teamId, String searchMonth, String searchYear){
        String resMsg = "";
        List<LectureModel> lectureListByTeam = new ArrayList<LectureModel>();

        HashMap<String,Object> reqMap = new HashMap<>();
        reqMap.put("teamId",teamId);
        reqMap.put("searchMonth",searchMonth);
        reqMap.put("searchYear",searchYear);

        try {
            lectureListByTeam = lectureMapper.getLectureListByTeam(reqMap);
        }catch (Exception e){
            resMsg = "수강 중인 팀 목록 조회 실패";
        }

        return new ResponseService<List<LectureModel>>(resMsg, lectureListByTeam);
    }

    /**
     * 강의 chapter 순서 변경
     * @param fkTeamId 변경이 일어난 강의 id
     */
    public ResponseService<String> setLectureChapter(Integer fkTeamId){
        String resMsg = "";

        try {
            if(lectureMapper.setLectureChapter(fkTeamId)<1){
                resMsg = "강의 chapter 순서 변경 실패";
            }
        }catch (Exception e){
            resMsg = "강의 chapter 순서 변경중 DB 에러 발생";
        }

        return new ResponseService<String>(resMsg, "");
    }

    /**
     * 팀 정보 및 강의 챕터 최신화
     * @param fkTeamId 팀 아이디
     */
    @Transactional(rollbackOn = Exception.class)
    public ResponseService<String> updateTeamInfoLatest (Integer fkTeamId){
        String resMsg = "";

        try {
            //1. 해당 팀의 강의 정렬 (강의 챕터 업데이트 하기)
            lectureMapper.setLectureChapter(fkTeamId);

            //2. 정렬 된 강의들의 시작, 종료일 (처음, 마지막) , 총 강의 수 세팅
            List<LectureModel> lectureList = lectureMapper.selectLectureList(fkTeamId);
            TeamModel reqTeamInfo = new TeamModel();
            reqTeamInfo.setTeamId(fkTeamId);
            reqTeamInfo.setStartDt(lectureList.get(0).getStartDt());
            reqTeamInfo.setEndDt(lectureList.get(lectureList.size()-1).getStartDt());
            reqTeamInfo.setTotalLecture(lectureList.size());

            //3. 팀 기초정보 업데이트
            teamMapper.updateTeamLectureSchedule(reqTeamInfo);

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("강의 chapter 순서 변경중 DB 에러 발생");
        }

        return new ResponseService<String>(resMsg, "");
    }

    /**
     * 전체 강의 회차 수
     * @param teamId 강의 팀 아이디
     */
    public ResponseService<Integer> getAllLectureCount(Integer teamId){
        String resMsg = "";
        int resCount =-1;
        HashMap<String,Object> reqMap = new HashMap<>();
        reqMap.put("teamId",teamId);
        try {
            resCount = lectureMapper.getAllLectureCount(reqMap);
        }catch (Exception e){

        }
        return new ResponseService<Integer>(resMsg, resCount);
    }
}
