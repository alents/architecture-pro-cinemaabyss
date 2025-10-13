package ru.cinema.eventsservice.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.cinema.eventsservice.dto.MovieEvent;

@Component
public class MovieListener {

    public static final Logger logger = LoggerFactory.getLogger(MovieListener.class);

    @KafkaListener(topics = "movie-events")
    public void listen(MovieEvent movieEvent) {
        logger.info("Received: {}",  movieEvent);
    }
}
