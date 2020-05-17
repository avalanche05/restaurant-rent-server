package com.server.restaurantrent.repo;

import com.server.restaurantrent.models.Owner;
import org.springframework.data.repository.CrudRepository;

public interface OwnerRepository extends CrudRepository<Owner,Long> {
}
