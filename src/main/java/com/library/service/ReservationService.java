package com.library.service;

import com.library.model.Book;
import com.library.model.Reservation;
import com.library.model.User;
import com.library.repository.BookRepository;
import com.library.repository.ReservationRepository;
import com.library.repository.UserRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class ReservationService {
    @Inject
    private ReservationRepository reservationRepository;

    @Inject
    private BookRepository bookRepository;

    @Inject
    private UserRepository userRepository;

    public void createReservation(Long userId, Long bookId) throws Exception {
        User user = userRepository.findById(userId);
        Book book = bookRepository.findById(bookId);

        if (user == null) {
            throw new Exception("Nie znaleziono użytkownika");
        }

        if (book == null) {
            throw new Exception("Nie znaleziono książki");
        }

        if (!book.isAvailable()) {
            throw new Exception("Książka nie jest dostępna do rezerwacji");
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setBook(book);
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setExpirationDate(LocalDateTime.now().plusDays(3));

        book.setAvailable(false);
        bookRepository.save(book);
        reservationRepository.save(reservation);
    }

    public void cancelReservation(Long reservationId) throws Exception {
        Reservation reservation = reservationRepository.findById(reservationId);
        if (reservation == null) {
            throw new Exception("Nie znaleziono rezerwacji");
        }

        Book book = reservation.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        reservationRepository.delete(reservationId);
    }

    public List<Reservation> getUserReservations(User user) {
        return reservationRepository.findByUser(user);
    }

    public List<Reservation> getAllActiveReservations() {
        return reservationRepository.findAllActive();
    }
}