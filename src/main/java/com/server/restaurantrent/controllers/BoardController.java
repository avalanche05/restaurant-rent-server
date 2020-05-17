package com.server.restaurantrent.controllers;


import com.server.restaurantrent.models.Board;

import com.server.restaurantrent.repo.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class BoardController {

    @Autowired
    private BoardRepository tableRepository;

    @PostMapping("/table/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String tableAdd(@RequestParam long idRestaurant, @RequestParam float x,@RequestParam float y, Model model){
        Board table = new Board(idRestaurant,x,y);
        tableRepository.save(table);
        return "СТОЛ УСПЕШНО ДОБАВЛЕН В БАЗУ ДАННЫХ";
    }
    @PostMapping("/table/get")
    @ResponseStatus(HttpStatus.CREATED)
    public ArrayList<Board> tableGet(@RequestParam Long idRestaurant,Model model){
    ArrayList<Board> tables = new ArrayList<>();
    Iterable<Board> allTables = tableRepository.findAll();
        Iterable<Board> owners = tableRepository.findAll();
        for (Board temp:owners) {
            if(temp.getIdRestaurant() == idRestaurant){
                tables.add(temp);
            }
        }
        return tables;
    }
}

