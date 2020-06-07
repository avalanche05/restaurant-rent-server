package com.server.restaurantrent.controllers;

import com.server.restaurantrent.models.Board;
import com.server.restaurantrent.models.Rent;
import com.server.restaurantrent.repo.BoardRepository;
import com.server.restaurantrent.repo.RentRepository;
import com.server.restaurantrent.repo.RestaurantRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController

// контроллер запросов связанных с заказами
public class RentController {

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private BoardRepository boardRepository;

    // метод обрабатывает запрос добавления заказа
    @PostMapping("/rent/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String rentAdd(@RequestBody Rent rent, Model model) throws JSONException {
        // проходим по всем заказам
        for (Rent temp : rentRepository.findAll()) {
            // проверяем не заказали ли принятые столы
            if ((temp.getDate()).equals(rent.getDate()) && isIdTablesContains(getTables(temp.getIdTables()), getTables(rent.getIdTables()))) {
                // переводим минуты с часами в часы с дрообной частью
                double hoursTemp = Integer.parseInt(temp.getTime().split(":")[0]) + (Double.parseDouble(temp.getTime().split(":")[1]) / 60);
                double hoursUser = Integer.parseInt(rent.getTime().split(":")[0]) + (Double.parseDouble(rent.getTime().split(":")[1]) / 60);
                // проверяем не заказали ли принятые столы в это же время с промежутком в 1,5 часа
                if (Math.abs(hoursTemp - hoursUser) < 1.5) {
                    return "Столы уже забронированы";
                }

            }
        }
        // добавляем в заказ id ресторана
        for (Board temp : boardRepository.findAll()) {
            if (temp.getId() == getTables(rent.getIdTables()).get(0)) {
                rent.setIdRestaurant(temp.getIdRestaurant());
                break;
            }
        }

        // сохраняем заказ в базе данных
        rentRepository.save(rent);
        return "ЗАКАЗ СОЗДАН";
    }

    // метод обрабатывает запрос списка всех заказов отправленных владельцу
    @PostMapping("rent/owner/get")
    public ArrayList<Rent> rentGetOwner(@RequestParam Long idOwner, Model model) {
        ArrayList<Rent> rents = new ArrayList<>();
        for (Rent temp : rentRepository.findAll()) {
            if (temp.getIdOwner().equals(idOwner)) {
                rents.add(temp);
            }
        }
        return rents;
    }

    // метод обрабатывает запрос списка всех заказов отправленных пользователем
    @PostMapping("rent/user/get")
    public ArrayList<Rent> getUserRent(@RequestParam Long idUser, Model model) {
        ArrayList<Rent> rents = new ArrayList<>();
        for (Rent temp : rentRepository.findAll()) {
            if (temp.getIdUser().equals(idUser)) {
                rents.add(temp);
            }
        }
        return rents;
    }

    // метод обрабатывает запрос удаления брони
    @PostMapping("rent/delete")
    public String deleteRent(@RequestParam Long id, Model model) {
        rentRepository.deleteById(id);
        return "Бронь успешно удалена";
    }

    // метод возвращает массив id столов, которые парсит из JSON
    public static ArrayList<Long> getTables(String response) throws JSONException {
        JSONArray tablesJson = new JSONArray(response);
        ArrayList<Long> tables = new ArrayList<>();
        for (int i = 0; i < tablesJson.length(); i++) {
            tables.add(tablesJson.getLong(i));
        }
        return tables;
    }

    // метод проверяет содержится ли хоть один элемент первого массива во втором массиве
    public boolean isIdTablesContains(ArrayList<Long> idTables1, ArrayList<Long> idTables2) {
        for (long temp1 : idTables1) {
            for (long temp2 : idTables2) {
                if (temp1 == temp2) {
                    return true;
                }
            }
        }
        return false;
    }
}
