package com.library.repository;

import com.library.model.Reservation;
import com.library.model.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class ReservationRepository {
    @PersistenceContext
    private EntityManager em;

    public Reservation findById(Long id) {
        return em.find(Reservation.class, id);
    }

    public List<Reservation> findByUser(User user) {
        return em.createQuery(
                        "SELECT r FROM Reservation r WHERE r.user = :user AND r.expirationDate > CURRENT_TIMESTAMP",
                        Reservation.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<Reservation> findAllActive() {
        return em.createQuery(
                        "SELECT r FROM Reservation r WHERE r.expirationDate > CURRENT_TIMESTAMP",
                        Reservation.class)
                .getResultList();
    }

    public void save(Reservation reservation) {
        if (reservation.getId() == null) {
            em.persist(reservation);
        } else {
            em.merge(reservation);
        }
    }

    public void delete(Long id) {
        Reservation reservation = findById(id);
        if (reservation != null) {
            em.remove(reservation);
        }
    }

    public int countActiveReservationsForUser(User user) {
        return em.createQuery(
                        "SELECT COUNT(r) FROM Reservation r WHERE r.user = :user " +
                                "AND r.expirationDate > CURRENT_TIMESTAMP",
                        Long.class)
                .setParameter("user", user)
                .getSingleResult()
                .intValue();
    }
}