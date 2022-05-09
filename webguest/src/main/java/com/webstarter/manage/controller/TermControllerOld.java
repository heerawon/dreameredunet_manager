package com.webstarter.manage.controller;

import com.webstarter.manage.model.BoardModel;
import com.webstarter.manage.model.TeacherModel;
import com.webstarter.manage.model.TermModel;
import com.webstarter.manage.service.TermService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Log4j2
@Controller
@RequestMapping("term_old")
public class TermControllerOld {
    @Autowired
    private TermService termService;

    @GetMapping("/detail")
    public String getTerm(@RequestParam(value = "id", defaultValue = "-1") Integer termId,Model model) {
        List<TermModel> termList = termService.getTermList();
        model.addAttribute("resList",termList);
        TermModel termModel = termService.selectLatestTerm();
        model.addAttribute("resDetail",termModel);
        return "ex_function/term/term_detail";
    }

    @GetMapping("/register")
    public String getTermRegistPage(Model model, @ModelAttribute TermModel termModel) {
        model.addAttribute("resDetail",termModel);
        model.addAttribute("edit",0);
        return "ex_function/terms/term_register";
    }

    @PostMapping("/insertUpdate")
    public String insertTerm(TermModel termModel, @RequestParam(value = "edit", defaultValue = "") Integer edit ) {
        log.info("term :::::"+termModel);
        log.info("edit :::::"+edit);
        if(edit==1){
            int termId = termModel.getTermId();
            // termService.updateTerm(termModel);
            log.info("update boardId :::::" + termId);
            return "redirect:detail?id=" +termId;
        }else{
            termModel.setTermActive(1);
            int termId = termService.insertTerm(termModel);
            log.info("insert termId ::::::"+termId);
//            return "redirect:detail?id="+termId;
            return "redirect:detail";
        }
    }
}
