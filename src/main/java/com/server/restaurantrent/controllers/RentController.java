package com.server.restaurantrent.controllers;

import com.mysql.cj.xdevapi.JsonArray;
import com.server.restaurantrent.models.Board;
import com.server.restaurantrent.models.Rent;
import com.server.restaurantrent.models.Restaurant;
import com.server.restaurantrent.repo.BoardRepository;
import com.server.restaurantrent.repo.RentRepository;
import com.server.restaurantrent.repo.RestaurantRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.logging.Logger;

@RestController
public class RentController {

    @Autowired
    private RentRepository orderRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private BoardRepository boardRepository;

    private static Logger log = Logger.getLogger(RentController.class.getName());

    @PostMapping("/rent/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String rentAdd(@RequestBody Rent rent, Model model) throws JSONException {
        for (Rent temp : orderRepository.findAll()){
            if((temp.getDate()).equals(rent.getDate())&&isIdTablesContains(getTables(temp.getIdTables()),getTables(rent.getIdTables()))){
                double hoursTemp = Integer.parseInt(temp.getTime().split(":")[0]) + (Double.parseDouble(temp.getTime().split(":")[1])/60);
                double hoursUser = Integer.parseInt(rent.getTime().split(":")[0]) + (Double.parseDouble(rent.getTime().split(":")[1])/60);
                if(Math.abs(hoursTemp - hoursUser) < 1.5){
                    return "Столы уже забронированы";
                }

            }
        }

        for(Board temp : boardRepository.findAll()){
            if(temp.getId() == getTables(rent.getIdTables()).get(0)){
                rent.setIdRestaurant(temp.getIdRestaurant());
                break;
            }
        }

        orderRepository.save(rent);
        return "ЗАКАЗ СОЗДАН";
    }
    @PostMapping("rent/owner/get")
    public ArrayList<Rent> rentGetOwner(@RequestParam Long idOwner,Model model){
        ArrayList<Rent> rents = new ArrayList<>();
        for(Rent temp : orderRepository.findAll()){
           if(temp.getIdOwner().equals(idOwner) ){
                rents.add(temp);
            }
        }
        return rents;
   }
    @PostMapping("rent/user/get")
    public ArrayList<Rent> getUserRent(@RequestParam Long idUser,Model model){
        ArrayList<Rent> rents = new ArrayList<>();
        for(Rent temp : orderRepository.findAll()){
            if(temp.getIdUser().equals(idUser) ){
                rents.add(temp);
            }
        }
        return rents;
    }
    @PostMapping("rent/delete")
    public String deleteRent(@RequestParam Long id,Model model){
        orderRepository.deleteById(id);
        return "Бронь успешно удалена";
    }
    public static ArrayList<Long> getTables(String response) throws JSONException {
        JSONArray tablesJson = new JSONArray(response);
        ArrayList<Long> tables = new ArrayList<>();
        for(int i = 0; i < tablesJson.length(); i++){
            tables.add(tablesJson.getLong(i));
        }
        return tables;
    }
    public boolean isIdTablesContains(ArrayList<Long> idTables1, ArrayList<Long> idTables2){
        for (long temp1 : idTables1) {
            for(long temp2 : idTables2){
                if (temp1 == temp2){
                    return true;
                }
            }
        }
        return false;
    }
}
