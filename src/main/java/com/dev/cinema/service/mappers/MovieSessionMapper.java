package com.dev.cinema.service.mappers;

import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.dto.MovieSessionRequestDto;
import com.dev.cinema.model.dto.MovieSessionResponseDto;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class MovieSessionMapper {
    private final MovieService movieService;
    private final CinemaHallService cinemaHallService;

    public MovieSessionMapper(MovieService movieService, CinemaHallService cinemaHallService) {
        this.movieService = movieService;
        this.cinemaHallService = cinemaHallService;
    }

    public MovieSessionResponseDto mapToDto(MovieSession movieSession) {
        MovieSessionResponseDto movieSessionResponseDto = new MovieSessionResponseDto();
        movieSessionResponseDto.setId(movieSession.getId());
        movieSessionResponseDto.setMovieId(movieSession.getMovie().getId());
        movieSessionResponseDto.setCinemaHallId(movieSession.getCinemaHall().getId());
        movieSessionResponseDto.setShowTime(
                movieSession.getShowTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return movieSessionResponseDto;
    }

    public MovieSession mapToMovie(MovieSessionRequestDto movieSessionRequestDto) {
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(movieService.get(movieSessionRequestDto.getMovieId()));
        movieSession
                .setCinemaHall(cinemaHallService.get(movieSessionRequestDto.getCinemaHallId()));
        movieSession.setShowTime(LocalDateTime.parse(movieSessionRequestDto.getShowTime()));
        return movieSession;
    }
}
