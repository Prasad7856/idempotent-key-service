package com.prasad.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentHistoryResponse {

    private Long paymentId;

    private String userId;

    private Double amount;

    private String currency;

    private String status;

    private LocalDateTime createdAt;


    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public PaymentHistoryResponse(Long paymentId, String userId, Double amount, String currency, String status,LocalDateTime createdAt) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.amount = amount;
        this.currency = currency;
        this.createdAt = createdAt;
        this.status = status;
    }
}
