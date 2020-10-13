package com.dev.cinema;

import com.dev.cinema.lib.Injector;
import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.model.Movie;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.User;
import com.dev.cinema.security.AuthenticationService;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import com.dev.cinema.service.MovieSessionService;
import com.dev.cinema.service.ShoppingCartService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.naming.AuthenticationException;

public class Main {
    private static Injector injector = Injector.getInstance("com.dev.cinema");

    public static void main(String[] args) throws AuthenticationException {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie testMovie = new Movie();
        testMovie.setTitle("Fast and Furious");
        movieService.add(testMovie);
        testMovie = movieService.add(testMovie);
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall testCinemaHall = new CinemaHall();
        testCinemaHall.setCapacity(61);
        testCinemaHall.setDescription("Test");
        testCinemaHall = cinemaHallService.add(testCinemaHall);
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(testCinemaHall);
        movieSession.setMovie(testMovie);
        movieSession.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        movieSessionService.findAvailableSessions(testMovie.getId(),
                LocalDate.now()).forEach(System.out::println);

        AuthenticationService autService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        autService.register("test@gmail.com", "qwerty");
        User user1 = autService.login("test@gmail.com", "qwerty");
        System.out.println(user1);

        ShoppingCartService shoppingCartService
                = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        shoppingCartService.addSession(movieSession, user1);
        shoppingCartService.addSession(movieSession, user1);
    }
}
