package com.server.restaurantrent.controllers;

import com.server.restaurantrent.models.Owner;
import com.server.restaurantrent.repo.AuthTokenRepository;
import com.server.restaurantrent.repo.OwnerRepository;
import com.server.restaurantrent.services.AuthMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController

// контроллер запросов связанных с владельцем
public class OwnerController {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private AuthTokenRepository authTokenRepository;

    // метод обрабатывает запрос регистрации владельца
    @PostMapping("/owner/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public Owner ownerSignUp(@RequestParam String email, @RequestParam String password, Model model) {
        // проверяем уникальность электронной почты
        email = email.toLowerCase();
        if (ownerRepository.existsByEmail(email)) {
            return new Owner();
        }
        // сохраняем владельца в базе данных
        Owner owner = new Owner(email, password);
        owner = ownerRepository.save(owner);

        AuthMessageService authMessageService = new AuthMessageService(email, owner.getId(), authTokenRepository);
        authMessageService.sendAuthMessage();

        return owner;
    }

    // метод обрабатывает запрос входа владельца
    @PostMapping("/owner/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Owner ownerLogin(@RequestParam String email, @RequestParam String password) {
        // ищем владельца в базе данных
        Owner owner = ownerRepository.findByEmailAndPassword(email, password);
        if (owner == null) {
            return new Owner();
        }
        return owner;
    }

    // метод обрабатывает запрос состояния электронной почты владельца
    @PostMapping("/owner/confirm")
    public Boolean isConfirm(@RequestParam Long id) {
        return ownerRepository.findById(id).get().getAuth();
    }


}
