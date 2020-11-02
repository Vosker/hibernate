package com.dev.cinema.model.dto;

public class TicketResponseDto {
    private String movieSessionMovieTitle;
    private Long userId;
    private Long ticketId;

    public String getMovieSessionMovieTitle() {
        return movieSessionMovieTitle;
    }

    public void setMovieSessionMovieTitle(String movieSessionMovieTitle) {
        this.movieSessionMovieTitle = movieSessionMovieTitle;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
}
