package com.english_app.controller;

import com.english_app.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    @Autowired
    private UserDetailService userDetailService;

    @GetMapping
    public String index(Model model) {
//        model.addAttribute("message", "Role Guest");
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(authentication);
        return "guest/home";
    }

    @GetMapping("/login")
    public String login() {
        return "SignInOrRegister/login";
    }

    @GetMapping("/oauth2/login/success")
    public String oauth2Login(OAuth2AuthenticationToken authentication) {
        this.userDetailService.loginFromOauth2(authentication);
        return "redirect:/";
    }
}
