package com.siteminder.repository;

import com.siteminder.entity.UserEntiy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCheckRepository extends JpaRepository<UserEntiy, Long> {
    UserEntiy findByUsername(String username);
}
