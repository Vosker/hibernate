package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.OrderDao;
import com.dev.cinema.exceptions.DataProcessingException;
import com.dev.cinema.lib.Dao;
import com.dev.cinema.model.Order;
import com.dev.cinema.model.User;
import com.dev.cinema.util.HibernateUtil;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class OrderDaoImpl implements OrderDao {
    private static final Logger log = Logger.getLogger(OrderDaoImpl.class);

    @Override
    public Order add(Order order) {
        Transaction transaction = null;
        Session session = null;
        log.info("Adding order " + order);
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
            log.info("Order added");
            return order;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Failed to add the order "
                    + order, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Order> getByUser(User user) {
        log.info("Getting all orders of user " + user);
        try (Session session = HibernateUtil.getSessionFactory().openSession().getSession()) {
            Query<Order> getAllOrdersQuery = session.createQuery("select distinct o from Order o "
                    + "join fetch o.tickets "
                    + "where o.user = :user", Order.class);
            getAllOrdersQuery.setParameter("user", user);
            return getAllOrdersQuery.getResultList();
        }
    }
}
