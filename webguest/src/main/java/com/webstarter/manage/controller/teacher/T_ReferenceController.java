package com.webstarter.manage.controller.teacher;

import com.webstarter.manage.configure.ConstData;
import com.webstarter.manage.model.UploadFile;
import com.webstarter.manage.service.ReferenceService;
import com.webstarter.manage.service.ReferenceServiceOld;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Log4j2
@Controller
@RequestMapping("t_reference")
@RequiredArgsConstructor
public class T_ReferenceController {
    private final ReferenceService referenceService;


    //    @GetMapping("")
//    public String teacherReferenceList(Model model,
//                                       @RequestParam(defaultValue = "") String errCode
//    ) {
//        var responseService = referenceServiceOld.selectListReference();
//        if (responseService.isSuccess()) {
//            model.addAttribute("lsData", responseService.getResObjectData());
//        } else {
//            model.addAttribute("lsData", new ArrayList<UploadFile>());
//            errCode = "referenceList_001";
//        }
//        model.addAttribute("errMsg", ConstData.getCode(errCode));
//
//        return "teacher/reference/teacher_reference_list";
//    }


    /**
     * 강의자료 목록
     * @param model
     * @param errCode
     * @return
     */
    @GetMapping("")
    public String teacherReferenceList(Model model,
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
        return "teacher/reference/teacher_reference_list";
    }


    /**
     * 강의자료 상세
     *
     * @param model
     * @param id
     * @param errCode
     * @return
     */
    @GetMapping(value = "{id}")
    public String teacherReferenceDetail(Model model,
                                  @PathVariable Integer id,
                                  @RequestParam(defaultValue = "") String errCode
    ) {
        var responseService = referenceService.selectDetailReference(id.longValue());
        if (responseService.isSuccess()) {
        } else {
        }
        model.addAttribute("data", responseService.getResObjectData());
        return "teacher/reference/teacher_reference_detail";
    }


}
