package com.server.restaurantrent.repo;

import com.server.restaurantrent.models.Board;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<Board,Long> {
}
