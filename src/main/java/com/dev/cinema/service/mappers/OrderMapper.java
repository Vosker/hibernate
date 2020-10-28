package com.dev.cinema.service.mappers;

import com.dev.cinema.model.Order;
import com.dev.cinema.model.dto.OrderResponseDto;
import com.dev.cinema.model.dto.TicketResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    private final TicketMapper ticketMapper;

    public OrderMapper(TicketMapper ticketMapper) {
        this.ticketMapper = ticketMapper;
    }

    public OrderResponseDto mapToDto(Order order) {
        OrderResponseDto orderDto = new OrderResponseDto();
        orderDto.setId(order.getId());
        List<TicketResponseDto> tickets = order.getTickets().stream()
                .map(ticketMapper::mapToDto)
                .collect(Collectors.toList());
        orderDto.setTickets(tickets);
        orderDto.setOrderCreationTime(
                order.getOrderDate());
        orderDto.setUserId(order.getUser().getId());
        return orderDto;
    }
}
