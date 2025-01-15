package com.example.websample.controller;

import com.example.websample.dto.ErrorResponse;
import com.example.websample.exception.ErrorCode;
import com.example.websample.exception.WebSampleException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@RestController
public class SampleController {


    @GetMapping(value = "/order/{orderId}")
    public String getOrder(@PathVariable("orderId") String id)
            throws IllegalAccessException,
            SQLIntegrityConstraintViolationException {
        log.info("Get some order : " + id);

        if ("500".equals(id)) {
            throw new WebSampleException(
                    ErrorCode.TOO_BIG_ID_ERROR,
                    "500 is too big orderId."
            );
        }

        if ("3".equals(id)) {
            throw new WebSampleException(
                    ErrorCode.TOO_SMALL_ID_ERROR,
                    "3 is too small orderId."
            );
        }

        if ("4".equals(id)) {
            throw new SQLIntegrityConstraintViolationException(
                    "Duplicated insertion was tried."
            );
        }

        return "orderId:" + id + "," + "orderAmount: 1000";
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<ErrorResponse> handleIllegalAccessException(
            IllegalAccessException e) {
        log.error("IllegalAccessException is occurred", e);

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(ErrorCode.TOO_BIG_ID_ERROR,
                        "IllegalAccessException is occurred"));
    }

    @ExceptionHandler(WebSampleException.class)
    public ResponseEntity<ErrorResponse> handleWebSampleException(
            WebSampleException e) {
        log.error("WebSampleException is occurred", e);

        return ResponseEntity
                .status(HttpStatus.INSUFFICIENT_STORAGE)
                .body(new ErrorResponse(e.getErrorCode(),
                        "WebSampleException is occurred"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception e) {
        log.error("Exception is occurred", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR,
                        "Exception is occurred"));
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
