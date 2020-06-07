package com.server.restaurantrent.repo;

import com.server.restaurantrent.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
    User findByEmail(String email);
}
