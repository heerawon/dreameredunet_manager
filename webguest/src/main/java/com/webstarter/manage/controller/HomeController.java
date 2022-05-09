package com.webstarter.manage.controller;

import com.webstarter.manage.configure.LoginUser;
import com.webstarter.manage.security.Role;
import com.webstarter.manage.security.model.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value = "")
public class HomeController {

    
    //메인페이지
    @GetMapping(value = "/") //연결 될 주소 이름
    public String home(Model model,
                       @LoginUser SessionUser user) {
        if(user == null) {
            return "redirect:/auth/login";
        }else{
            if(user.getRole()== Role.ADMIN){
                return "redirect:/board/list";
            }else{
                return "redirect:/mypage/info";
            }
        }
    }

    //로그인
    @GetMapping(value = "/login") //연결 될 주소 이름
    public ModelAndView login(ModelAndView mnv) {
        mnv.setViewName("login"); //html 파일이름
        return mnv;
    }

    //상품 목록
    @GetMapping(value = "/item/list")
    public ModelAndView itemList(ModelAndView mnv) {
        mnv.setViewName("/item/item_list");
        return mnv;
    }

    //상품 상세
    @GetMapping(value = "/item/detail")
    public ModelAndView itemDetail(ModelAndView mnv) {
        mnv.setViewName("/item/item_detail");
        return mnv;
    }

    //상품 수정
    @GetMapping(value = "/item/detail/update")
    public ModelAndView itemDetailUpdate(ModelAndView mnv) {
        mnv.setViewName("/item/item_detail_update");
        return mnv;
    }

    //상품 상세2 - 반려
    @GetMapping(value = "/item/detail/return")
    public ModelAndView itemDetailReturn(ModelAndView mnv) {
        mnv.setViewName("/item/item_detail_return");
        return mnv;
    }

    //상품 상세2 - 반려 수정
    @GetMapping(value = "/item/return/update")
    public ModelAndView itemReturnUpdate(ModelAndView mnv) {
        mnv.setViewName("/item/item_return_update");
        return mnv;
    }

    //수업 등록 - 라이브
    @GetMapping(value = "/class/live/register")
    public ModelAndView classLiveRegister(ModelAndView mnv) {
        mnv.setViewName("/item/class_live_register");
        return mnv;
    }

    //수업 상세 - 라이브
    @GetMapping(value = "/class/live/detail")
    public ModelAndView classLiveDetail(ModelAndView mnv) {
        mnv.setViewName("/item/class_live_detail");
        return mnv;
    }

    //수업 수정 - 라이브
    @GetMapping(value = "/class/live/update")
    public ModelAndView classLiveUpdate(ModelAndView mnv) {
        mnv.setViewName("/item/class_live_update");
        return mnv;
    }

    //수업 등록 - 스트리밍
    @GetMapping(value = "/class/streaming/register")
    public ModelAndView classStreamingRegister(ModelAndView mnv) {
        mnv.setViewName("/item/class_streaming_register");
        return mnv;
    }

    //수업 상세 - 스트리밍
    @GetMapping(value = "/class/streaming/detail")
    public ModelAndView classStreamingDetail(ModelAndView mnv) {
        mnv.setViewName("/item/class_streaming_detail");
        return mnv;
    }

    //판매자 회원 목록
    @GetMapping(value = "/seller/list")
    public ModelAndView sellerList(ModelAndView mnv) {
        mnv.setViewName("/seller/seller_list");
        return mnv;
    }

    //판매자 회원 상세1 (판매자 정보)
    @GetMapping(value = "/seller/detail")
    public ModelAndView sellerDetail(ModelAndView mnv) {
        mnv.setViewName("/seller/seller_detail");
        return mnv;
    }

    //판매자 회원 상세2 (상품목록)
    @GetMapping(value = "/seller/item/list")
    public ModelAndView sellerItemList(ModelAndView mnv) {
        mnv.setViewName("/seller/seller_item_list");
        return mnv;
    }

    //판매자 회원 등록
    @GetMapping(value = "/seller/register")
    public ModelAndView sellerRegister(ModelAndView mnv) {
        mnv.setViewName("/seller/seller_register");
        return mnv;
    }

    //판매자 회원 수정
    @GetMapping(value = "/seller/update")
    public ModelAndView sellerUpdate(ModelAndView mnv) {
        mnv.setViewName("/seller/seller_update");
        return mnv;
    }

    //사용자 회원 목록
    @GetMapping(value = "/user/list")
    public ModelAndView userList(ModelAndView mnv) {
        mnv.setViewName("/user/user_list");
        return mnv;
    }

    //사용자 회원 상세1 (사용자 정보)
    @GetMapping(value = "/user/detail")
    public ModelAndView userDetail(ModelAndView mnv) {
        mnv.setViewName("/user/user_detail");
        return mnv;
    }

    //사용자 회원 상세2 (구매 내역)
    @GetMapping(value = "/user/purchase/list")
    public ModelAndView userPurchaseList(ModelAndView mnv) {
        mnv.setViewName("/user/user_purchase_list");
        return mnv;
    }

    //사용자 회원 상세2 (적립금 내역)
    @GetMapping(value = "/user/point/list")
    public ModelAndView userPointList(ModelAndView mnv) {
        mnv.setViewName("/user/user_point_list");
        return mnv;
    }

    //사용자 회원 상세 - 적립금 추가
    @GetMapping(value = "/user/point/register")
    public ModelAndView userPointRegister(ModelAndView mnv) {
        mnv.setViewName("/user/user_point_register");
        return mnv;
    }

    //사용자 회원 상세 - 적립금 상세
    @GetMapping(value = "/user/point/detail")
    public ModelAndView userPointDetail(ModelAndView mnv) {
        mnv.setViewName("/user/user_point_detail");
        return mnv;
    }

    //메인 슬라이드
    @GetMapping(value = "/main/slide")
    public ModelAndView mainSlide(ModelAndView mnv) {
        mnv.setViewName("/main/main_slide");
        return mnv;
    }

    //메인 슬라이드 등록
    @GetMapping(value = "/main/slide/register")
    public ModelAndView mainSlideRegister(ModelAndView mnv) {
        mnv.setViewName("/main/main_slide_register");
        return mnv;
    }

    //메인 슬라이드 상세
    @GetMapping(value = "/main/slide/detail")
    public ModelAndView mainSlideDetail(ModelAndView mnv) {
        mnv.setViewName("/main/main_slide_detail");
        return mnv;
    }

    //카테고리
    @GetMapping(value = "/category")
    public ModelAndView category(ModelAndView mnv) {
        mnv.setViewName("/main/category");
        return mnv;
    }

    //카테고리 정렬
    @GetMapping(value = "/category/sort")
    public ModelAndView categorySort(ModelAndView mnv) {
        mnv.setViewName("/main/category_sort");
        return mnv;
    }

    //이달의 이벤트 수업
    @GetMapping(value = "/event/class")
    public ModelAndView eventClass(ModelAndView mnv) {
        mnv.setViewName("/main/event_class");
        return mnv;
    }

    //이달의 이벤트 수업 등록
    @GetMapping(value = "/event/class/register")
    public ModelAndView eventClassRegister(ModelAndView mnv) {
        mnv.setViewName("/main/event_class_register");
        return mnv;
    }

    //추천 수업
    @GetMapping(value = "/recommend/class")
    public ModelAndView recommendtClass(ModelAndView mnv) {
        mnv.setViewName("/main/recommend_class");
        return mnv;
    }

    //추천 수업 등록
    @GetMapping(value = "/recommend/class/register")
    public ModelAndView recommendClassRegister(ModelAndView mnv) {
        mnv.setViewName("/main/recommend_class_register");
        return mnv;
    }

    //공지사항
    @GetMapping(value = "/notice/list")
    public ModelAndView noticeList(ModelAndView mnv) {
        mnv.setViewName("/bulletin/notice_list");
        return mnv;
    }

    //공지사항 등록
    @GetMapping(value = "/notice/register")
    public ModelAndView noticeRegister(ModelAndView mnv) {
        mnv.setViewName("/bulletin/notice_register");
        return mnv;
    }

    //공지사항 상세
    @GetMapping(value = "/notice/detail")
    public ModelAndView noticeDetail(ModelAndView mnv) {
        mnv.setViewName("/bulletin/notice_detail");
        return mnv;
    }

    //이벤트
    @GetMapping(value = "/event/list")
    public ModelAndView eventList(ModelAndView mnv) {
        mnv.setViewName("/bulletin/event_list");
        return mnv;
    }

    //이벤트 등록
    @GetMapping(value = "/event/register")
    public ModelAndView eventRegister(ModelAndView mnv) {
        mnv.setViewName("/bulletin/event_register");
        return mnv;
    }

    //이벤트 상세
    @GetMapping(value = "/event/detail")
    public ModelAndView eventDetail(ModelAndView mnv) {
        mnv.setViewName("/bulletin/event_detail");
        return mnv;
    }

    //판매현황
    @GetMapping(value = "/sale/list")
    public ModelAndView saleList(ModelAndView mnv) {
        mnv.setViewName("/order/sale_list");
        return mnv;
    }

    //판매현황 검색 결과 없을 시
    @GetMapping(value = "/sale/search/empty")
    public ModelAndView saleSearchEmpty(ModelAndView mnv) {
        mnv.setViewName("order/sale_search_empty");
        return mnv;
    }

    //판매현황 상세
    @GetMapping(value = "/sale/detail")
    public ModelAndView saleDetail(ModelAndView mnv) {
        mnv.setViewName("/order/sale_detail");
        return mnv;
    }




}