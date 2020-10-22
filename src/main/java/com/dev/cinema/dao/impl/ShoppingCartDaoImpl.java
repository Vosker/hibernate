package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.ShoppingCartDao;
import com.dev.cinema.exceptions.DataProcessingException;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class ShoppingCartDaoImpl implements ShoppingCartDao {
    private static final Logger log = Logger.getLogger(ShoppingCartDaoImpl.class);

    private final SessionFactory sessionFactory;

    public ShoppingCartDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public ShoppingCart add(ShoppingCart shoppingCart) {
        Transaction transaction = null;
        Session session = null;
        log.info("Adding shopping cart " + shoppingCart);
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(shoppingCart);
            transaction.commit();
            log.info("Shopping cart added");
            return shoppingCart;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add the shopping cart "
                    + shoppingCart, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public ShoppingCart getByUser(User user) {
        log.info("Getting cart of user " + user);
        try (Session session = sessionFactory.openSession()) {
            Query<ShoppingCart> getCartByUserQuery
                    = session.createQuery("from ShoppingCart sc "
                    + "left join fetch sc.tickets "
                    + "where sc.user = :user", ShoppingCart.class);
            getCartByUserQuery.setParameter("user", user);
            return getCartByUserQuery.getSingleResult();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get shopping cart of user "
                    + user, e);
        }
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        Transaction transaction = null;
        Session session = null;
        log.info("Updating shopping cart " + shoppingCart);
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(shoppingCart);
            log.info("Cart was updated");
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't update shoppingCart "
                    + shoppingCart, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
