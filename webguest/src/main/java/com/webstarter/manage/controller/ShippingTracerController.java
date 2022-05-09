package com.webstarter.manage.controller;

import com.webstarter.manage.configure.ConstData;
import com.webstarter.manage.model.BoardModel;
import com.webstarter.manage.service.BoardService;
import com.webstarter.manage.service.SmartTracerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Log4j2
@Controller
@RequestMapping("shippingTracer")
@RequiredArgsConstructor
public class ShippingTracerController {
    @Value("${shippingTracer.t_key}")
    private String t_key;

    /**
     *
     * @param model
     * @param code 택배사 코드  http://info.sweettracker.co.kr/apidoc
     * @param invoice 택배 송장
     * @return
     */
    @GetMapping("")
    public String testTracer(Model model,
                             @RequestParam(value = "code", defaultValue = "") String code,
                             @RequestParam(value = "invoice", defaultValue = "") String invoice) {
        model.addAttribute("t_key",t_key);
        model.addAttribute("code",code);
        model.addAttribute("invoice",invoice);
        return "ex_function/smartTracer";
    }
}
