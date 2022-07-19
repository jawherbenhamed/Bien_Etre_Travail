package com.pidev.backend.controller;

import com.pidev.backend.entities.Message;
import com.pidev.backend.entities.ReadMessageRequest;
import com.pidev.backend.repos.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class MessageController {
    @Autowired
    MessageRepository messageRepository;

    @GetMapping(value = "/messages/{channelId}")
    public List<Message> findMessages( @PathVariable("channelId") String channelId) {
        return messageRepository.findAllByChannel(channelId);
    }
    @PostMapping(value = "/messages")
    public void sendReadReceipt(@RequestBody ReadMessageRequest request) {
        messageRepository.sendReadReceipt(request.getChannel(), request.getUsername());
    }
    @PostMapping(value = "/messages/add")
    public void sendReadReceipt(@RequestBody Message m ) {
        m.setChannel("travail");
        m.setTimestamp(new Date());
        messageRepository.save(m);
    }

}
