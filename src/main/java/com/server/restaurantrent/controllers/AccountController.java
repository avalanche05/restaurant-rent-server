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
        // проходим циклом по всем авторизационным токенам
//        for (AuthToken temp : authTokenRepository.findAll()) {
//            // проверяем существует ли заданный токен в базе данных
//            if (temp.getUuid().equals(uuid)) {
//                // проходим циклом по всем пользователям
//                for (User userTemp : userRepository.findAll()) {
//                    // если авторизационный токен относится к этому пользователя
//                    if (userTemp.getId().equals(temp.getIdUser())) {
//                        // делаем аккаунт пользователья подтверждённым
//                        userTemp.setAuth(true);
//                        // сохраняем пользователя в базе данных
//                        userRepository.save(userTemp);
//                        // возвращаем странницу с информацией о успешном подтверждении почты
//                        return "auth_success";
//                    }
//                }
//                // проходим циклом по всем пользователям
//                for (Owner ownerTemp : ownerRepository.findAll()) {
//                    // если авторизационный токен относится к этому владельцу
//                    if (ownerTemp.getId().equals(temp.getIdUser())) {
//                        // делаем аккаунт владельца подтверждённым
//                        ownerTemp.setAuth(true);
//                        // сохраняем владельца в базе данных
//                        ownerRepository.save(ownerTemp);
//                        // возвращаем странницу с информацией о успешном подтверждении почты
//                        return "auth_success";
//                    }
//                }
//            }
//        }
        AuthToken authToken = authTokenRepository.findAuthTokenByUuid(uuid);
        if(authToken != null){
            Optional<User> optionalUser = userRepository.findById(authToken.getIdUser());
            if (optionalUser.isPresent()){
                User user = optionalUser.get();
                user.setAuth(true);
                userRepository.save(user);
                return "auth_success";
            }
            Optional<Owner> optionalOwner = ownerRepository.findById(authToken.getIdUser());
            if (optionalOwner.isPresent()){
                Owner owner = optionalOwner.get();
                owner.setAuth(true);
                ownerRepository.save(owner);
                return "auth_success";
            }
        }
        // возвращаем пустую странницу
        return "auth_unsuccess";
    }
}
