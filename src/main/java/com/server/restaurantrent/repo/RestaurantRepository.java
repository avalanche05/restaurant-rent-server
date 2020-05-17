package com.server.restaurantrent.repo;


import com.server.restaurantrent.models.Restaurant;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<Restaurant,Long> {
}
