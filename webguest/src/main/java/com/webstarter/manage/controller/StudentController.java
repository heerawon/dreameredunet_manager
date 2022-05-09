package com.webstarter.manage.controller;

import com.webstarter.manage.model.*;
import com.webstarter.manage.service.AuthService;
import com.webstarter.manage.service.PaymentOrderService;
import com.webstarter.manage.service.ShippingService;
import com.webstarter.manage.service.StudentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private PaymentOrderService paymentOrderService;

    @Autowired
    private ShippingService shippingService;

    //학생 목록 (사용자 회원관리)
    @GetMapping("/list")
    public String getStudentList(Model model,
                                 @RequestParam(defaultValue = "") String userName,
                                 @RequestParam(defaultValue = "") String studentGender,
                                 @RequestParam(defaultValue = "") String birthStartDt,
                                 @RequestParam(defaultValue = "") String birthEndDt,
                                 ResponseService<List<StudentModel>> responseStudentList) {
        responseStudentList = studentService.getStudentList(userName,studentGender,birthStartDt,birthEndDt);
        if(responseStudentList.isSuccess()){
            model.addAttribute("resList",responseStudentList.getResObjectData());
            model.addAttribute("userName",userName);
            model.addAttribute("studentGender",studentGender);
            model.addAttribute("birthStartDt",birthStartDt);
            model.addAttribute("birthEndDt",birthEndDt);
        }
        return "ex_function/student/pub_student_list";
    }

    //학생 상세
    @GetMapping("/detail")
    public String studentDetail(Model model,
                                @RequestParam(value = "id", defaultValue = "") String fkUserId,
                                ResponseService<StudentModel> ResponseStudentService,
                                ResponseService<List<PaymentOrderModel>> ResponsePaymnetService,
                                ResponseService<List<ShippingModel>> ResponseShippingService) {

        ResponseStudentService = studentService.getStudentDetail(fkUserId);
        var resStudent = ResponseStudentService.getResObjectData();

        ResponsePaymnetService = paymentOrderService.selectOrderList("",fkUserId,"","","");
        var orderList = ResponsePaymnetService.getResObjectData();

        ResponseShippingService = shippingService.selectShippingList(fkUserId,"",-1L,"","");
        var shippingList = ResponseShippingService.getResObjectData();

        model.addAttribute("resModel",resStudent);
        model.addAttribute("orderList",orderList);
        model.addAttribute("shippingList",shippingList);

        return "ex_function/student/pub_student_detail";
    }

    //학생정보 수정
    @PostMapping("/update")
    public ResponseEntity<HttpMessageModel> updateStudent(@ModelAttribute StudentModel studentModel,
                                                          ResponseService<String> responseUpdateService) {

        try {
            String studentId = studentModel.getUserId();
            responseUpdateService = studentService.updateStudent(studentModel);
            if (responseUpdateService.isSuccess()){
                return new ResponseMessage(200, "success", "").getResponse();
            }else{
                return new ResponseMessage(202, "success", "변경되지 않았습니다. 재시도해주세요. ").getResponse();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseMessage(500, e.getMessage(), "재시도해주세요.").getResponse();
        }
    }

    //회원 탈퇴일 설정
    @PostMapping("/delete")
    public ResponseEntity<HttpMessageModel> deleteStudent(@RequestParam(value = "id", defaultValue = "") String userId,
                                                          ResponseService<String> responseDeleteService) {
        log.info("delete userId ::::::"+userId);

        try {
            responseDeleteService = studentService.updateUserWithdrawal(userId);
            if(responseDeleteService.isSuccess()){
                log.info("withdrawal dt : "+responseDeleteService.getResObjectData());
                return new ResponseMessage(200, "success", responseDeleteService.getResObjectData()).getResponse();
            }else {
                return new ResponseMessage(202, "success", "변경되지 않았습니다. 재시도해주세요. ").getResponse();
            }
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseMessage(500, e.getMessage(), "재시도해주세요.").getResponse();
        }
    }

}
