package com.webstarter.manage.controller;

import com.webstarter.manage.model.*;
import com.webstarter.manage.service.PaymentOrderService;
import com.webstarter.manage.service.ShippingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log4j2
@Controller
@RequestMapping(value = "shipping")
public class ShippingController {
    @Autowired
    private PaymentOrderService paymentOrderService;

    @Autowired
    private ShippingService shippingService;

    //배송 추가 (주문상세 -> 배송추가 선택시 실행)
    @PostMapping("/insert")
    public ResponseEntity<HttpMessageModel> insertShipping (@RequestParam(defaultValue = "subscribe") String type, // subscribe : 구독   item : 일반
                                                            @RequestParam(defaultValue = "") Long orderId, // 주문 목록
                                                            @RequestParam(defaultValue = "") String isShipping, // 주문 목록
                                                            ResponseService<String> ResponseOrderService,
                                                            ResponseService<PaymentOrderModel> ResponseDetailService,
                                                            ResponseService<Long> ResponseShippingService){


        //1. 주문정보(payment_order) 테이블 is_shipping 값 업데이트
        ResponseOrderService = paymentOrderService.updateIsShipping(orderId,isShipping);
        //2. 주문정보(payment_order) 조회 (shipping테이블 세팅하기 위함)
        ResponseDetailService = paymentOrderService.selectOrderDetail(type,orderId);
        var paymentOrderModel = ResponseDetailService.isSuccess()? ResponseDetailService.getResObjectData(): new PaymentOrderModel();
        //3. 배송정보 insert
        ResponseShippingService = shippingService.insertShipping(paymentOrderModel);
        var shippingId = ResponseShippingService.isSuccess()? ResponseShippingService.getResObjectData(): -1;

        try {
            return new ResponseMessage(200, "success", shippingId).getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseMessage(500, e.getMessage(), "재시도해주세요.").getResponse();
        }
    }

    //배송 상세
    @GetMapping("detail")
    public String getShippingDetail(Model model,
                                    @RequestParam(value = "id", defaultValue = "") Long shippingId, // 배송 아이디
                                    ResponseService<ShippingModel> ResponseShippingService,
                                    ResponseService<List<ShippingServiceModel>> ResponseShippingCodeListService
    ) {
        ResponseShippingService = shippingService.selectShippingDetail(shippingId);
        var shippingModel = ResponseShippingService.isSuccess()? ResponseShippingService.getResObjectData(): new ShippingModel();

        ResponseShippingCodeListService = shippingService.selectShippingServiceList();
        var shippingCodeList = ResponseShippingCodeListService.isSuccess() ? ResponseShippingCodeListService.getResObjectData(): new ArrayList<ShippingServiceModel>();

        model.addAttribute("resModel",shippingModel);
        model.addAttribute("shippingCodeList",shippingCodeList);

        return "ex_function/shipping/pub_order_delivery_register3";
    }

    //배송 목록
    @GetMapping("list")
    public String getShippingList(Model model,
                                  @RequestParam(defaultValue = "") String order, // 주문자
                                  @RequestParam(defaultValue = "-1") Long status, // 상태
                                  @RequestParam(defaultValue = "") String startDt, // 검색 시자일자
                                  @RequestParam(defaultValue = "") String endDt, // 검색 종료 일자
                                  ResponseService<List<ShippingModel>> listResponseService
    ) {
        listResponseService = shippingService.selectShippingList("",order,status,startDt,endDt);
        var shippingList = listResponseService.isSuccess()? listResponseService.getResObjectData():new ArrayList<ShippingModel>();
        model.addAttribute("resList",shippingList);
        model.addAttribute("order",order);
        model.addAttribute("status",status);
        model.addAttribute("startDt",startDt);
        model.addAttribute("endDt",endDt);
        return "ex_function/shipping/pub_order_delivery_list";
    }

    //송장등록
    @PostMapping("/update")
    public ResponseEntity<HttpMessageModel> updateInvoice (@RequestParam(defaultValue = "-1") Long shippingId,
                                                           @RequestParam(defaultValue = "") String shippingCompany,
                                                           @RequestParam(defaultValue = "") String shippingInvoice,
                                                           @RequestParam(defaultValue = "") String sendDt,
                                                           @RequestParam(defaultValue = "") Long orderId,
                                                           @RequestParam(defaultValue = "") Long isShipping,
                                                           ResponseService<String> ResponseShippingService){
        log.info("shippingId"+shippingId);
        log.info("sendDt"+sendDt);

        try {
            ResponseShippingService = shippingService.updateShipping(shippingId,shippingCompany,shippingInvoice,sendDt);
            return new ResponseMessage(200, "success", "").getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseMessage(500, e.getMessage(), "재시도해주세요.").getResponse();
        }
    }

    //송장삭제 (초기화)
    @PostMapping("/updateShippingStatus")
    public ResponseEntity<HttpMessageModel> updateStatus (@RequestParam(defaultValue = "-1") Long shippingId,
                                                          @RequestParam(defaultValue = "") Long orderId,
                                                          @RequestParam(defaultValue = "") String isShipping,
                                                          ResponseService<String> ResponseOrderService,
                                                          ResponseService<String> ResponseShippingService){
        try {

            log.error("shippingId"+shippingId);
            log.error("orderId"+orderId);
            log.error("isShipping"+isShipping);

            //1. payment_order 테이블 is_shipping 값 업데이트
            ResponseOrderService = paymentOrderService.updateIsShipping(orderId,isShipping);
            //2. shipping 테이블 삭제
            ResponseShippingService = shippingService.deleteShipping(shippingId);
            return new ResponseMessage(200, "success", "").getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseMessage(500, e.getMessage(), "재시도해주세요.").getResponse();
        }
    }
}
