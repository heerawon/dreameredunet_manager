package com.webstarter.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FeeModel {
    private int rateId;
    private String rateName;
    private int price;
    private String regDt;
    private int is_del;
}
