package com.webstarter.manage.service;

import com.webstarter.manage.mapper.db1.ProductItemsMapper;
import com.webstarter.manage.mapper.db1.ProductSubscribeMapper;
import com.webstarter.manage.mapper.db1.TermMapper;
import com.webstarter.manage.model.ProductItemsModel;
import com.webstarter.manage.model.ProductSubscriptionModel;
import com.webstarter.manage.model.ResponseService;
import com.webstarter.manage.model.TermModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class ProductService {
    @Resource
    private ProductSubscribeMapper productSubscribeMapper;
    @Resource
    private ProductItemsMapper productItemsMapper;

    public List<ProductSubscriptionModel> getProductSubscriptionList(){
        return productSubscribeMapper.selectProductSubscriptionList();
    }

    public ProductSubscriptionModel getProductSubscriptionDetail(Integer subscriptionId){
        ProductSubscriptionModel model = productSubscribeMapper.selectProductSubscriptionDetail(subscriptionId);
        return model;
    }

    public boolean insertProductSubscription(ProductSubscriptionModel model){
        int res = productSubscribeMapper.insertProductSubscription(model);

        if(res > 0){
            return true;
        }
        return false;
    }


//    구독 업데이트 사용하지 않음
//    public boolean updateProductSubscription(ProductSubscriptionModel model){
//        int res = productSubscribeMapper.updateProductSubscription(model);
//
//        if(res > 0){
//            return true;
//        }
//        return false;
//    }
    public boolean deleteProductSubscription(Integer productSubscribeId){
        int res = productSubscribeMapper.deleteProductSubscription(productSubscribeId);

        if(res > 0){
            return true;
        }
        return false;
    }

    /**
     * 공개 여부 변경 서비스
     * @param model ( product_subscribe_id, is_active )
     */
    public ResponseService<String> setSubscribeIsActive(ProductSubscriptionModel model){
        String resMsg = "";
        try {
            if(productSubscribeMapper.setSubscribeIsActive(model)<1){
                resMsg = "공개 상태 변경에 실패했습니다.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            resMsg = "공개 상태 변경중 DB오류가 발생했습니다.";
        }
        return new ResponseService<String>(resMsg, "");
    }


    public List<ProductItemsModel> getProductItemsList(){
        return productItemsMapper.selectProductItemList();
    }

    public ProductItemsModel getProductItemsDetail(Integer ItemsId){
        ProductItemsModel model = productItemsMapper.selectProductItemDetail(ItemsId);
        return model;
    }

    public boolean insertProductItem(ProductItemsModel model){
        int res = productItemsMapper.insertProductItem(model);

        if(res > 0){
            return true;
        }
        return false;
    }
    public boolean updateProductItem(ProductItemsModel model){
        int res = productItemsMapper.updateProductItem(model);

        if(res > 0){
            return true;
        }
        return false;
    }
    public boolean deleteProductItem(Integer productSubscribeId){
        int res = productItemsMapper.deleteProductItem(productSubscribeId);

        if(res > 0){
            return true;
        }
        return false;
    }
    /**
     * 공개 여부 변경 서비스
     * @param model ( product_items_id, is_active )
     */
    public ResponseService<String> setProductItemIsActive(ProductItemsModel model){
        String resMsg = "";
        try {
            if(productItemsMapper.setProductItemIsActive(model)<1){
                resMsg = "공개 상태 변경에 실패했습니다.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            resMsg = "공개 상태 변경중 DB오류가 발생했습니다.";
        }
        return new ResponseService<String>(resMsg, "");
    }
}
