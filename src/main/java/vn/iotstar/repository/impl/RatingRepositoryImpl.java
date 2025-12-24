package vn.iotstar.repository.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import vn.iotstar.config.JPAConfig;
import vn.iotstar.entity.Rating;
import vn.iotstar.entity.Rating.RatingId;
import vn.iotstar.repository.RatingRepository;

public class RatingRepositoryImpl implements RatingRepository {

    @Override
    public Rating save(Rating rating) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            rating = em.merge(rating);  // merge = insert/update
            tx.commit();
            return rating;

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            throw e;

        } finally {
            em.close();
        }
    }

    @Override
    public List<Rating> findAll() {
        EntityManager em = JPAConfig.getEntityManager();

        try {
            TypedQuery<Rating> query = em.createNamedQuery("rating.findAll", Rating.class);
            return query.getResultList();

        } finally {
            em.close();
        }
    }

    @Override
    public Rating findById(int userId, int bookId) {
        EntityManager em = JPAConfig.getEntityManager();

        try {
            RatingId id = new RatingId(userId, bookId);
            return em.find(Rating.class, id);

        } finally {
            em.close();
        }
    }

    @Override
    public void deleteById(int userId, int bookId) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            RatingId id = new RatingId(userId, bookId);
            Rating rating = em.find(Rating.class, id);

            if (rating != null) {
                tx.begin();
                em.remove(rating);
                tx.commit();
            }

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            throw e;

        } finally {
            em.close();
        }
    }
    
    @Override
    public List<Rating> findAll(int page, int pageSize) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Rating> query =
                    em.createQuery("SELECT r FROM Rating r", Rating.class);

            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);

            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public long count() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT COUNT(r) FROM Rating r", Long.class
            ).getSingleResult();
        } finally {
            em.close();
        }
    }
}
