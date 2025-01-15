package com.example.websample.controller;

import com.example.websample.dto.ErrorResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class SampleController {


    @GetMapping(value = "/order/{orderId}")
    public String getOrder(@PathVariable("orderId") String id)
            throws IllegalAccessException {
        log.info("Get some order : " + id);

        if ("500".equals(id)) {
            throw new IllegalAccessException("500 is not valid orderId.");
        }
        return "orderId:" + id + "," + "orderAmount: 1000";
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(IllegalAccessException.class)
    public ErrorResponse handleIllegalAccessException(IllegalAccessException e){
        log.error("IllegalAccessException is occured", e);

        return new ErrorResponse("INVALID_ACCESS",
                "IllegalAccessException is occured");
    }

    @DeleteMapping(value = "/order/{orderId}")
    public String deleteOrder(@PathVariable("orderId") String id) {
        log.info("Delete some order : " + id);
        return "Delete orderId:" + id;
    }

    @GetMapping(value = "/order")
    public String getOrderWithRequestParam(
            @RequestParam(value = "orderId", required = false,
                    defaultValue = "defaultId") String id,
            @RequestParam("orderAmount") Integer amount) {
        log.info("Get order : " + id + ", amount : " + amount);
        return "orderId:" + id + "," + "orderAmount:" + amount;
    }

    @PostMapping(value = "/order")
    public String createOrder(
            @RequestBody CreateOrderRequest createOrderRequest,
            @RequestHeader String userAccountId) {
        log.info("Create order : " + createOrderRequest +
                ", userAccountId : " + userAccountId);
        return "orderId:" + createOrderRequest.getOrderId() + ", " +
                "orderAmount:" + createOrderRequest.getOrderAmount();
    }

    @PutMapping(value = "/order")
    public String createOrder() {
        log.info("Create order");
        return "order created -> orderId: 1 orderAmount: 1000";
    }

    @Data
    public static class CreateOrderRequest {
        private String orderId;
        private Integer orderAmount;
    }

}
