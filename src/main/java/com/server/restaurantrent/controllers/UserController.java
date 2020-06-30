package com.server.restaurantrent.controllers;


import com.server.restaurantrent.models.User;
import com.server.restaurantrent.repo.AuthTokenRepository;
import com.server.restaurantrent.repo.UserRepository;
import com.server.restaurantrent.services.AuthMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController

// контроллер запросов связанных с пользователем
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Autowired
    public JavaMailSender emailSender;

    // метод обрабатывает запрос регистрации пользователя
    @PostMapping("/user/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public User signUpUser(@RequestParam String email, @RequestParam String password, Model model) {
        email = email.toLowerCase();
        if (userRepository.existsByEmail(email)) {
            return new User();
        }
        // сохраняем владельца в базе данных
        User user = new User(email, password);
        user = userRepository.save(user);

        AuthMessageService authMessageService = new AuthMessageService(email, user.getId(), authTokenRepository);
        authMessageService.sendAuthMessage();

        return user;
    }

    // метод обрабатывает запрос входа пользователя
    @PostMapping("/user/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User loginUser(@RequestParam String email, @RequestParam String password, Model model) {
        // ищем владельца в базе данных
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user == null) {
            return new User();
        }
        return user;
    }

    // метод обрабатывает запрос состояния электронной почты пользователя
    @PostMapping("/user/confirm")
    public Boolean isConfirm(@RequestParam Long id) {
        return userRepository.findById(id).get().getAuth();
    }

}

