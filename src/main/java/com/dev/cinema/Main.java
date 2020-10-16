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
import com.dev.cinema.service.OrderService;
import com.dev.cinema.service.ShoppingCartService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.naming.AuthenticationException;
import org.apache.log4j.Logger;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class);
    private static Injector injector = Injector.getInstance("com.dev.cinema");

    public static void main(String[] args) throws AuthenticationException {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie testMovie = new Movie();
        testMovie.setTitle("Fast and Furious");
        movieService.add(testMovie);
        testMovie = movieService.add(testMovie);
        movieService.getAll().forEach(log::info);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall testCinemaHall = new CinemaHall();
        testCinemaHall.setCapacity(61);
        testCinemaHall.setDescription("Test");
        testCinemaHall = cinemaHallService.add(testCinemaHall);
        cinemaHallService.getAll().forEach(log::info);

        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(testCinemaHall);
        movieSession.setMovie(testMovie);
        movieSession.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        movieSessionService.findAvailableSessions(testMovie.getId(),
                LocalDate.now()).forEach(log::info);

        AuthenticationService autService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        autService.register("test@gmail.com", "qwerty");
        log.info("Registration completed");
        User user1 = autService.login("test@gmail.com", "qwerty");
        log.info("Login completed");
        try {
            autService.login("1919", "7777");
        } catch (AuthenticationException e) {
            log.warn("Wrong email or password",e);
        }

        ShoppingCartService shoppingCartService
                = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        shoppingCartService.addSession(movieSession, user1);
        shoppingCartService.addSession(movieSession, user1);

        OrderService orderService
                = (OrderService) injector.getInstance(OrderService.class);
        orderService.completeOrder(shoppingCartService.getByUser(user1).getTickets(), user1);
        orderService.getOrderHistory(user1).forEach(log::info);
    }
}
