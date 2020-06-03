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
public class BoardController {

    @Autowired
    private BoardRepository tableRepository;

    @Autowired
    private RentRepository rentRepository;

    @PostMapping("/table/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String tableAdd(@RequestBody ArrayList<Board> tables){
        for (Rent temp : rentRepository.findAll()){
            if(temp.getIdRestaurant() == tables.get(0).getIdRestaurant())
                rentRepository.delete(temp);
        }
        for (Board temp : tableRepository.findAll()){
            if(temp.getIdRestaurant() == tables.get(0).getIdRestaurant())
                tableRepository.delete(temp);
        }
        for (Board temp : tables){
            tableRepository.save(temp);
        }
        return "Столы успешно добавлены";
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

