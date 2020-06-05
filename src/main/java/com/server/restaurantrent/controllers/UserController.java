package com.server.restaurantrent.controllers;



import com.server.restaurantrent.models.AuthToken;
import com.server.restaurantrent.models.User;
import com.server.restaurantrent.repo.AuthTokenRepository;
import com.server.restaurantrent.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import javax.swing.plaf.UIResource;
import java.util.Properties;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthTokenRepository authTokenRepository;

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

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("prostovana304@gmail.com","Ineznay555");
                    }
                });
        User user = new User(email,password);
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("prostovana304@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("avalanche4533@gmail.com"));
            message.setSubject("Подтверждение электронной почты");
            String uniqueToken = UUID.randomUUID().toString();
            message.setText("Добро пожаловть!," +
                    "\n\n Чтобы подтвердить адрес электронной почты перейдите по ссылке https://restaurant-rent-server.herokuapp.com/accuont/confirm" + uniqueToken);



            user = userRepository.save(user);

            Transport.send(message);

            authTokenRepository.save(new AuthToken(uniqueToken,user.getId()));


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


        return user;
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

    @GetMapping("/")
    public String test(){
        return "test";
    }




}

