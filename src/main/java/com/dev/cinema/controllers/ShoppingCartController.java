package com.dev.cinema.controllers;

import com.dev.cinema.service.mappers.ShoppingCartMapper;
import com.dev.cinema.model.User;
import com.dev.cinema.model.dto.ShoppingCartResponseDto;
import com.dev.cinema.service.MovieSessionService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopping-carts")
public class ShoppingCartController {
    private final ShoppingCartService cartService;
    private final MovieSessionService sessionService;
    private final UserService userService;
    private final ShoppingCartMapper mapper;

    public ShoppingCartController(ShoppingCartService cartService,
                                  MovieSessionService sessionService,
                                  UserService userService, ShoppingCartMapper mapper) {
        this.cartService = cartService;
        this.sessionService = sessionService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping("/movie-sessions")
    public void addMovieSession(@RequestParam Long movieSessionId,
                                @RequestParam Long userId) {
        cartService.addSession(sessionService.get(movieSessionId),
                userService.get(userId));
    }

    @GetMapping("/by-user")
    public ShoppingCartResponseDto getByUserId(@RequestParam(name = "id") Long userId) {
        User user = userService.get(userId);
        return mapper.mapToDto(cartService.getByUser(user));
    }
}
