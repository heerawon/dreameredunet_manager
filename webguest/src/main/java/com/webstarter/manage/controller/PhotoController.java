package com.webstarter.manage.controller;

import com.webstarter.manage.model.ProductItemsModel;
import com.webstarter.manage.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("photo")
public class PhotoController {
    @Resource
    private ProductService productService;

    // 상품 금액 설정
    //    @GetMapping("/items")
    //    public String productItemsList(Map<String, Object> reqMap, Model model) {
    //        List<ProductItemsModel> lsData =  productService.getProductItemsList();
    //        model.addAttribute("lsData",lsData);
    //
    //        return "ex_function/photo/product_items";
    //    }
}
