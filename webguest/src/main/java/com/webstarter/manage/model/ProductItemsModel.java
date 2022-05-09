package com.webstarter.manage.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductItemsModel {
    private int productItemsId;
    private String itemsName;
    private int itemsPrice;
    private int isActive;
    private  String regDate;
}
