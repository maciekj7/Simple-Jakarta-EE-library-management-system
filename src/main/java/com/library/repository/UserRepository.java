package com.library.repository;

import com.library.model.User;
import com.library.model.UserRole;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class UserRepository {
    @PersistenceContext
    private EntityManager em;

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAllReaders() {
        return em.createQuery(
                        "SELECT u FROM User u WHERE u.role = :role",
                        User.class)
                .setParameter("role", UserRole.READER)
                .getResultList();
    }

    public User findByCardNumber(String cardNumber) {
        try {
            return em.createQuery(
                            "SELECT u FROM User u WHERE u.cardNumber = :cardNumber",
                            User.class)
                    .setParameter("cardNumber", cardNumber)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void save(User user) {
        if (findByCardNumber(user.getCardNumber()) != null && user.getId() == null) {
            throw new IllegalStateException("Użytkownik o podanym numerze karty już istnieje");
        }

        if (user.getId() == null) {
            em.persist(user);
        } else {
            em.merge(user);
        }
    }

    public void delete(Long id) throws Exception {
        User user = findById(id);
        if (user != null) {
            Long activeLoans = em.createQuery(
                            "SELECT COUNT(l) FROM Loan l WHERE l.user = :user AND l.returnDate IS NULL",
                            Long.class)
                    .setParameter("user", user)
                    .getSingleResult();

            Long activeReservations = em.createQuery(
                            "SELECT COUNT(r) FROM Reservation r WHERE r.user = :user AND r.expirationDate > CURRENT_TIMESTAMP",
                            Long.class)
                    .setParameter("user", user)
                    .getSingleResult();

            if (activeLoans > 0 || activeReservations > 0) {
                throw new Exception("Nie można usunąć użytkownika, który ma aktywne wypożyczenia lub rezerwacje");
            }

            em.createQuery("DELETE FROM Loan l WHERE l.user = :user AND l.returnDate IS NOT NULL")
                    .setParameter("user", user)
                    .executeUpdate();

            em.createQuery("DELETE FROM Reservation r WHERE r.user = :user AND r.expirationDate <= CURRENT_TIMESTAMP")
                    .setParameter("user", user)
                    .executeUpdate();

            em.remove(user);
        }
    }

}
