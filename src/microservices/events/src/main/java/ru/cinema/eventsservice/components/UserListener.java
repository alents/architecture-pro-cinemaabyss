package ru.cinema.eventsservice.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.cinema.eventsservice.dto.UserEvent;

@Component
public class UserListener {

    private static final Logger logger = LoggerFactory.getLogger(UserListener.class);

    @KafkaListener(topics = "user-events")
    public void listen(UserEvent userEvent) {
        logger.info("Received: {}",  userEvent);
    }
}
