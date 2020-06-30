package com.server.restaurantrent.controllers;

import com.server.restaurantrent.models.Board;
import com.server.restaurantrent.models.Rent;
import com.server.restaurantrent.models.Restaurant;
import com.server.restaurantrent.repo.BoardRepository;
import com.server.restaurantrent.repo.RentRepository;
import com.server.restaurantrent.repo.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController

// контроллер запросов связанных с рестораном
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private RentRepository rentRepository;

    // метод обрабатывает запрос добавления ресторана
    @PostMapping("/restaurant/add")
    public Restaurant restaurantAdd(@RequestBody Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @PostMapping("/restaurant/delete")
    public String restaurantDelete(@RequestParam Long id) {
        // предварительно удаляем все заказы и столы, связанные с этим рестораном
        rentRepository.removeByIdRestaurant(id);
        boardRepository.removeByIdRestaurant(id);
        // удаляем сам ресторан
        restaurantRepository.deleteById(id);
        return "Ресторан успешно удалён";
    }

    // метод обрабатывает запрос массива ресторанов конкретного владельца
    @PostMapping("/restaurant/get")
    public ArrayList<Restaurant> restaurantGet(@RequestParam Long idOwner) {
        return  restaurantRepository.findAllByIdOwner(idOwner);
    }

    // метод обрабатывает запрос массива всех ресторанов
    @GetMapping("/restaurant/all")
    public ArrayList<Restaurant> restaurantGetAll() {
        return (ArrayList<Restaurant>) restaurantRepository.findAll();
    }
}
