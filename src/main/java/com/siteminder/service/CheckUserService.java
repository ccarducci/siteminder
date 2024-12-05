package com.siteminder.service;

import com.siteminder.entity.UserEntiy;
import com.siteminder.model.UserCheckReponse;
import com.siteminder.repository.UserCheckRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CheckUserService {

    Logger logger = LoggerFactory.getLogger(CheckUserService.class);

    UserCheckRepository userCheckRepository;

    @Value("password-salt")
    private String passwordSalt;

    public CheckUserService(UserCheckRepository userCheckRepository) {
        this.userCheckRepository = userCheckRepository;
    }

    public ResponseEntity<UserCheckReponse> checkUser(String username, String password, String uuid) {
        UserCheckReponse reponse = new UserCheckReponse();
        logger.info("{} Finding username: {}", uuid, username);
        UserEntiy user = userCheckRepository.findByUsername(username);
        if (user == null) {
            reponse.setMessage("User not found: " + username);
            logger.error("{} Not found username: {}", uuid, username);
            return new ResponseEntity<>(reponse, HttpStatus.NOT_FOUND);
        }
        logger.info("{} Finded username: {}", uuid, username);
        logger.info("{} Check password for username: {}", uuid, username);
        // Verify the password
        boolean isPasswordCorrect = BCrypt.checkpw(password, user.getPassword());
        if (!isPasswordCorrect) {
            reponse.setMessage("User password not correct for username:" + username);
            logger.error("{} Password not correct for username: {}", uuid, username);
            return new ResponseEntity<>(reponse, HttpStatus.UNAUTHORIZED);
        }
        logger.info("{} Password correct for username: {}", uuid, username);
        reponse.setMessage("Password correct for username: " + username);
        return new ResponseEntity<>(reponse, HttpStatus.OK);
    }

    public static void main(String[] args) {
        // Original password
        String password = "my_secure_password";

        // Generate salt
        String salt = BCrypt.gensalt();
        System.out.println("Salt generates: " + salt);

        // Hash the password
        String hashedPassword = BCrypt.hashpw(password, salt);
        System.out.println("Hashed Password: " + hashedPassword);

        // Verify the password
        boolean isPasswordCorrect = BCrypt.checkpw(password, hashedPassword);

        if (isPasswordCorrect) {
            System.out.println("Password matches!");
        } else {
            System.out.println("Password does not match.");
        }
    }
}
