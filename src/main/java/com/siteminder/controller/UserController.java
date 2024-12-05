package com.siteminder.controller;

import com.siteminder.model.UserCheckReponse;
import com.siteminder.service.CheckUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    private CheckUserService checkUserService;

    public UserController(CheckUserService checkUserService) {
        this.checkUserService = checkUserService;
    }

    @PostMapping("check-user")
    public ResponseEntity<UserCheckReponse> checkUser(@RequestHeader("user") String username
            , @RequestHeader("password") String password) {
        String uuid = UUID.randomUUID().toString();
        logger.info("{} Check username: {}", uuid, username);
        return checkUserService.checkUser(username, password, uuid);
    }

}
