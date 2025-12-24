package vn.iotstar.repository.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import vn.iotstar.config.JPAConfig;
import vn.iotstar.entity.Author;
import vn.iotstar.repository.AuthorRepository;

public class AuthorRepositoryImpl implements AuthorRepository {

    @Override
    public Author save(Author author) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            if (author.getAuthorId() > 0) {
                author = em.merge(author);   // update
            } else {
                em.persist(author);          // insert
            }
            tx.commit();
            return author;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Author> findAll() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Author> query = em.createQuery(
                "SELECT a FROM Author a", Author.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Author findById(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.find(Author.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteById(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            Author author = em.find(Author.class, id);

            if (author != null) {
                tx.begin();
                em.remove(author);
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
    public List<Author> findAll(int page, int pageSize) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Author> query =
                    em.createQuery("SELECT a FROM Author a", Author.class);

            query.setFirstResult((page - 1) * pageSize);   // OFFSET
            query.setMaxResults(pageSize);                 // LIMIT

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
                    "SELECT COUNT(a) FROM Author a", Long.class
            ).getSingleResult();
        } finally {
            em.close();
        }
    }

}
