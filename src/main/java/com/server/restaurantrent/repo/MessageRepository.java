package com.server.restaurantrent.repo;

import com.server.restaurantrent.models.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message,Long> {
}