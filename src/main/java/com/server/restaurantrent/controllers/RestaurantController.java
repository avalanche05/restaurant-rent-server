package com.server.restaurantrent.controllers;

import com.server.restaurantrent.models.Owner;
import com.server.restaurantrent.models.Restaurant;
import com.server.restaurantrent.repo.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RestaurantController {

    

    @Autowired
    private RestaurantRepository restaurantRepository;

    @PostMapping("/restaurant/add")
    public String restaurantAdd(@RequestParam Long idOwner,@RequestParam String name, @RequestParam String address, Model model){
        Restaurant restaurant = new Restaurant(idOwner,name,address);
        return restaurantRepository.save(restaurant).getId()+"";
    }
    @PostMapping("/restaurant/get")
    public ArrayList<Restaurant> restaurantGet(@RequestParam Long idOwner, Model model){
        ArrayList<Restaurant> restaurants = new ArrayList<>();
            for (Restaurant temp : restaurantRepository.findAll()) {
                if (temp.getIdOwner() == idOwner) {
                    restaurants.add(temp);
                }
            }
        return restaurants;
    }
    @GetMapping("/restaurant/all")
    public List<Restaurant> restaurantGetAll(){
        List<Restaurant> restaurants = new ArrayList<>();
            for(Restaurant temp : restaurantRepository.findAll()){
                restaurants.add(temp);
            }
        return restaurants;
    }
}
