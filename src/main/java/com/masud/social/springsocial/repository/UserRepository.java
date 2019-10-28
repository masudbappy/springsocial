package com.masud.social.springsocial.repository;

import com.masud.social.springsocial.commons.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {

    UserInfo findByEmailAndEnabled(String email, boolean enabled);

    UserInfo findByEmail(String email);
}
