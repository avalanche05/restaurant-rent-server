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

    @PostMapping("/owner/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public Owner ownerSignUp(@RequestParam String email,@RequestParam String password, Model model){
        for(Owner temp : ownerRepository.findAll()){
            if(email.equals(temp.getEmail())){
                return new Owner();
            }
        }
        Owner owner = new Owner(email,password);
        return ownerRepository.save(owner);
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

    @GetMapping("/")
    public String test(){
        return "test";
    }
}
