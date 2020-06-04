package com.server.restaurantrent.controllers;


import com.server.restaurantrent.models.Message;
import com.server.restaurantrent.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @PostMapping("/message/send")
    public boolean sendMessage(@RequestBody Message message){
        messageRepository.save(message);
        return true;
    }
    @PostMapping("/message/get")
    public ArrayList<Message> getMessage(@RequestParam Long idRent){
        ArrayList<Message> messages = new ArrayList<>();
        for(Message temp : messageRepository.findAll()){
            if(temp.getIdRent() == idRent){
                messages.add(temp);
            }
        }
        return messages;
    }
}
