package com.server.restaurantrent.repo;

import com.server.restaurantrent.models.Rent;
import org.springframework.data.repository.CrudRepository;

public interface RentRepository extends CrudRepository<Rent,Long> {
}
