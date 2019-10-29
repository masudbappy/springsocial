package com.masud.social.springsocial.controller;

import com.masud.social.springsocial.commons.model.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = "/")
    public String home(Model model){
        model.addAttribute("user", new UserInfo());
        model.addAttribute("errormessage", "");

        return "view/home";
    }


    @GetMapping(value = "/loginfailure")
    public String loginfailure(Model model){
        model.addAttribute("user", new UserInfo());
        model.addAttribute("errormessage", "Please enter correct email & password");
        return "view/home";
    }
}
