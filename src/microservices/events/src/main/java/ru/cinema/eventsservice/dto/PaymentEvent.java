package ru.cinema.eventsservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
public class PaymentEvent {
    @JsonProperty("payment_id")
    private Long paymentId;
    @JsonProperty("user_id")
    private Long userId;
    private Double amount;
    private String status;
    private Instant timestamp;
    @JsonProperty("method_type")
    private String methodType;
}

