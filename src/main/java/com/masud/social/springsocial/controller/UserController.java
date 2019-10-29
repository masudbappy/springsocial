package com.masud.social.springsocial.controller;

import com.masud.social.springsocial.commons.model.UserInfo;
import com.masud.social.springsocial.securityservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/dashboard")
    public String userDashboard(Principal principal, Model model){
        UserInfo dbUser = userService.findByEmail(principal.getName());
        model.addAttribute("user", dbUser);
        return "view/userProfile";
    }
}
