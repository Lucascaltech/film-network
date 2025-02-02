package com.luca.film.film.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class RentedFilmResponse {
    private  Integer id;
    private  String filmTitle;
    private  String director;
    private  String imdbId;
    private LocalDateTime rentalDate;
    private  boolean returned;
    private boolean returnApproved;
    private Double rentalPrice;

}
