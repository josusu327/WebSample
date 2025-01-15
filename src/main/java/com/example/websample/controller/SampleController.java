package com.example.websample.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SampleController {

    @GetMapping(value = "/order/1")
    public String getOrder(){
        log.info("Get some order");
        return "orderId: 1 orderAmount: 1000";
    }

    @PutMapping(value = "/order")
    public String createOrder(){
        log.info("Create order");
        return "order created -> orderId: 1 orderAmount: 1000";
    }

}
