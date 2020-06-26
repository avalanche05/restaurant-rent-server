package com.server.restaurantrent.repo;

import com.server.restaurantrent.models.Board;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;

public interface BoardRepository extends CrudRepository<Board,Long> {
    @Transactional
    Long removeByIdRestaurant(long idRestaurant);
    ArrayList<Board> findAllByIdRestaurant(long idRestaurant);
}
