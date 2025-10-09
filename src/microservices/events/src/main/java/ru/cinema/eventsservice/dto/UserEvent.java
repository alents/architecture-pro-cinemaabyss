package ru.cinema.eventsservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.Instant;

@Data
public class UserEvent {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("username")
    private String userName;
    private String email;
    private String action;
    private Instant timestamp;
}



