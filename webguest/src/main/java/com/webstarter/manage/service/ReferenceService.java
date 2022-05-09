package com.webstarter.manage.service;

import com.webstarter.manage.jpaEntity.ReferenceVO;
import com.webstarter.manage.jpaEntity.ReferenceListVO;
import com.webstarter.manage.jpaRepository.ReferenceRepository;
import com.webstarter.manage.model.ResponseService;
import com.webstarter.manage.model.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class ReferenceService {
    private final ReferenceRepository referenceRepository;

    /**
     * 강의자료 등록
     * @return
     */
    public ResponseService<ReferenceVO> insertReference(ReferenceVO referenceVO){
        String resMsg = "";
        try {
            ReferenceVO finalReferenceVO = referenceVO;
            var res = referenceRepository.findByIdAndIsDel(referenceVO.getId(),0L)
                    .map(entity -> entity.update(finalReferenceVO.getTitle(), finalReferenceVO.getContent()))
                    .orElse(referenceVO.toEntity());
            referenceVO = referenceRepository.saveAndFlush(res);
        }
        catch (Exception e) {
            log.info("insertReference:::::" + e.getMessage());
            resMsg = "강의자료 등록 중 DB에러 발생";
        }
        return new ResponseService<ReferenceVO>(resMsg, referenceVO);
    }



    /**
     * 강의자료 삭제
     * @param id
     * @return
     */
    public ResponseService<String> deleteReference(Integer id){
        String resMsg = "";
        try {
            var res = referenceRepository.findByIdAndIsDel(id.longValue(),0L)
                    .map(entity -> entity.updateIsDel(1L)).orElseThrow();
            var resRepository = referenceRepository.saveAndFlush(res);

        }
        catch (Exception e) {
            resMsg = "파일 삭제 중 DB에러 발생";
        }
        return new ResponseService<String>(resMsg, "");
    }

    /**
     *  강의자료 목록
     * @return
     */
    public ResponseService<List<ReferenceListVO>> selectListReference(){
        String resMsg = "";
        List<ReferenceListVO> referenceVOList = new ArrayList<>();
        try {
            referenceVOList = referenceRepository.findByIsDel(0L,Sort.by(Sort.Direction.DESC,"id"));
        }
        catch (Exception e) {
            resMsg = "강의 자료 목록 조회 중 DB에러 발생";
        }
        return new ResponseService<List<ReferenceListVO>>(resMsg, referenceVOList);
    }


    /**
     *  강의자료 상세
     * @return
     */
    public ResponseService<ReferenceVO> selectDetailReference(Long bbsId){
        String resMsg = "";
        ReferenceVO referenceVO = ReferenceVO.builder().build();
        try {
            referenceVO = referenceRepository.findByIdAndIsDel(bbsId,0L).orElse(ReferenceVO.builder().build());
        }
        catch (Exception e) {
            resMsg = "강의 자료 목록 조회 중 DB에러 발생";
        }
        return new ResponseService<ReferenceVO>(resMsg, referenceVO);
    }


}
