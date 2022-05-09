package com.webstarter.manage.model;


import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class PaymentOrderModel {

  private long orderId;
  private long fkPgIamportId;
  private String fkSuserId;
  private long fkProductItemsId;
  private long fkProductSubscribeId;
  private String orderName;
  private String orderRecipient;
  private String orderCell;
  private String orderAddress;
  private String orderAddressDetail;
  private String orderMemo;
  private String orderStatus;
  private String orderZipcode;
  private String merchantUid;
  private String subscribeName;
  private String subscribeTerm;
  private String itemsName;
  private String userSnsType;
  private String email;
  private String userId;
  private String userName;
  private String isShipping; // 배송여부 (1: 배송등록, 0: 배송없음)

  private long totalPrice;

  @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
  private Date regDt;
  private long isDel;


}
