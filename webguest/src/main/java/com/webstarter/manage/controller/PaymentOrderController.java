package com.webstarter.manage.controller;

import com.webstarter.manage.configure.LoginUser;
import com.webstarter.manage.mapper.db1.ShippingMapper;
import com.webstarter.manage.model.*;
import com.webstarter.manage.security.model.SessionUser;
import com.webstarter.manage.service.PaymentOrderService;
import com.webstarter.manage.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "order")
public class PaymentOrderController {
    @Autowired
    private PaymentOrderService paymentOrderService;

    @Autowired
    private ShippingService shippingService;

    @GetMapping("list")
    public String getTeamList(Model model,
                              @RequestParam(value = "type", defaultValue = "subscribe") String type, // subscribe : 구독   item : 일반
                              @RequestParam(value = "order", defaultValue = "") String order, // 주문자
                              @RequestParam(value = "startDt", defaultValue = "") String startDt, // 검색 시자일자
                              @RequestParam(value = "endDt", defaultValue = "") String endDt // 검색 종료 일자
                              ) {
        var listResponseService = paymentOrderService.selectOrderList(type,"",order,startDt,endDt);
        var payMentList =  listResponseService.getResObjectData();
        model.addAttribute("resList",payMentList);
        model.addAttribute("type",type);
        model.addAttribute("order",order);
        model.addAttribute("startDt",startDt);
        model.addAttribute("endDt",endDt);
        return "ex_function/order/pub_order_list";
    }

    @GetMapping("detail")
    public String getOrderDetail(Model model,
                                 @RequestParam(value = "type", defaultValue = "subscribe") String type, // subscribe : 구독   item : 일반
                                 @RequestParam(value = "id", defaultValue = "") Long orderId, // 주문 목록
                                 @RequestParam(value = "userId", defaultValue = "") String userId, // 주문 자 아이디
                                 ResponseService<PaymentOrderModel> ResponseDetailService,
                                 ResponseService<ShippingModel> ResponseShippingService
    ) {


        ResponseDetailService = paymentOrderService.selectOrderDetail(type,orderId);
        var resModel = ResponseDetailService.isSuccess()? ResponseDetailService.getResObjectData(): new PaymentOrderModel();

        ResponseShippingService = shippingService.selectShippingStatus(orderId,userId);
        var shippingModel = ResponseShippingService.isSuccess()? ResponseShippingService.getResObjectData(): new ShippingModel();

        model.addAttribute("resModel",resModel);
        model.addAttribute("shippingModel",shippingModel);
        model.addAttribute("type",type);

        return "ex_function/order/pub_order_detail";
    }

    //배송여부 변경
    @PostMapping("/updateIsShipping")
    public ResponseEntity<HttpMessageModel> updateIsShipping (@RequestParam(defaultValue = "") Long orderId,
                                                              @RequestParam(defaultValue = "") String isShipping,
                                                              ResponseService<String> ResponseOrderService){
        try {
            ResponseOrderService = paymentOrderService.updateIsShipping(orderId,isShipping);
            return new ResponseMessage(200, "success", "").getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseMessage(500, e.getMessage(), "재시도해주세요.").getResponse();
        }
    }
}
