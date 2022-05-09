package com.webstarter.manage.controller;


import com.webstarter.manage.configure.LoginUser;
import com.webstarter.manage.model.*;
import com.webstarter.manage.security.model.SessionUser;
import com.webstarter.manage.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("product")
public class ProductController {
    @Resource
    private ProductService productService;

    // 구독료 설정
    @GetMapping("/subscribe")
    public String productSubscribeList(Map<String, Object> reqMap, Model model) {
        List<ProductSubscriptionModel> lsData =  productService.getProductSubscriptionList();
        model.addAttribute("lsData",lsData);

        return "ex_function/product/product_subscribes";
    }

    // 구독료 추가 화면
    @GetMapping("/subscribe/register")
    public String productSubscribeListRegister(Model model,
                                               @RequestParam(defaultValue = "") String type,
                                               @RequestParam(defaultValue = "-1") Integer id,
                                               ProductSubscriptionModel productSubscriptionModel) {
        model.addAttribute("type",type);

        if("edit".equals(type)){
            productSubscriptionModel = productService.getProductSubscriptionDetail(id);
            model.addAttribute("resModel",productSubscriptionModel);
            model.addAttribute("urlType","update");
        }else{
            model.addAttribute("urlType","register");
        }
        return "ex_function/product/product_subscribes_register";
    }

    // INSERT 구독료
    @PostMapping("/subscribe/register")
    public String productSubscribeListInsert(Map<String, Object> reqMap,
                                             ProductSubscriptionModel model ) {
        productService.insertProductSubscription(model);
        return "redirect:/product/subscribe";
    }

    // UPDATE 구독  ** 주의
    @PostMapping("/subscribe/update")
    @Transactional(rollbackOn = Exception.class)
    public String productSubscribeListUpdate(Map<String, Object> reqMap,
                                             ProductSubscriptionModel model ) throws Exception {

        //기존 상품 값을 보관하기위해 수정시 삭제 후 새로등록
        if(!productService.deleteProductSubscription(model.getProductSubscribeId())){
            throw new Exception("기존 상품 삭제 실패");
        }
        if(!productService.insertProductSubscription(model)){
            throw new Exception("상품 새로 추가 실패");
        }
//        productService.updateProductSubscription(model); //수정은 사용하지 않음 결제 시 값이 보관되어야 함
        return "redirect:/product/subscribe";
    }

    // DELETE 구독료
    @GetMapping("/subscribe/delete")
    public String productSubscribeListDelete(@RequestParam(defaultValue = "-1") Integer id){
        productService.deleteProductSubscription(id);

        return "redirect:/product/subscribe";
    }

    // 구독료 공개 여부 변경
    @PostMapping("/subscribe/setActive")
    public ResponseEntity<HttpMessageModel> setSubscribeIsActive(@ModelAttribute ProductSubscriptionModel model,
                                                                 ResponseService<String> responseSetIsActive){

        responseSetIsActive = productService.setSubscribeIsActive(model);

        if(responseSetIsActive.isSuccess()){
            return new ResponseMessage(200, "success", "").getResponse();
        } else {
            return new ResponseMessage(500, "false", "재시도해주세요.").getResponse();
        }
    }




    // 상품 설정
    @GetMapping("/items")
    public String productItemsList(Map<String, Object> reqMap, Model model) {
        List<ProductItemsModel> lsData =  productService.getProductItemsList();
        model.addAttribute("lsData",lsData);

        return "ex_function/product/product_items";
    }

    // 상품 추가 화면
    @GetMapping("/items/register")
    public String productItemsListRegister(Model model,
                                           @RequestParam(defaultValue = "") String type,
                                           @RequestParam(defaultValue = "-1") Integer id,
                                           ProductItemsModel productItemsModel) {

        model.addAttribute("type",type);

        if("edit".equals(type)){
            productItemsModel = productService.getProductItemsDetail(id);
            model.addAttribute("resModel",productItemsModel);
            model.addAttribute("urlType","update");
        }else{
            model.addAttribute("urlType","register");
        }

        return "ex_function/product/product_items_register";
    }

    // INSERT 상품
    @PostMapping("/items/register")
    public String productItemsListInsert(Map<String, Object> reqMap,
                                         ProductItemsModel model ) {
        productService.insertProductItem(model);
        return "redirect:/product/items";
    }

    // UPDATE 상품
    @PostMapping("/items/update")
    @Transactional(rollbackOn = Exception.class)
    public String productItemsListUpdate(Map<String, Object> reqMap,
                                         ProductItemsModel model ) throws Exception{

        //기존 상품 값을 보관하기위해 수정시 삭제 후 새로등록
        if(!productService.deleteProductItem(model.getProductItemsId())){
            throw new Exception("기존 상품 삭제 실패");
        }
        if(!productService.insertProductItem(model)){
            throw new Exception("상품 새로 추가 실패");
        }

        //        productService.updateProductItem(model);  // 결제시 검증을 위해 기존 값은 보관되어어 야함
        return "redirect:/product/items";
    }

    // DELETE 상품
    @GetMapping("/items/delete")
    public String productItemsListDelete(@RequestParam(defaultValue = "-1") Integer id){
        productService.deleteProductItem(id);
        return "redirect:/product/items";
    }

    // 상품 공개 여부 변경
    @PostMapping("/items/setActive")
    public ResponseEntity<HttpMessageModel> setProductItemsIsActive(@ModelAttribute ProductItemsModel model,
                                                                    ResponseService<String> responseSetIsActive){

        responseSetIsActive = productService.setProductItemIsActive(model);

        if(responseSetIsActive.isSuccess()){
            return new ResponseMessage(200, "success", "").getResponse();
        } else {
            return new ResponseMessage(500, "false", "재시도해주세요.").getResponse();
        }
    }
}
