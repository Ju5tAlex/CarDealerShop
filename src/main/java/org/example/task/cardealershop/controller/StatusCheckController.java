package org.example.task.cardealershop.controller;

import org.example.task.cardealershop.service.MQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusCheckController {

    private MQService mqService;

    @Autowired
    public StatusCheckController(MQService mqService) {
        this.mqService = mqService;
    }

    @GetMapping("/status")
    public String getStatus() {
        return "OK";
    }

    @PostMapping("/mqsent")
    public boolean sentMessage(@RequestBody String message) {
        return mqService.sendMessageToMQ(message);
    }

    @GetMapping("/mqreceive")
    public String receiveMessage() {
        return mqService.receiveMessage();
    }
}
