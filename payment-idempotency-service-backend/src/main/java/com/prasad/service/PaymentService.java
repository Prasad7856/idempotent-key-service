package com.prasad.service;

import com.prasad.dto.PaymentHistoryResponse;
import com.prasad.dto.PaymentRequest;
import com.prasad.dto.PaymentResponse;
import com.prasad.entity.IdempotencyKey;
import com.prasad.entity.Payment;
import com.prasad.exception.InvalidRequestException;
import com.prasad.repository.IdempotencyRepository;
import com.prasad.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private IdempotencyRepository idempotencyRepository;

    @Transactional
    public PaymentResponse processPayment(String key,
                                          String requestHash,
                                          PaymentRequest request){

        IdempotencyKey record =
                idempotencyRepository.lockByKey(key)
                        .orElseThrow(() ->
                                new InvalidRequestException("Key not found"));

        if(record.getResponseBody()!=null){

            try{

                ObjectMapper mapper = new ObjectMapper();
//
//                return mapper.readValue(
//                        record.getResponseBody(),
//                        PaymentResponse.class
//                );
                PaymentResponse response = mapper.readValue(
                        record.getResponseBody(),
                        PaymentResponse.class
                );
                response.setStatus("SUCCESS");
                response.setMessage("Payment is already completed");
                return response;

            }catch(Exception e){
                throw new RuntimeException();
            }
        }

        Payment payment = new Payment();

        payment.setUserId(request.getUserId());
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());
        payment.setStatus("SUCCESS");
        payment.setCreatedAt(LocalDateTime.now());

        paymentRepository.save(payment);

        PaymentResponse response = new PaymentResponse();

        response.setPaymentId(payment.getId());
        response.setStatus("SUCCESS");
        response.setMessage("Payment Successful !!!");

        try{

            ObjectMapper mapper = new ObjectMapper();

            record.setResponseBody(
                    mapper.writeValueAsString(response)
            );

            record.setStatus("COMPLETED");

            idempotencyRepository.save(record);

        }catch(Exception e){
            throw new RuntimeException();
        }

        return response;
    }

    public List<PaymentHistoryResponse> getPaymentHistory() {

        List<Payment> payments = paymentRepository.findAll();

        return payments.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private PaymentHistoryResponse mapToResponse(Payment payment) {

        return new PaymentHistoryResponse(
                payment.getId(),
                payment.getUserId(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getStatus(),
                payment.getCreatedAt()
        );
    }
}
