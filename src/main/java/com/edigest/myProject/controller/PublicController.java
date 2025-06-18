package com.edigest.myProject.controller;

import com.edigest.myProject.entity.User;
import com.edigest.myProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user)
    {
        userService.saveNewUser(user);
    }
}
