package com.masud.social.springsocial.controller;

import com.masud.social.springsocial.commons.model.UserInfo;
import com.masud.social.springsocial.securityservice.SecurityService;
import com.masud.social.springsocial.securityservice.UserService;
import com.masud.social.springsocial.service.GoogleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.google.api.plus.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;

@Controller
public class GoogleController {
    @Autowired
    private GoogleService googleService;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

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
    public String googleprofiledata(@PathVariable String accessToken, Model model, HttpServletRequest request) {
        Person user = googleService.getGoogleUserProfile(accessToken);

        UserInfo dbUser = userService.findByEmail(user.getAccountEmail());
        String role = "USER";
        if (dbUser != null){
            dbUser.setFirstName(user.getGivenName());
            dbUser.setLastName(user.getFamilyName());
            dbUser.setEmail(user.getAccountEmail());
            userService.update(dbUser);
            role = dbUser.getRole();
            model.addAttribute("user",dbUser);
        }else {
            UserInfo userInfo = new UserInfo(user.getGivenName(), user.getFamilyName(), user.getAccountEmail());
            userInfo.setEmail(user.getAccountEmail());
            userInfo.setEnabled(true);
            userInfo.setRole("USER");
            userService.save(userInfo);
            role = userInfo.getRole();
            model.addAttribute("user",userInfo);
        }
        securityService.autoLogin(user.getAccountEmail(),null,role,request);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<? extends GrantedAuthority> grantedAuthorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = grantedAuthorities.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        System.out.println(name);

        return "view/userProfile";
    }
}
