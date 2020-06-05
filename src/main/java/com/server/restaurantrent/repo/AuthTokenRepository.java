package com.server.restaurantrent.repo;

import com.server.restaurantrent.models.AuthToken;
import org.springframework.data.repository.CrudRepository;

public interface AuthTokenRepository extends CrudRepository<AuthToken,Long> {
}
