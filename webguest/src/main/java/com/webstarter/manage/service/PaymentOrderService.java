package com.webstarter.manage.service;

import com.webstarter.manage.mapper.db1.PaymentOrderMapper;
import com.webstarter.manage.mapper.db1.ShippingMapper;
import com.webstarter.manage.model.PaymentOrderModel;
import com.webstarter.manage.model.ResponseService;
import com.webstarter.manage.model.ShippingModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentOrderService {
    @Resource
    private PaymentOrderMapper paymentOrderMapper;



    /**
     * 주문내역 목록
     * @param type 주문 유형 (subscribe: 구독 , item: 일반상품, 없을경우 사용자 상세 페이지에서 조회)
     * @param order 주문자명
     * @param startDt 검색 시작일 (주문일)
     * @param endDt 검색 종료일 (주문일)
     */
    public ResponseService<List<PaymentOrderModel>> selectOrderList(String type, String userId, String order, String startDt, String endDt){
        String errMsg = "";
        List<PaymentOrderModel> resPaymentModel = new ArrayList<>();
        Map reqMap = new HashMap<String,Object>();
        reqMap.put("order",order);
        reqMap.put("userId",userId);
        reqMap.put("startDt",startDt);
        reqMap.put("endDt",endDt);
        if("subscribe".equals(type)){
            // 구독
            resPaymentModel = paymentOrderMapper.selectOrderListSubscribe(reqMap);

        }else if("item".equals(type)){
            // 상품
            resPaymentModel = paymentOrderMapper.selectOrderListItem(reqMap);

        }else{
//            errMsg = "올바르지 않은 유형입니다.";
            resPaymentModel = paymentOrderMapper.selectAllOrderList(reqMap);

        }
        return new ResponseService<List<PaymentOrderModel>>(errMsg,resPaymentModel);
    }

    /**
     * 주문내역 상세
     * @param type 주문 유형 (subscribe: 구독 , item: 일반상품)
     * @param orderId 주문목록
     */
    public ResponseService<PaymentOrderModel> selectOrderDetail(String type, Long orderId){
        String errMsg = "";
        PaymentOrderModel resPaymentModel = new PaymentOrderModel();
        Map reqMap = new HashMap<String,Object>();
        reqMap.put("orderId",orderId);

        if("subscribe".equals(type)){
            // 구독
            resPaymentModel = paymentOrderMapper.selectDetailSubscribe(reqMap);
        }else if("item".equals(type)){
            // 상품
            resPaymentModel = paymentOrderMapper.selectDetailItem(reqMap);
        }else{
            errMsg = "올바르지 않은 유형입니다.";
        }

        return new ResponseService<PaymentOrderModel>(errMsg,resPaymentModel);
    }

    /**
     * 배송여부 변경
     * @param orderId 주문 아이디
     * @param isShipping 배송여부 (1: 배송등록, 0: 배송없음)
     */
    public ResponseService<String> updateIsShipping(Long orderId, String isShipping){
        String errMsg = "";
        PaymentOrderModel paymentOrderModel = new PaymentOrderModel();
        paymentOrderModel.setOrderId(orderId);
        paymentOrderModel.setIsShipping(isShipping);

        try {
            if(paymentOrderMapper.updateIsShipping(paymentOrderModel)<1){
                errMsg = "배송여부 변경 중 오류 발생";
            }
        }catch (Exception e){
            errMsg = "배송여부 변경 중 DB 오류 발생";
        }

        return new ResponseService<String>(errMsg,"");
    }

}
