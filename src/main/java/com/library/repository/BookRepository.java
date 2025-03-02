package com.library.repository;

import com.library.model.Book;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class BookRepository {
    @PersistenceContext
    private EntityManager em;

    public Book findById(Long id) {
        return em.find(Book.class, id);
    }

    public List<Book> findAll() {
        return em.createQuery("SELECT b FROM Book b", Book.class)
                .getResultList();
    }

    public List<Book> findAvailable() {
        return em.createQuery(
                        "SELECT b FROM Book b WHERE b.isAvailable = true",
                        Book.class)
                .getResultList();
    }

    public void save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
        } else {
            em.merge(book);
        }
    }

    public void delete(Long id) throws Exception {
        Book book = findById(id);
        if (book == null) {
            throw new Exception("Nie znaleziono książki");
        }

        Long activeLoans = em.createQuery(
                        "SELECT COUNT(l) FROM Loan l WHERE l.book = :book AND l.returnDate IS NULL",
                        Long.class)
                .setParameter("book", book)
                .getSingleResult();

        Long activeReservations = em.createQuery(
                        "SELECT COUNT(r) FROM Reservation r WHERE r.book = :book AND r.expirationDate > CURRENT_TIMESTAMP",
                        Long.class)
                .setParameter("book", book)
                .getSingleResult();

        if (activeLoans > 0 || activeReservations > 0) {
            throw new Exception("Nie można usunąć książki, która ma aktywne wypożyczenia lub rezerwacje");
        }

        em.createQuery("DELETE FROM Loan l WHERE l.book = :book AND l.returnDate IS NOT NULL")
                .setParameter("book", book)
                .executeUpdate();

        em.createQuery("DELETE FROM Reservation r WHERE r.book = :book AND r.expirationDate <= CURRENT_TIMESTAMP")
                .setParameter("book", book)
                .executeUpdate();

        em.remove(book);
    }
}