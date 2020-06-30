package com.server.restaurantrent.repo;


import com.server.restaurantrent.models.Restaurant;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface RestaurantRepository extends CrudRepository<Restaurant,Long> {
    ArrayList<Restaurant> findAllByIdOwner(long idOwner);
}
