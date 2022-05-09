package com.webstarter.manage.controller;

import com.webstarter.manage.model.FeeModel;
import com.webstarter.manage.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("fee")
public class FeeController {
    @Autowired
    private FeeService feeService;

    @GetMapping("/register")
    public String getTermRegistPage(Model model, @ModelAttribute FeeModel feeModel) {
        model.addAttribute("resDetail",feeModel);
        return "ex_function/fee/fee_register";
    }

    @PostMapping("/insert")
    public String insertTerm(FeeModel feeModel) {
        System.out.println("fee :::::"+feeModel);
        String resPath = "redirect:list";
//        String resMsg = feeService.insertFee(feeModel);
        String resMsg = "";
        if(resMsg.length()>0){
            resPath = resPath+"?error="+resMsg;
        }
        return resPath;
    }
}
