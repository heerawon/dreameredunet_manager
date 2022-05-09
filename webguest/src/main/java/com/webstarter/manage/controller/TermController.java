package com.webstarter.manage.controller;

import com.webstarter.manage.model.TermModel;
import com.webstarter.manage.service.TermService;
import com.webstarter.manage.service.TermsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("term")
public class TermController {
    @Autowired
    private TermsService termsService;

    @GetMapping("/private")
    public String getTermPrivate(Model model) {
        String contents = termsService.getTerm("private");

        model.addAttribute("contents",contents);
        return "ex_function/terms/pub_terms_private";
    }

    @PostMapping("/private")
    public String updateTermPrivate(@RequestParam(value = "contents", defaultValue = "") String contents ,
                                    HashMap<String,Object> reqMap) {

        reqMap.put("type","private");
        reqMap.put("contents",contents);

        termsService.updateTerm(reqMap);

        return "redirect:/term/private";
    }

    @GetMapping("/term")
    public String getTermTerm(Model model) {
        String contents =  termsService.getTerm("term");
        model.addAttribute("contents",contents);
        return "ex_function/terms/pub_terms_term";
    }


    @PostMapping("/term")
    public String updateTermTerm(@RequestParam(value = "contents", defaultValue = "") String contents ,
                                 HashMap<String,Object> reqMap) {
        reqMap.put("type","term");
        reqMap.put("contents",contents);
        termsService.updateTerm(reqMap);

        return "redirect:/term/term";
    }



}
