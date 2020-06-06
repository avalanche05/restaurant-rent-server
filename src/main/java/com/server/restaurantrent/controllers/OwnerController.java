package com.server.restaurantrent.controllers;

import com.server.restaurantrent.models.AuthToken;
import com.server.restaurantrent.models.Owner;
import com.server.restaurantrent.repo.AuthTokenRepository;
import com.server.restaurantrent.repo.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.UUID;

@RestController
public class OwnerController {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private AuthTokenRepository authTokenRepository;

    @PostMapping("/owner/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public Owner ownerSignUp(@RequestParam String email,@RequestParam String password, Model model){
        for(Owner temp : ownerRepository.findAll()){
            if(email.equals(temp.getEmail())){
                return new Owner();
            }
        }
        Owner owner = new Owner(email,password);
        owner = ownerRepository.save(owner);

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
                        return new PasswordAuthentication("restaurantrent0@gmail.com","ServerPass");
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("restaurantrent0@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Подтверждение электронной почты");
            String uniqueToken = UUID.randomUUID().toString();
            message.setText("Добро пожаловать!," +
                    "\n\n Чтобы подтвердить адрес электронной почты, перейдите по ссылке https://restaurant-rent-server.herokuapp.com/account/confirm/" + uniqueToken);


            Transport.send(message);

            authTokenRepository.save(new AuthToken(uniqueToken,owner.getId()));


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return owner;
    }
    @PostMapping("/owner/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Owner ownerLogin(@RequestParam String email,@RequestParam String password, Model model){
        Owner owner = new Owner(email,password);
        Iterable<Owner> owners = ownerRepository.findAll();
        for (Owner temp:owners) {
            if(temp.getEmail().equals(email) && temp.getPassword().equals(password)){
                return temp;
            }
        }
        return new Owner();
    }

    @PostMapping("/owner/confirm")
    public Boolean isConfirm(@RequestParam Long id){
        return ownerRepository.findById(id).get().getAuth();
    }


}
