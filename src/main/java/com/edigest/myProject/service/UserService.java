package com.edigest.myProject.service;
import com.edigest.myProject.entity.User;
import com.edigest.myProject.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepo userRepo;
    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public void saveNewUser(User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepo.save(user);
    }
    public void saveUser(User user)
    {
        userRepo.save(user);
    }
    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepo.save(user);
    }
    public List<User> getAll()
    {
        return userRepo.findAll();
    }
    public Optional<User> findById(ObjectId myId)
    {
        return userRepo.findById(myId);
    }
    public void deleteById(ObjectId myId)
    {
        userRepo.deleteById(myId);
    }
    public void deleteByUsername(String username)
    {
        userRepo.deleteByUsername(username);
    }
    public User findByUsername(String username)
    {
        return userRepo.findByUsername(username);
    }

}
