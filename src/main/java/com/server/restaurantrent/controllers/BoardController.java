package com.server.restaurantrent.controllers;


import com.server.restaurantrent.models.Board;
import com.server.restaurantrent.repo.BoardRepository;
import com.server.restaurantrent.repo.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        // берём id ресторана из любого стола(т.к. одним запросом можно прислать массив столов только с одного ресторана)
        long idRestaurant = tables.get(0).getIdRestaurant();
        // перед добавлением удаляем все столы и заказы которые были раньше связанны с рестораном, в который мы добавляем столы
        System.out.println("Rents deleted: " + rentRepository.removeByIdRestaurant(idRestaurant));
        System.out.println("Tables deleted: " + tableRepository.removeByIdRestaurant(idRestaurant));
        // сохранняем принятые столы в базе данных
        tableRepository.saveAll(tables);
        return "Столы успешно добавлены";
    }

    // метод обрабатывает запрос массива столов у определённого ресторана
    @PostMapping("/table/get")
    @ResponseStatus(HttpStatus.CREATED)
    public ArrayList<Board> tableGet(@RequestParam Long idRestaurant) {
        // отправляем все столы связанные с принятым id ресторана
        return tableRepository.findAllByIdRestaurant(idRestaurant);
    }
}

