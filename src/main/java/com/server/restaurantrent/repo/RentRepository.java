package com.server.restaurantrent.repo;

import com.server.restaurantrent.models.Rent;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface RentRepository extends CrudRepository<Rent,Long> {
    @Transactional
    Long removeByIdRestaurant(long idRestaurant);
}
