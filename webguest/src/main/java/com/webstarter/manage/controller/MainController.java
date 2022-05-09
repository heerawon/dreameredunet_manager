package com.webstarter.manage.controller;

import com.webstarter.manage.model.MainUserModel;
import com.webstarter.manage.model.UploadFile;
import com.webstarter.manage.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("main")
public class MainController {


    @Autowired
    MainService mainService;

    @GetMapping("/map")
    public String mainPageMap(Map<String, Object> reqMap,Model model) {
        //http://localhost:9090/main/map

        // HashMap을 이용하여 요청합니다.
        // DB와 연동되지 않는 값이나, 반복해서 필요로 하지 않는 값은 HashMap을 이용하면,
        // 쉽게 전달 할 수 있습니다.

        int reqInt = 2;

        String resStr = "";
        reqMap.put("userPkId", reqInt);
        resStr = mainService.getDataMap(reqMap);

        model.addAttribute("userPkId",reqInt);
        model.addAttribute("userName",resStr);
        return "ex_main/main_map";
    }

    @GetMapping("/string")
    public String mainPageString(Map<String, Object> reqMap, Model model,
                                 @RequestParam(value = "name", defaultValue = "") String reqStr) {
        //http://localhost:9090/main/string?name=하나

        // String 문자열을 이용하여 요청합니다.
        // 단일 문자열로 요청할 경우 String Type 으로 간단히 요청 할 수 있습니다.

        String resStr = mainService.getDataString(reqStr);
        // Service 에서 Null 값이 전달되지 않도록 작성해둡니다.

        model.addAttribute("userName",reqStr);
        model.addAttribute("userPkId",resStr);
        return "ex_main/main_map";

    }

    @GetMapping("/model")
    public String mainPageModel(Map<String, Object> reqMap, Model model,
                                @ModelAttribute("MainUserModel") MainUserModel reqModel) {
        //http://localhost:9090/main/model?userPkId=1&userName=하나

        // Model 을 이용하여 요청합니다.
        // @ModelAttribute 를 통해 Model 에 값을 채워진 상태로 받을 수 있으며,
        // Model 인스턴스를 생성해 직접 값을 채워 전달 할 수 있습니다.

//        MainUserModel mainUserModel = new MainUserModel();
//        mainUserModel.setUserName("하나");
//        mainUserModel.setUserPkId(1);

        String resStr = mainService.getDataModel(reqModel);
        // Service 에서 Null 값이 전달되지 않도록 작성해둡니다.

        model.addAttribute("userName",resStr);
        model.addAttribute("reqModel",reqModel);

        return "ex_main/main_map";

    }

    @GetMapping("/list")
    public String mainPageList(Map<String, Object> reqMap, Model model) {
        //http://localhost:9090/main/list

        // 결과 값을 List<T> 로 전달 받습니다.
        // camelCase true 옵션을 적용해두었기 때문에,
        // user_name 은 userName 변수에 매핑 됩니다.
        // mapper 에는 resultType 에 List 는 생략, Model 이름만 전달합니다.
        // Mapper 에서 Model 을 사용하기 위해서는,
        // resource/mybatis/mybatis-config.xml 에 alias 를 작성합니다.


//        List<MainUserModel> userList = new ArrayList<MainUserModel>();  // <T> Generic 은  생략가능합니다.
        List<MainUserModel> userList = mainService.getDataUserList();

        model.addAttribute("userList",userList);

        return "ex_main/main_page_list";
    }



}

