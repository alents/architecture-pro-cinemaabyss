package ru.cinema.eventsservice.api;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import ru.cinema.eventsservice.dto.MovieEvent;
import ru.cinema.eventsservice.dto.PaymentEvent;
import ru.cinema.eventsservice.dto.UserEvent;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class ApiController {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    private final Map<String, String> statusSuccess = Map.of("status", "success");

    @GetMapping("/health")
    public ResponseEntity<Map<String, Boolean>> getHealth() {
        Map<String, Boolean> status = new HashMap<>();
        status.put("status", true);
        return ResponseEntity.ok(Map.of("status", true));
    }

    @PostMapping("/movie")
    public ResponseEntity<Map<String, String>> createMovieEvent(@RequestBody MovieEvent movieEvent) {
        log.info("Create movie: {}", movieEvent);
        kafkaTemplate.send("movie-events", movieEvent.getMovieId().toString(), movieEvent);
        return new ResponseEntity<>(statusSuccess, HttpStatus.CREATED);
    }

    @PostMapping("/user")
    public ResponseEntity<Map<String, String>> createUserEvent(@RequestBody UserEvent userEvent) {
        log.info("Create user: {}", userEvent);
        kafkaTemplate.send("user-events", userEvent.getUserId().toString(), userEvent);
        return new ResponseEntity<>(statusSuccess, HttpStatus.CREATED);
    }

    @PostMapping("/payment")
    public ResponseEntity<Map<String, String>> createPaymentEvent(@RequestBody PaymentEvent paymentEvent) {
        log.info("Create payment: {}", paymentEvent);
        kafkaTemplate.send("payment-events", paymentEvent.getPaymentId().toString(), paymentEvent);
        return new ResponseEntity<>(statusSuccess, HttpStatus.CREATED);
    }
}
