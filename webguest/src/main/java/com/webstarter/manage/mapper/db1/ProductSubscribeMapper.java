package com.webstarter.manage.mapper.db1;

import com.webstarter.manage.model.ProductSubscriptionModel;
import com.webstarter.manage.model.TermModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository

//구독 상품관리
public interface ProductSubscribeMapper {
    List<ProductSubscriptionModel> selectProductSubscriptionList();
    int insertProductSubscription(ProductSubscriptionModel term);
    int updateProductSubscription(ProductSubscriptionModel term); //사용하지 말것
    int deleteProductSubscription(int productSubscribeId);
    ProductSubscriptionModel selectProductSubscriptionDetail(int subscriptionId);
    int setSubscribeIsActive (ProductSubscriptionModel model);
}
