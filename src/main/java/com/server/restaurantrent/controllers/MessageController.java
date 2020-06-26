package com.server.restaurantrent.controllers;


import com.server.restaurantrent.models.Message;
import com.server.restaurantrent.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController

// контроллер запросов связанных с сообщениями
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    // метод обрабатывает запрос отправки сообщения
    @PostMapping("/message/send")
    public boolean sendMessage(@RequestBody Message message) {
        // сохраняем полученное сообщение в базе данных
        messageRepository.save(message);
        return true;
    }

    // метод обрабатывает запрос получения массива сообщений по конкретному заказу
    @PostMapping("/message/get")
    public ArrayList<Message> getMessage(@RequestParam Long idRent) {
        // отправляем все сообщения, связанные с конкретным заказом
        return messageRepository.findAllByIdRent(idRent);
    }
}
