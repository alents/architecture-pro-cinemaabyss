package ru.cinema.eventsservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MovieEvent {
    @JsonProperty("movie_id")
    private Long movieId;
    private String title;
    private String action;
    @JsonProperty("user_id")
    private Long userId;
    private Double rating;
    private List<String> genres;
    private String description;
}


