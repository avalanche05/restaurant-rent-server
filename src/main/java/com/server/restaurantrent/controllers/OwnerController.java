package com.server.restaurantrent.controllers;

import com.server.restaurantrent.models.Owner;
import com.server.restaurantrent.repo.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class OwnerController {

    @Autowired
    private OwnerRepository ownerRepository;

    @PostMapping("/owner/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String ownerAdd(@RequestParam String email,@RequestParam String password, Model model){
        for(Owner temp : ownerRepository.findAll()){
            if(email.contains(temp.getEmail())){
                return "Пользователь уже зарегистрирован";
            }
        }
        Owner owner = new Owner(email,password);
        return ownerRepository.save(owner).getId()+"";
    }
    @PostMapping("/owner/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String ownerLogin(@RequestParam String email,@RequestParam String password, Model model){
        Owner owner = new Owner(email,password);
        Iterable<Owner> owners = ownerRepository.findAll();
        for (Owner temp:owners) {
            if(temp.getEmail().equals(email) && temp.getPassword().equals(password)){
                return temp.getId()+"";
            }
        }
        return "Not found";
    }
    @PostMapping("/owner/get")
    @ResponseStatus(HttpStatus.FOUND)
    public String ownerGet(@RequestParam long id,Model model){
        if (ownerRepository.existsById(id)){
            return ownerRepository.findById(id).get().toString();
        }
        else {
            return "Пользователь не найден";
        }

    }
    @GetMapping("/")
    public String test(){
        return "test";
    }
}
