package com.webstarter.manage.service;

import com.webstarter.manage.mapper.db1.ShippingMapper;
import com.webstarter.manage.model.PaymentOrderModel;
import com.webstarter.manage.model.ResponseService;
import com.webstarter.manage.model.ShippingModel;
import com.webstarter.manage.model.ShippingServiceModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ShippingService {

    @Resource
    private ShippingMapper shippingMapper;

    /**
     * 택배사 목록 조회 (코드, 택배사명)
     */
    public ResponseService<List<ShippingServiceModel>> selectShippingServiceList(){
        String errMsg = "";
        Map reqMap = new HashMap<String,Object>();
        List<ShippingServiceModel> shippingCodeList = new ArrayList<ShippingServiceModel>();

        try {
            shippingCodeList = shippingMapper.selectShippingServiceList(reqMap);
        }catch (Exception e){
            errMsg = "배송 등록 여부 조회 실패";
        }

        return new ResponseService<List<ShippingServiceModel>>(errMsg,shippingCodeList);
    }

    /**
     * 배송 등록여부 및 상태 조회
     * @param orderId 배송 주문번호
     * @param userId 배송 주문 유저 아이디
     */
    public ResponseService<ShippingModel> selectShippingStatus(Long orderId, String userId){
        String errMsg = "";
        ShippingModel shippingModel = new ShippingModel();

        Map reqMap = new HashMap<String,Object>();
        reqMap.put("orderId",orderId);
        reqMap.put("userId",userId);

        try {
            shippingModel = shippingMapper.selectShippingStatus(reqMap);
        }catch (Exception e){
            errMsg = "배송 등록 여부 조회 실패";
        }

        return new ResponseService<ShippingModel>(errMsg,shippingModel);
    }

    /**
     * 배송등록
     * @param paymentOrderModel 배송테이블 세팅을 위해 주문 테이블에 입력된 내용을 가져옴
     */
    public ResponseService<Long> insertShipping (PaymentOrderModel paymentOrderModel){
        String errMsg = "";

        ShippingModel shippingModel = new ShippingModel();
        shippingModel.setFkSuserId(paymentOrderModel.getFkSuserId());
        shippingModel.setFkOrderId(paymentOrderModel.getOrderId());
        shippingModel.setReceiverName(paymentOrderModel.getOrderName());
        shippingModel.setReceiverCell(paymentOrderModel.getOrderCell());
        shippingModel.setReceiverZipcode(paymentOrderModel.getOrderZipcode());
        shippingModel.setReceiverAddress(paymentOrderModel.getOrderAddress());
        shippingModel.setMemo(paymentOrderModel.getOrderMemo());

        try {
            if(shippingMapper.insertShipping(shippingModel)<1){
                errMsg = "배송 등록중 실패";
            }
        }catch (Exception e){
            errMsg = "배송 등록 여부 조회중 DB 오류 발생";
        }

        return new ResponseService<Long>(errMsg,shippingModel.getShippingId());
    }

    /**
     * 배송 상세
     * @param shippingId 상세 조회 할 배송 아이디
     */
    public ResponseService<ShippingModel> selectShippingDetail (Long shippingId){
        String errMsg = "";
        ShippingModel shippingModel = new ShippingModel();

        Map reqMap = new HashMap<String,Object>();
        reqMap.put("shippingId",shippingId);

        try {
            shippingModel = shippingMapper.selectShippingDetail(reqMap);
        }catch (Exception e){
            errMsg = "배송 상세 조회중 DB 오류 발생";
        }

        return new ResponseService<ShippingModel>(errMsg,shippingModel);
    }

    /**
     * 배송목록
     * @param order 주문자명
     * @param status 배송상태 (0: , 1:)
     * @param startDt 주문일 검색 - 시작일
     * @param endDt 주문일 검색 - 종료일
     */
    public ResponseService<List<ShippingModel>> selectShippingList (String userId, String order,Long status, String startDt, String endDt){
        String errMsg = "";
        List<ShippingModel> shippingList = new ArrayList<ShippingModel>();

        Map reqMap = new HashMap<String,Object>();
        reqMap.put("userId",userId);
        reqMap.put("order",order);
        reqMap.put("status",status);
        reqMap.put("status",status);
        reqMap.put("startDt",startDt);
        reqMap.put("endDt",endDt);

        try {
            shippingList = shippingMapper.selectShippingList(reqMap);
        }catch (Exception e){
            errMsg = "배송 상세 조회중 DB 오류 발생";
        }

        return new ResponseService<List<ShippingModel>>(errMsg,shippingList);
    }

    /**
     * 송장 등록
     * @param shippingId 업데이트 할 배송 아이디
     * @param shippingCompany 택배사 (코드)
     * @param shippingInvoice 송장 번호
     * @param sendDt 배송 일자 (사용자 선택으로 입력함)
     */
    public ResponseService<String> updateShipping (Long shippingId, String shippingCompany, String shippingInvoice, String sendDt) throws ParseException {
        String errMsg = "";

        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sendDate = transFormat.parse(sendDt);

        ShippingModel shippingModel = new ShippingModel();
        shippingModel.setShippingId(shippingId);
        shippingModel.setShippingCompany(shippingCompany);
        shippingModel.setShippingInvoice(shippingInvoice);
        shippingModel.setSendDt(sendDate);

        try {
            if(shippingMapper.updateShipping(shippingModel)<1){
                errMsg = "배송 등록중 실패";
            }
        }catch (Exception e){
            errMsg = "배송 등록 여부 조회중 DB 오류 발생";
        }

        return new ResponseService<String>(errMsg,"");
    }

    /**
     * 배송 정보 삭제 (초기화 )
     * @param shippingId 업데이트 할 배송 아이디
     */
    public ResponseService<String> deleteShipping (Long shippingId) {
        String errMsg = "";

        ShippingModel shippingModel = new ShippingModel();
        shippingModel.setShippingId(shippingId);

        try {
            if(shippingMapper.deleteShipping(shippingModel)<1){
                errMsg = "배송 삭제중 실패";
            }
        }catch (Exception e){
            errMsg = "배송 삭제 중 DB 오류 발생";
        }

        return new ResponseService<String>(errMsg,"");
    }
}
