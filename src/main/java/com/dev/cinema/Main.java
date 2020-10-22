package com.dev.cinema;

import com.dev.cinema.config.AppConfig;
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
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        Movie testMovie = new Movie();
        testMovie.setTitle("Fast and Furious");
        MovieService movieService = context.getBean(MovieService.class);
        movieService.add(testMovie);
        testMovie = movieService.add(testMovie);
        movieService.getAll().forEach(log::info);

        CinemaHall testCinemaHall = new CinemaHall();
        testCinemaHall.setCapacity(61);
        testCinemaHall.setDescription("Test");
        CinemaHallService cinemaHallService = context.getBean(CinemaHallService.class);
        testCinemaHall = cinemaHallService.add(testCinemaHall);
        cinemaHallService.getAll().forEach(log::info);

        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(testCinemaHall);
        movieSession.setMovie(testMovie);
        movieSession.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService = context.getBean(MovieSessionService.class);
        movieSessionService.add(movieSession);
        movieSessionService.findAvailableSessions(testMovie.getId(),
                LocalDate.now()).forEach(log::info);

        AuthenticationService autService = context.getBean(AuthenticationService.class);
        autService.register("test@gmail.com", "qwerty");
        log.info("Registration completed");
        User user1 = null;
        try {
            user1 = autService.login("test@gmail.com", "qwerty");
        } catch (AuthenticationException e) {
            log.warn("Wrong email or password",e);
        }
        log.info("Login completed");

        ShoppingCartService shoppingCartService = context.getBean(ShoppingCartService.class);
        shoppingCartService.addSession(movieSession, user1);
        shoppingCartService.addSession(movieSession, user1);

        OrderService orderService = context.getBean(OrderService.class);
        orderService.completeOrder(shoppingCartService.getByUser(user1).getTickets(), user1);
        orderService.getOrderHistory(user1).forEach(log::info);
    }
}
