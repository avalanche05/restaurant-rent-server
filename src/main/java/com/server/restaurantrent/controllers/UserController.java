package com.server.restaurantrent.controllers;



import com.server.restaurantrent.models.User;
import com.server.restaurantrent.repo.UserRepository;
import com.server.restaurantrent.services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    public JavaMailSender emailSender;

    @PostMapping("/user/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public User signUpUser(@RequestParam String email, @RequestParam String password, Model model){
        for (User temp : userRepository.findAll()){
            if(email.equals(temp.getEmail())){
                return new User();
            }
        }

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("This is the test message for testing gmail smtp server using spring mail");
        mailMessage.setFrom(email);
        mailMessage.setText("This is the test message for testing gmail smtp server using spring mail. \n" +
                "Thanks \n Regards \n Saurabh ");

        emailSender.send(mailMessage);
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

