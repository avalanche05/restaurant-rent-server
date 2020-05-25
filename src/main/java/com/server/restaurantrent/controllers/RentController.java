package com.server.restaurantrent.controllers;

import com.server.restaurantrent.models.Rent;
import com.server.restaurantrent.repo.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class RentController {

    @Autowired
    private RentRepository orderRepository;

    @PostMapping("/rent/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String rentAdd(@RequestParam Long idUser, @RequestParam String idTables,String date,Long idOwner, Model model){
        Rent order = new Rent(idUser,idTables,date,idOwner);
        orderRepository.save(order);
        return "ЗАКАЗ СОЗДАН";
    }
    @PostMapping("/rent/get")
    public ArrayList<String> rentGet(@RequestParam Long idOwner,Model model){
        System.out.println(idOwner);
        ArrayList<String> rents = new ArrayList<>();
        for(Rent temp : orderRepository.findAll()){
           if(temp.getIdOwner().equals(idOwner) ){
                rents.add(temp.getIdTables());
            }
        }
        return rents;
   }
}
