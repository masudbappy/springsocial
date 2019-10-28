package com.masud.social.springsocial.securityservice;

import com.masud.social.springsocial.commons.model.UserInfo;

public interface UserService {
    UserInfo save(UserInfo userInfo);

    UserInfo findByEmail(String email);

    void update(UserInfo dbUser);
}
