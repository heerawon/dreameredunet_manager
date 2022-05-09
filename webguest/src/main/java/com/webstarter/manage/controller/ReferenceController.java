package com.webstarter.manage.controller;

import com.webstarter.manage.configure.ConstData;
import com.webstarter.manage.jpaEntity.ReferenceVO;
import com.webstarter.manage.model.BoardModel;
import com.webstarter.manage.model.UploadFile;
import com.webstarter.manage.s3config.S3Uploader;
import com.webstarter.manage.service.ReferenceService;
import com.webstarter.manage.service.ReferenceServiceOld;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;


@Log4j2
@Controller
@RequestMapping("reference")
@RequiredArgsConstructor
public class ReferenceController {
    private final ReferenceService referenceService;
    /**
     * 강의자료 목록
     *
     * @param model
     * @param errCode
     * @return
     */
    @GetMapping("")
    public String referenceList(Model model,
                                @RequestParam(defaultValue = "") String errCode
    ) {
        var responseService = referenceService.selectListReference();
        if (responseService.isSuccess()) {
            model.addAttribute("lsData", responseService.getResObjectData());
        } else {
            model.addAttribute("lsData", new ArrayList<UploadFile>());
            errCode = "referenceList_001";
        }
        model.addAttribute("errMsg", ConstData.getCode(errCode));
        return "ex_function/reference/reference_list";
    }


    /**
     * 강의자료 상세
     * @param model
     * @param id
     * @param errCode
     * @return
     */
    @GetMapping(value = "{id}")
    public String referenceDetail(Model model,
                                  @PathVariable Integer id,
                                @RequestParam(defaultValue = "") String errCode
    ) {
        var responseService = referenceService.selectDetailReference(id.longValue());
        if (responseService.isSuccess()) {
        } else {
        }
        model.addAttribute("data", responseService.getResObjectData());
        return "ex_function/reference/reference_detail";
    }

    /**
     * 강의자료 추가
     *
     * @param model
     * @return
     */
    @GetMapping("register")
    public String referenceRegister(Model model,
                                    @RequestParam(value = "id", defaultValue = "-1") Integer id,
                                    @RequestParam(value = "type", defaultValue = "") String type,
                                    ReferenceVO referenceVO
    ) {
        if ("edit".equals(type)) {
            var responseService = referenceService.selectDetailReference(id.longValue());
            model.addAttribute("edit", 1);
            model.addAttribute("referenceVO", responseService.isSuccess() ? responseService.getResObjectData() : ReferenceVO.builder().build());
        } else {
            model.addAttribute("edit", 0);
            model.addAttribute("referenceVO", ReferenceVO.builder().build());
        }
        return "ex_function/reference/reference_register";
    }



    @PostMapping("insertUpdate")
    public String referenceInsertOrUpdate(@ModelAttribute ReferenceVO referenceVO,
                                          @RequestParam(value = "edit", defaultValue = "") Integer edit) {
        log.info("referenceVO::::::" + referenceVO.toString());
        var resService = referenceService.insertReference(referenceVO);
        if(resService.isSuccess()){
            return "redirect:/reference/"+resService.getResObjectData().getId();
        }else{
            return "redirect:/reference?errCode=referenceInsertOrUpdate_001";
        }
    }



    @GetMapping("delete")
    public String deleteReference(@RequestParam(value = "id", defaultValue = "-1") int id
    ) {
        var responseService = referenceService.deleteReference(id);
        if (responseService.isSuccess()) {
            return "redirect:/reference";
        } else {
            return "redirect:/reference?errCode=deleteReference_001";
        }
    }


}
