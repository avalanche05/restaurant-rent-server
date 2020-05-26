package com.server.restaurantrent.controllers;

import com.server.restaurantrent.models.Board;
import com.server.restaurantrent.models.Rent;
import com.server.restaurantrent.models.Restaurant;
import com.server.restaurantrent.repo.BoardRepository;
import com.server.restaurantrent.repo.RentRepository;
import com.server.restaurantrent.repo.RestaurantRepository;
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

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private BoardRepository boardRepository;

    @PostMapping("/rent/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String rentAdd(@RequestParam Long idUser, @RequestParam String idTables,String date,String time,Long idOwner, Model model){
        Rent order = new Rent(idUser,idTables,date,idOwner,time);
        for (Rent temp : orderRepository.findAll()){
            if((temp.getDate()).equals(date)){
                double hoursTemp = Integer.parseInt(temp.getTime().split(":")[0]) + (Double.parseDouble(temp.getTime().split(":")[1])/60);
                double hoursUser = Integer.parseInt(time.split(":")[0]) + (Double.parseDouble(time.split(":")[1])/60);
                if(Math.abs(hoursTemp - hoursUser) < 1.5){
                    return "Столы уже забронированы";
                }

            }
        }

        for(Board temp : boardRepository.findAll()){
            System.out.println(idTables.split(",")[0].concat("["));
            if(temp.getId() == Long.parseLong(idTables.split(",")[0].concat("["))){
                order.setRestaurant(restaurantRepository.findById(temp.getIdRestaurant()).get());
                break;
            }
        }

        orderRepository.save(order);
        return "ЗАКАЗ СОЗДАН";
    }
    @PostMapping("/rent/get")
    public ArrayList<Rent> rentGet(@RequestParam Long idOwner,Model model){
        System.out.println(idOwner);
        ArrayList<Rent> rents = new ArrayList<>();
        for(Rent temp : orderRepository.findAll()){
           if(temp.getIdOwner().equals(idOwner) ){
                rents.add(temp);
            }
        }
        return rents;
   }
}
