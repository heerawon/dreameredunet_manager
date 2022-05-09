package com.webstarter.manage.mapper.db1;

import com.webstarter.manage.model.ProductItemsModel;
import com.webstarter.manage.model.ProductSubscriptionModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository

//판매 상품관리
public interface ProductItemsMapper {
    List<ProductItemsModel> selectProductItemList();
    int insertProductItem(ProductItemsModel term);
    int updateProductItem(ProductItemsModel term);
    int deleteProductItem(int productSubscribeId);
    ProductItemsModel selectProductItemDetail(int subscriptionId);
    int setProductItemIsActive(ProductItemsModel model);
}
