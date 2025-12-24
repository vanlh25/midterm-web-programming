package vn.iotstar.repository.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import vn.iotstar.config.JPAConfig;
import vn.iotstar.entity.Book;
import vn.iotstar.repository.BookRepository;

public class BookRepositoryImpl implements BookRepository {

    @Override
    public Book save(Book book) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            if (book.getBookId() > 0) {
                book = em.merge(book);   // update
            } else {
                em.persist(book);        // insert
            }
            tx.commit();
            return book;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Book findById(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Book> query = em.createQuery(
                "SELECT b FROM Book b " +
                "LEFT JOIN FETCH b.bookAuthors ba " +
                "LEFT JOIN FETCH ba.author " +
                "LEFT JOIN FETCH b.ratings " +
                "WHERE b.bookId = :id", Book.class
            );
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }


    @Override
    public void deleteById(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            Book book = em.find(Book.class, id);
            if (book != null) {
                tx.begin();
                em.remove(book);
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
    public List<Book> findAll() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
        	TypedQuery<Book> query = em.createQuery(
                    "SELECT DISTINCT b FROM Book b " +
                    "LEFT JOIN FETCH b.bookAuthors ba " +
                    "LEFT JOIN FETCH ba.author " +
                    "LEFT JOIN FETCH b.ratings r", Book.class
                );
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Book> findAll(int page, int pageSize) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            // 1️Lấy ID sách phân trang
            TypedQuery<Integer> idQuery = em.createQuery(
                "SELECT b.bookId FROM Book b ORDER BY b.bookId", Integer.class
            );
            idQuery.setFirstResult((page - 1) * pageSize);
            idQuery.setMaxResults(pageSize);
            List<Integer> bookIds = idQuery.getResultList();

            System.out.println("Book IDs page " + page + ": " + bookIds);

            if (bookIds.isEmpty()) {
                return List.of();
            }

            // 2️ Lấy sách cùng join fetch các collection
            TypedQuery<Book> bookQuery = em.createQuery(
                "SELECT DISTINCT b FROM Book b " +
                "LEFT JOIN FETCH b.bookAuthors ba " +
                "LEFT JOIN FETCH ba.author " +
                "LEFT JOIN FETCH b.ratings r " +
                "WHERE b.bookId IN :ids " +
                "ORDER BY b.bookId", Book.class
            );
            bookQuery.setParameter("ids", bookIds);

            return bookQuery.getResultList();

        } finally {
            em.close();
        }
    }


    @Override
    public long count() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.createQuery(
                "SELECT COUNT(b) FROM Book b", Long.class
            ).getSingleResult();
        } finally {
            em.close();
        }
    }

}
