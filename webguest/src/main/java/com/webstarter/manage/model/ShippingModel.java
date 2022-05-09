package com.webstarter.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@ToString
public class ShippingModel extends PaymentOrderModel{
    private long shippingId;
    private String fkSuserId;
    private long fkOrderId;
    private String receiverName;
    private String receiverCell;
    private String receiverZipcode;
    private String receiverAddress;
    private String shippingStatus;
    private String memo;
    private String shippingCompany;
    private String shippingInvoice;
    private String shippingServiceName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sendDt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date regDt;

    private long isDel;

    private String userName;
    private String userRegDt;
    private String studentBirth;
    private String teamName;
}
