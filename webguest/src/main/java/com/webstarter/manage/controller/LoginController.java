package com.webstarter.manage.controller;

import com.webstarter.manage.configure.LoginUser;
import com.webstarter.manage.security.model.SessionUser;
import com.webstarter.manage.security.model.UserModel;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "auth")
@Log4j2
public class LoginController {
//    @Autowired
//    private AuthService userService;



    @GetMapping(value = { "login"})
    public ModelAndView getLoginPage(@LoginUser SessionUser user) {

        if(user != null) {
            log.info("getLoginPage::::: Sessionuser :"+user.toString());
        }else{
            log.info("getLoginPage::::: Sessionuser is Empty");

        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ex_function/login");
        return modelAndView;
    }

    @GetMapping(value = "failed")
    @ResponseBody
    public String getFailedPage() {
        return "Login failed";
    }

    @PostMapping("login")
    public String getLoginPageDo(UserModel user, Model model) {
        log.info("user :::::"+user);

//        UserDetails userDetails = userService.loadUserByUsername(user.getUserId());
//        boolean result = userService.userVerification(user);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info(":::::: "+principal.toString());
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //provider.authenticate(auth);

//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        UserDetails userDetails = (UserDetails)principal;
//        String username = ((UserDetails) principal).getUsername();

//        model.addAttribute("userDetails",userDetails);
//        model.addAttribute("userName",username);
        model.addAttribute("adminMessage","로그인 성공 ! ");
        log.info("userName"+model.getAttribute("userName"));
        log.info("adminMessage"+model.getAttribute("adminMessage"));

//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("security_user/home");
//        return modelAndView;
        return "redirect:/intro/loginComplete";
    }

    @GetMapping("registration")
    public ModelAndView getRegistrationPage() {
        ModelAndView modelAndView = new ModelAndView();
        UserModel user = new UserModel();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("security_user/registration");
        return modelAndView;
    }

    @PostMapping("registration")
    public ModelAndView createNewUser(UserModel user, BindingResult bindingResult) {

        log.info("user:::::::::"+user.toString());

        ModelAndView modelAndView = new ModelAndView();

//        UserDetails userExists = userService.loadUserByLoginId(user.getUserId());
//        if (userExists != null) {
//            bindingResult
//                    .rejectValue("userId", "error.userId",
//                            "There is already a user registered with the loginId provided");
//        }

//        userService.saveUser(user,"ADMIN");
        log.info("bindingResult.saveUserAdmin()::::::");

        modelAndView.addObject("successMessage", "User has been registered successfully");
        modelAndView.addObject("user", new UserModel());
        modelAndView.setViewName("security_user/registration");

        return modelAndView;
    }

    @GetMapping("home")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
//        log.info(userPrincipal.toString());
//        modelAndView.addObject("userName", "Welcome " + userPrincipal.getName() + " (" + userPrincipal.getId() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("security_user/home");
        return modelAndView;
    }

    @GetMapping("exception")
    public ModelAndView getUserPermissionExceptionPage() {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("security_user/access-denied");

        return mv;
    }
}
