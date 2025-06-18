package com.edigest.myProject.controller;

import com.edigest.myProject.entity.User;
import com.edigest.myProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User userInDb = userService.findByUsername(username);
        userInDb.setUsername(user.getUsername());
        userInDb.setPassword(user.getPassword());
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody User user)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userService.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
