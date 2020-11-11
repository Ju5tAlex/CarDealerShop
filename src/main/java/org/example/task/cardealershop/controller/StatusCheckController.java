package org.example.task.cardealershop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusCheckController {

    @GetMapping("/status")
    public String getStatus() {
        return "OK";
    }
}
