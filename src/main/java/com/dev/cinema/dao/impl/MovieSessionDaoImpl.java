package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.MovieSessionDao;
import com.dev.cinema.exceptions.DataProcessingException;
import com.dev.cinema.model.MovieSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class MovieSessionDaoImpl implements MovieSessionDao {
    private static final Logger log = Logger.getLogger(MovieSessionDaoImpl.class);

    private final SessionFactory sessionFactory;

    public MovieSessionDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        log.info("Getting all movie sessions with movie ID "
                + movieId + " for " + date);
        try (Session session = sessionFactory.openSession()) {
            Query<MovieSession> getAllAvailableSessionsQuery =
                    session.createQuery("from MovieSession where id = :id"
                                    + " and showTime between :start AND :end",
                            MovieSession.class);
            getAllAvailableSessionsQuery.setParameter("id", movieId);
            getAllAvailableSessionsQuery.setParameter("start", date.atTime(LocalTime.MIN));
            getAllAvailableSessionsQuery.setParameter("end", date.atTime(LocalTime.MAX));
            return getAllAvailableSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find available Movie Sessions", e);
        }
    }

    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        Session session = null;
        log.info("Adding movie session " + movieSession);
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(movieSession);
            transaction.commit();
            log.info("Movie session added");
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add Movie Session"
                    + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public MovieSession get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(MovieSession.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Can't find MovieSession with id: " + id, e);
        }
    }
}
