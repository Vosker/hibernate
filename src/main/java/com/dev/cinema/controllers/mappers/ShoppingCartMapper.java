package com.dev.cinema.controllers.mappers;

import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.dto.ShoppingCartResponseDto;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartMapper {
    private final TicketMapper mapper;

    public ShoppingCartMapper(TicketMapper mapper) {
        this.mapper = mapper;
    }

    public ShoppingCartResponseDto fromEntityToDto(ShoppingCart cart) {
        ShoppingCartResponseDto shoppingCartDto = new ShoppingCartResponseDto();
        shoppingCartDto.setUserId(cart.getUser().getId());
        shoppingCartDto.setTickets(cart.getTickets()
                .stream()
                .map(mapper::fromEntityToDto)
                .collect(Collectors.toList()));
        return shoppingCartDto;
    }
}
