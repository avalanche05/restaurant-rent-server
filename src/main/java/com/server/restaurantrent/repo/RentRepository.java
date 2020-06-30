package com.server.restaurantrent.repo;

import com.server.restaurantrent.models.Rent;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;

public interface RentRepository extends CrudRepository<Rent,Long> {
    @Transactional
    Long removeByIdRestaurant(long idRestaurant);
    ArrayList<Rent> findAllByIdOwner(long idOwner);
    ArrayList<Rent> findAllByIdUser(long idUser);
    ArrayList<Rent> findAllByDate(String date);
}
