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
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AccountController {

    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/account/confirm/{token}")
    public String confirmAccount(@PathVariable String token, Model model){
        for (AuthToken temp : authTokenRepository.findAll()){
            if(temp.getUuid().equals(token)){
                for (User userTemp : userRepository.findAll()){
                    if(userTemp.getId().equals(temp.getIdUser())){
                        userTemp.setAuth(true);
                        userRepository.save(userTemp);
                        return "auth_success";
                    }
                }
                for (Owner ownerTemp : ownerRepository.findAll()){
                    if(ownerTemp.getId().equals(temp.getIdUser())){
                        ownerTemp.setAuth(true);
                        ownerRepository.save(ownerTemp);
                        return "auth_success";
                    }
                }
            }
        }
        return "Срок вашего токена истёк";
    }
}
