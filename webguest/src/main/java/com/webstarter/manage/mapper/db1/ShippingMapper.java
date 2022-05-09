package com.webstarter.manage.mapper.db1;

import com.webstarter.manage.model.ShippingModel;
import com.webstarter.manage.model.ShippingServiceModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
//배송관리
public interface ShippingMapper {
    List<ShippingServiceModel> selectShippingServiceList (Map reqMap);
    ShippingModel selectShippingStatus(Map reqMap);
    List<ShippingModel> selectShippingList(Map reqMap);
    ShippingModel selectShippingDetail(Map reqMap);
    int insertShipping(ShippingModel shippingModel);
    int updateShipping(ShippingModel shippingModel);
    int deleteShipping(ShippingModel shippingModel);
}
