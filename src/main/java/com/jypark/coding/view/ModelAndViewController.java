package com.jypark.coding.view;

import com.jypark.coding.domain.users.dto.UserGetDTO;
import com.jypark.coding.domain.users.dto.UserLoginRequestDTO;
import com.jypark.coding.domain.users.service.UserService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class ModelAndViewController {

    private final UserService userService;

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("/loginPage.html");
    }

    @PostMapping("/login")
    public ModelAndView loginIntoIdPassword(@ModelAttribute UserLoginRequestDTO request) {
        if(request.isIdNull() || request.isPasswordNull()) {
            return null;
        }
        final UserGetDTO forLogin = userService.getForLogin(request);
        if(forLogin.getStatus().is4xxClientError()) {
            return login();
        }

        ModelAndView modelAndView = new ModelAndView("/channel");
        return modelAndView;
    }

    @PostMapping("/logout")
    public ModelAndView logout(HttpServletRequest httpServletrequest) {
        userService.logout(httpServletrequest);
        return login();
    }
}
