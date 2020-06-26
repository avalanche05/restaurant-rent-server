package com.server.restaurantrent.repo;

import com.server.restaurantrent.models.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface MessageRepository extends CrudRepository<Message,Long> {
    ArrayList<Message> findAllByIdRent(long idRent);
}
