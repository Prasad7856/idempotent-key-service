package com.prasad.controller;

import com.prasad.config.HashUtil;
import com.prasad.dto.PaymentHistoryResponse;
import com.prasad.dto.PaymentRequest;
import com.prasad.dto.PaymentResponse;
import com.prasad.service.IdempotencyService;
import com.prasad.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @Autowired
    private IdempotencyService idempotencyService;

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
            @RequestHeader("Idempotency-Key") String key,
            @RequestBody PaymentRequest request){

        String requestHash =
                HashUtil.hashRequest(request);

        idempotencyService.validateAndCreateKey(
                key,requestHash
        );

        PaymentResponse response =
                paymentService.processPayment(
                        key,requestHash,request
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<List<PaymentHistoryResponse>> getPaymentHistory() {

        List<PaymentHistoryResponse> payments = paymentService.getPaymentHistory();

        return ResponseEntity.ok(payments);
    }
}
