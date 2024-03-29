package com.masud.social.springsocial.Util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

    static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String getEncodedPassword(String password){
        return passwordEncoder.encode(password);
    }
}
