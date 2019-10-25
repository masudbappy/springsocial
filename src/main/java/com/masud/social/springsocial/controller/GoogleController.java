package com.masud.social.springsocial.controller;

import com.masud.social.springsocial.commons.model.UserInfo;
import com.masud.social.springsocial.service.FacebookService;
import com.masud.social.springsocial.service.GoogleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.User;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.google.api.userinfo.GoogleUserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class GoogleController {
    @Autowired
    private GoogleService googleService;

    @GetMapping(value = "/googleLogin")
    public RedirectView googleView() {
        RedirectView redirectView = new RedirectView();
        String url = googleService.googleLogin();
        System.out.println(url);
        redirectView.setUrl(url);
        return redirectView;
    }

    @GetMapping(value = "/google")
    public String google(@RequestParam("code") String code) {
        String accessToken = googleService.getGoogleAccessToken(code);
        return "redirect:/googleprofiledata/" + accessToken;
    }

    @GetMapping(value = "/googleprofiledata/{accessToken:.+}")
    public String googleprofiledata(@PathVariable String accessToken, Model model) {
        GoogleUserInfo user = googleService.getGoogleUserProfile(accessToken);
        UserInfo userInfo = new UserInfo(user.getFirstName(),user.getLastName(),user.getEmail());
        model.addAttribute("user", userInfo);
        return "view/userProfile";
    }
}
