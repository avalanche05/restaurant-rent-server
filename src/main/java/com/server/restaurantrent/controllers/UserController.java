package com.server.restaurantrent.controllers;

import com.server.restaurantrent.models.Owner;
import com.server.restaurantrent.models.User;
import com.server.restaurantrent.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public User signUpUser(@RequestParam String email, @RequestParam String password, Model model){
        for (User temp : userRepository.findAll()){
            if(email.contains(temp.getEmail())){
                return new User("edc","ihhg");
            }
        }
        User user = new User(email,password);
        return userRepository.save(user);
    }
    @PostMapping("/user/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User loginUser1(@RequestParam String email,@RequestParam String password, Model model){
        User user = new User(email,password);
        Iterable<User> users = userRepository.findAll();
        for (User temp:users) {
            if(temp.getEmail().equals(email) && temp.getPassword().equals(password)){
                return temp;
            }
        }
        return new User();
    }

}

