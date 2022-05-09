package com.webstarter.manage.mapper.db1;

import com.webstarter.manage.model.PaymentOrderModel;

import java.util.List;
import java.util.Map;

public interface PaymentOrderMapper {
    List<PaymentOrderModel> selectAllOrderList(Map reqMap);
    List<PaymentOrderModel> selectOrderListSubscribe(Map reqMap);
    List<PaymentOrderModel> selectOrderListItem(Map reqMap);
    PaymentOrderModel selectDetailSubscribe(Map reqMap);
    PaymentOrderModel selectDetailItem(Map reqMap);
    int updateIsShipping(PaymentOrderModel paymentOrderModel);
}
