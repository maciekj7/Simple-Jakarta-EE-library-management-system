package com.library.repository;

import com.library.model.Loan;
import com.library.model.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class LoanRepository {
    @PersistenceContext
    private EntityManager em;

    public Loan findById(Long id) {
        return em.find(Loan.class, id);
    }

    public List<Loan> findActiveByUser(User user) {
        return em.createQuery(
                        "SELECT l FROM Loan l WHERE l.user = :user AND l.returnDate IS NULL",
                        Loan.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<Loan> findAllActive() {
        return em.createQuery(
                        "SELECT l FROM Loan l WHERE l.returnDate IS NULL",
                        Loan.class)
                .getResultList();
    }

    public void save(Loan loan) {
        if (loan.getId() == null) {
            em.persist(loan);
        } else {
            em.merge(loan);
        }
    }

    public int countActiveLoansForUser(User user) {
        return em.createQuery(
                        "SELECT COUNT(l) FROM Loan l WHERE l.user = :user AND l.returnDate IS NULL",
                        Long.class)
                .setParameter("user", user)
                .getSingleResult()
                .intValue();
    }
}