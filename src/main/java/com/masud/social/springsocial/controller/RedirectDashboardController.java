package com.masud.social.springsocial.controller;

import com.masud.social.springsocial.commons.model.UserInfo;
import com.masud.social.springsocial.securityservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class RedirectDashboardController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/redirectdashboard")
    public String redirectdashboard(Principal principal){
        String redirectUrl = "";
        UserInfo dbUser = userService.findByEmail(principal.getName());
        if (dbUser!=null && StringUtils.hasText(dbUser.getRole())){
            if (dbUser.getRole().equalsIgnoreCase("ADMIN")){
                redirectUrl = "redirect:/admin/dashboard";
            }else if ((dbUser.getRole().equalsIgnoreCase("USER"))){
                redirectUrl = "redirect:/user/dashboard";
            }
        }
        return redirectUrl;

    }
}
