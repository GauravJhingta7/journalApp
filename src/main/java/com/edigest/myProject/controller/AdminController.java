package com.edigest.myProject.controller;

import com.edigest.myProject.entity.User;
import com.edigest.myProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers()
    {
        List<User> all_users=userService.getAll();
        if(all_users!=null&&!all_users.isEmpty())
        {
            return new ResponseEntity<>(all_users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public void createAdmin(@RequestBody User user)
    {
        userService.saveAdmin(user);
    }
}
