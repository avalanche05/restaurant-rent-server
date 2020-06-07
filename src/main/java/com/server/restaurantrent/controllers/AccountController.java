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
    @GetMapping("/account/confirm/{token}")
    public String confirmAccount(@PathVariable String token, Model model) {
        // проходим циклом по всем авторизационным токенам
        for (AuthToken temp : authTokenRepository.findAll()) {
            // проверяем существует ли заданный токен в базе данных
            if (temp.getUuid().equals(token)) {
                // проходим циклом по всем пользователям
                for (User userTemp : userRepository.findAll()) {
                    // если авторизационный токен относится к этому пользователя
                    if (userTemp.getId().equals(temp.getIdUser())) {
                        // делаем аккаунт пользователья подтверждённым
                        userTemp.setAuth(true);
                        // сохраняем пользователя в базе данных
                        userRepository.save(userTemp);
                        // возвращаем странницу с информацией о успешном подтверждении почты
                        return "auth_success";
                    }
                }
                // проходим циклом по всем пользователям
                for (Owner ownerTemp : ownerRepository.findAll()) {
                    // если авторизационный токен относится к этому владельцу
                    if (ownerTemp.getId().equals(temp.getIdUser())) {
                        // делаем аккаунт владельца подтверждённым
                        ownerTemp.setAuth(true);
                        // сохраняем владельца в базе данных
                        ownerRepository.save(ownerTemp);
                        // возвращаем странницу с информацией о успешном подтверждении почты
                        return "auth_success";
                    }
                }
            }
        }
        // возвращаем пустую странницу
        return "/";
    }
}
