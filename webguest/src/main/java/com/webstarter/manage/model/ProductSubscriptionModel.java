package com.webstarter.manage.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSubscriptionModel {
    private int productSubscribeId;
    private String subscribeName;
    private int subscribePrice;
    private int isActive;
    private  String regDate;
    //추가
    private String subscribeDesc; // 부연설명 : 상품 하단에 나옴 
    private int subscribeTerm; //이용기간
}
