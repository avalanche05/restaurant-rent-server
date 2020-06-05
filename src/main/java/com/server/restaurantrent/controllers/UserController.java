package com.server.restaurantrent.controllers;


import com.server.restaurantrent.models.ConfirmationToken;
import com.server.restaurantrent.models.User;
import com.server.restaurantrent.repo.ConfirmationTokenRepository;
import com.server.restaurantrent.repo.UserRepository;
import com.server.restaurantrent.services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping("/user/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public User signUpUser(@RequestParam String email, @RequestParam String password, Model model){
        for (User temp : userRepository.findAll()){
            if(email.equals(temp.getEmail())){
                return new User();
            }
        }
        ConfirmationToken confirmationToken = new ConfirmationToken(new User(email,password));
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("chand312902@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
                +"http://localhost:8082/confirm-account?token="+confirmationToken.getConfirmationToken());

        emailSenderService.sendEmail(mailMessage);
        User user = new User(email,password);
        return userRepository.save(user);
    }
    @PostMapping("/user/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User loginUser(@RequestParam String email,@RequestParam String password, Model model){
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

