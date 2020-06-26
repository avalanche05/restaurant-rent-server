package com.server.restaurantrent.controllers;

import com.server.restaurantrent.models.AuthToken;
import com.server.restaurantrent.models.Owner;
import com.server.restaurantrent.models.User;
import com.server.restaurantrent.repo.AuthTokenRepository;
import com.server.restaurantrent.repo.OwnerRepository;
import com.server.restaurantrent.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

// контроллер запросов связанных с аккаунтом
@Controller
public class AccountController {

    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private UserRepository userRepository;

    // метод обрабатывает запрос подтверждения электронной почты
    @GetMapping("/account/confirm/{uuid}")
    public String confirmAccount(@PathVariable String uuid, Model model) {
        // пытаемся найти токен авторизации по uuid
        AuthToken authToken = authTokenRepository.findAuthTokenByUuid(uuid);
        // проверяем, сушествует ли такой токен
        if (authToken != null) {
            // пытаемся найти пользователя, id которого указан в токене
            Optional<User> optionalUser = userRepository.findById(authToken.getIdUser());
            // проверяем, существует ли такой пользователь
            if (optionalUser.isPresent()) {
                // переводим Optional<User> в User
                User user = optionalUser.get();
                // ставим значение авторизации пользователя как положительное
                user.setAuth(true);
                // сохраняем изменения
                userRepository.save(user);
                // возвращаем страницу успешной авторизации
                return "auth-success";
            }
            // пытаемся найти владельца, id которого указан в токене
            Optional<Owner> optionalOwner = ownerRepository.findById(authToken.getIdUser());
            // проверяем, существует ли такой владелец
            if (optionalOwner.isPresent()) {
                // переводим Optional<Owner> в Owner
                Owner owner = optionalOwner.get();
                // ставим значение авторизации владельца как положительное
                owner.setAuth(true);
                // сохраняем изменения
                ownerRepository.save(owner);
                // возвращаем страницу успешной авторизации
                return "auth-success";
            }
        }
        // возвращаем страницу ошибки авторизации
        return "auth-bad";
    }
}
