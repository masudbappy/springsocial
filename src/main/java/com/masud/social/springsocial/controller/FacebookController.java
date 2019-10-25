package com.masud.social.springsocial.controller;

import com.masud.social.springsocial.commons.model.UserInfo;
import com.masud.social.springsocial.service.FacebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class FacebookController {

    @Autowired
    private FacebookService facebookService;

    @GetMapping(value = "/facebookLogin")
    public RedirectView facebookView() {
        RedirectView redirectView = new RedirectView();
        String url = facebookService.facebookLogin();
        System.out.println(url);
        redirectView.setUrl(url);
        return redirectView;
    }

    @GetMapping(value = "/facebook")
    public String facebook(@RequestParam("code") String code) {
        String accessToken = facebookService.getFacebookAccessToken(code);
        return "redirect:/facebookprofiledata/" + accessToken;
    }

    @GetMapping(value = "/facebookprofiledata/{accessToken:.+}")
    public String facebookprofiledata(@PathVariable String accessToken, Model model) {
        User user = facebookService.getFacebookUserProfile(accessToken);
        UserInfo userInfo = new UserInfo(user.getFirstName(), user.getLastName(), user.getEmail());
        model.addAttribute("user", userInfo);
        return "view/userProfile";
    }
}
