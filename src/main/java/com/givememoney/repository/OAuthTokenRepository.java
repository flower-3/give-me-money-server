package com.givememoney.repository;

import com.givememoney.entity.OAuthToken;
import com.givememoney.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuthTokenRepository extends JpaRepository<OAuthToken, Long> {
    OAuthToken findByUser(User user);
}
