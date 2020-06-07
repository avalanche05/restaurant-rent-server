package com.server.restaurantrent.controllers;


import com.server.restaurantrent.models.Board;
import com.server.restaurantrent.models.Rent;
import com.server.restaurantrent.repo.BoardRepository;
import com.server.restaurantrent.repo.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController

// контроллер запросов связанных со столами
public class BoardController {

    @Autowired
    private BoardRepository tableRepository;

    @Autowired
    private RentRepository rentRepository;

    // метод обрабатывает запрос добавления массива столов
    @PostMapping("/table/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String tableAdd(@RequestBody ArrayList<Board> tables) {
        // перед добавлением удаляем все столы и заказы которые были раньше связанны с рестораном, в который мы добавляем столы
        for (Rent temp : rentRepository.findAll()) {
            if (temp.getIdRestaurant() == tables.get(0).getIdRestaurant())
                rentRepository.delete(temp);
        }
        for (Board temp : tableRepository.findAll()) {
            if (temp.getIdRestaurant() == tables.get(0).getIdRestaurant())
                tableRepository.delete(temp);
        }
        // сохранняем принятые столы в базе данных
        for (Board temp : tables) {
            tableRepository.save(temp);
        }
        return "Столы успешно добавлены";
    }

    // метод обрабатывает запрос массива столов у определённого ресторана
    @PostMapping("/table/get")
    @ResponseStatus(HttpStatus.CREATED)
    public ArrayList<Board> tableGet(@RequestParam Long idRestaurant) {
        ArrayList<Board> tables = new ArrayList<>();
        Iterable<Board> owners = tableRepository.findAll();
        // находим все столы связанные с принятым id ресторана
        for (Board temp : owners) {
            if (temp.getIdRestaurant() == idRestaurant) {
                tables.add(temp);
            }
        }
        return tables;
    }
}

