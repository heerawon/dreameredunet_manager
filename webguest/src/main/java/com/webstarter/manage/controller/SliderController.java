package com.webstarter.manage.controller;

import com.webstarter.manage.configure.ConstData;
import com.webstarter.manage.model.BoardModel;
import com.webstarter.manage.model.SliderImagesModel;
import com.webstarter.manage.service.BoardService;
import com.webstarter.manage.service.SliderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Log4j2
@Controller
@RequestMapping("slider")
@RequiredArgsConstructor
public class SliderController {

    private final SliderService sliderService;


    @GetMapping("{type}")
    public String sliderCommList(Model model,
                                 @PathVariable String type,
                                 @RequestParam(defaultValue = "") String errCode
    ) {
        if (getTypeTitle(type).length() < 1) {
            return "redirect:/board/list?errMsg=sliderCommList_001";
        }
        var responseService = sliderService.selectListSlider(type);
        if (responseService.isSuccess()) {
            model.addAttribute("lsData", responseService.getResObjectData());
        } else {
            model.addAttribute("lsData", new ArrayList<SliderImagesModel>());
            errCode = "sliderCommList_002";
        }

        model.addAttribute("title", getTypeTitle(type));
        model.addAttribute("type", type);
        model.addAttribute("errMsg", ConstData.getCode(errCode));
        return "ex_function/slider_images/slider_list";
    }


    @PostMapping("add")
    public String addSlider(Model model,
                            @RequestParam(value = "imgSrc", defaultValue = "") String imgSrc,
                            @RequestParam(value = "type", defaultValue = "") String type
    ) {
        var responseService = sliderService.insertSlider(type, imgSrc);
        if (responseService.isSuccess()) {
            return "redirect:/slider/" + type;
        } else {
            return "redirect:/slider/" + type + "?errCode=addSlider_001";
        }
    }

    @GetMapping("delete")
    public String deleteSlider(@RequestParam(value = "id", defaultValue = "-1") int id,
                               @RequestParam(value = "type", defaultValue = "") String type) {
        var responseService = sliderService.deleteSlider(id);
        if (responseService.isSuccess()) {
            return "redirect:/slider/" + type;
        } else {
            return "redirect:/slider/" + type + "?errCode=addSlider_003";
        }
    }

    @PostMapping("edit")
    public String updateSlider(@RequestParam(value = "id", defaultValue = "-1") int id,
                               @RequestParam(value = "imgSrc", defaultValue = "") String imgSrc,
                               @RequestParam(value = "type", defaultValue = "") String type) {
        var responseService = sliderService.updateSlider(id, imgSrc);
        if (responseService.isSuccess()) {
            return "redirect:/slider/" + type;
        } else {
            return "redirect:/slider/" + type + "?errCode=addSlider_003";
        }
    }

    /**
     * 특별활동 종류별 이름 가져오기
     *
     * @param type
     * @return
     */
    private String getTypeTitle(String type) {
        String str = "";
        switch (type) {
            case "dong":
                str = "동화작가";
                break;
            case "cal":
                str = "칼럼리스트";
                break;
            case "myung":
                str = "명사특강";
                break;
            case "dok":
                str = "독서특강";
                break;

        }
        return str;
    }
}
