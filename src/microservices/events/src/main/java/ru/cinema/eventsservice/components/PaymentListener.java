package ru.cinema.eventsservice.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.cinema.eventsservice.dto.PaymentEvent;

@Component
public class PaymentListener {

    public static final Logger logger = LoggerFactory.getLogger(PaymentListener.class);

    @KafkaListener(topics = "payment-events")
    public void listen(PaymentEvent paymentEvent) {
        logger.info("Received: {}",  paymentEvent);
    }
}
