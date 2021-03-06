package com.dev.cinema.service.impl;

import com.dev.cinema.dao.MovieDao;
import com.dev.cinema.model.Movie;
import com.dev.cinema.service.MovieService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpls implements MovieService {

    private final MovieDao movieDao;

    public MovieServiceImpls(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public Movie add(Movie movie) {
        return movieDao.add(movie);
    }

    @Override
    public Movie get(Long id) {
        return movieDao.get(id);
    }

    @Override
    public List<Movie> getAll() {
        return movieDao.getAll();
    }
}
