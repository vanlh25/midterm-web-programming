package vn.iotstar.repository.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import vn.iotstar.config.JPAConfig;
import vn.iotstar.entity.User;
import vn.iotstar.repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public User save(User user) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            if (user.getId() == 0) {
                em.persist(user);    // INSERT
            } else {
                em.merge(user);      // UPDATE
            }

            tx.commit();
            return user;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public User findById(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<User> findAll() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<User> query =
                em.createQuery("SELECT u FROM User u", User.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteById(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            User user = em.find(User.class, id);
            if (user != null) {
                tx.begin();
                em.remove(user);
                tx.commit();
            }
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            Long count = em.createQuery(
                    "SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class)
                    .setParameter("email", email)
                    .getSingleResult();

            return count > 0;

        } finally {
            em.close();
        }
    }

    @Override
    public boolean updatePasswordByEmail(String email, String newPassword) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            List<User> users = em.createQuery(
                    "SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getResultList();

            if (users.isEmpty()) {
                return false; // Không tìm thấy user
            }

            User user = users.get(0);

            tx.begin();
            user.setPasswd(newPassword);  // đúng field passwd
            em.merge(user);
            tx.commit();

            return true;

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;

        } finally {
            em.close();
        }
    }
    
    @Override
    public User findByEmail(String email) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.email = :email",
                User.class
            );
            query.setParameter("email", email);

            List<User> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);

        } finally {
            em.close();
        }
    }
    
    @Override
    public List<User> findAll(int page, int pageSize) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);

            query.setFirstResult((page - 1) * pageSize);  // OFFSET
            query.setMaxResults(pageSize);                // LIMIT

            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public long count() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(u) FROM User u", Long.class)
                     .getSingleResult();
        } finally {
            em.close();
        }
    }

}
