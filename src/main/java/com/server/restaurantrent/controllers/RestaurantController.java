package com.server.restaurantrent.controllers;

import com.server.restaurantrent.models.Owner;
import com.server.restaurantrent.models.Restaurant;
import com.server.restaurantrent.repo.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RestaurantController {

    

    @Autowired
    private RestaurantRepository restaurantRepository;

    @PostMapping("/restaurant/add")
    public Restaurant restaurantAdd(@RequestBody Restaurant restaurant){
        return restaurantRepository.save(restaurant);
    }

    @PostMapping("/restaurant/delete")
    public String restaurantDelete(@RequestParam Long id){
        restaurantRepository.deleteById(id);
        return "Ресторан успешно удалён";
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
    public ArrayList<Restaurant> restaurantGetAll(){
        ArrayList<Restaurant> restaurants = new ArrayList<>();
            for(Restaurant temp : restaurantRepository.findAll()){
                restaurants.add(temp);
            }
        return restaurants;
    }
}
