package com.webstarter.manage.controller;

import com.webstarter.manage.model.PaymentOrderModel;
import com.webstarter.manage.model.ResponseService;
import com.webstarter.manage.model.ShippingModel;
import com.webstarter.manage.model.StudentModel;
import com.webstarter.manage.service.PaymentOrderService;
import com.webstarter.manage.service.ShippingService;
import com.webstarter.manage.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("excelDown")
public class ExcelDownloadController {
    //출처: https://karas1237.tistory.com/7 [카라스의 기술블로그]

    @Autowired
    private StudentService studentService;

    @Autowired
    private PaymentOrderService paymentOrderService;

    @Autowired
    private ShippingService shippingService;

    @GetMapping(path="/sample", produces = "application/vnd.ms-excel")
    public String downloadSampleExcel() {
        return "sampleXls";
    }

    //사용자 회원관리 목록 엑셀 다운로드
    @GetMapping(path="/studentList", produces = "application/vnd.ms-excel")
    public String downloadStudentExcel(Model model,
                                       @RequestParam(defaultValue = "") String userName,
                                       @RequestParam(defaultValue = "") String studentGender,
                                       @RequestParam(defaultValue = "") String birthStartDt,
                                       @RequestParam(defaultValue = "") String birthEndDt,
                                       ResponseService<List<StudentModel>> responseStudentList) {

        responseStudentList = studentService.getStudentList(userName,studentGender,birthStartDt,birthEndDt);
        if(responseStudentList.isSuccess()){
            model.addAttribute("resList",responseStudentList.getResObjectData());
        }

        return "studentXls";
    }

    //주문 목록 엑셀 다운로드
    @GetMapping(path="/orderList", produces = "application/vnd.ms-excel")
    public String downloadOrderListExcel(Model model,
                                         @RequestParam(value = "type", defaultValue = "subscribe") String type, // subscribe : 구독   item : 일반
                                         @RequestParam(value = "order", defaultValue = "") String order, // 주문자
                                         @RequestParam(value = "startDt", defaultValue = "") String startDt, // 검색 시자일자
                                         @RequestParam(value = "endDt", defaultValue = "") String endDt, // 검색 종료 일자
                                         ResponseService<List<PaymentOrderModel>> responseOrderList) {

        responseOrderList = paymentOrderService.selectOrderList(type,"",order,startDt,endDt);
        var paymentList = responseOrderList.isSuccess() ? responseOrderList.getResObjectData() : new ArrayList<PaymentOrderModel>();
        model.addAttribute("resList",paymentList);

        return "orderListXls";
    }

    //주문 목록 엑셀 다운로드
    @GetMapping(path="/shippingList", produces = "application/vnd.ms-excel")
    public String downloadShippingListExcel(Model model,
                                            @RequestParam(defaultValue = "") String order, // 주문자
                                            @RequestParam(defaultValue = "-1") Long status, // 상태
                                            @RequestParam(defaultValue = "") String startDt, // 검색 시자일자
                                            @RequestParam(defaultValue = "") String endDt, // 검색 종료 일자
                                            ResponseService<List<ShippingModel>> listResponseService) {

        listResponseService = shippingService.selectShippingList("",order,status,startDt,endDt);
        var shippingList = listResponseService.isSuccess()? listResponseService.getResObjectData():new ArrayList<ShippingModel>();
        model.addAttribute("resList",shippingList);

        return "ShippingListXls";
    }
}
