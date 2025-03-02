package com.library.service;

import com.library.model.Book;
import com.library.model.Loan;
import com.library.model.Reservation;
import com.library.model.User;
import com.library.repository.BookRepository;
import com.library.repository.LoanRepository;
import com.library.repository.ReservationRepository;
import com.library.repository.UserRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class LoanService {
    @Inject
    private LoanRepository loanRepository;

    @Inject
    private BookRepository bookRepository;

    @Inject
    private UserRepository userRepository;

    public void createLoan(Long userId, Long bookId) throws Exception {
        User user = userRepository.findById(userId);
        Book book = bookRepository.findById(bookId);

        if (user == null) {
            throw new Exception("Nie znaleziono użytkownika");
        }

        if (book == null) {
            throw new Exception("Nie znaleziono książki");
        }

        if (!book.isAvailable()) {
            throw new Exception("Książka nie jest dostępna");
        }

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(LocalDateTime.now());
        loan.setDueDate(LocalDateTime.now().plusDays(30));

        book.setAvailable(false);
        bookRepository.save(book);
        loanRepository.save(loan);
    }

    public void returnBook(Long loanId) throws Exception {
        Loan loan = loanRepository.findById(loanId);
        if (loan == null) {
            throw new Exception("Nie znaleziono wypożyczenia");
        }

        loan.setReturnDate(LocalDateTime.now());
        Book book = loan.getBook();
        book.setAvailable(true);

        bookRepository.save(book);
        loanRepository.save(loan);
    }

    public List<Loan> getUserActiveLoans(User user) {
        return loanRepository.findActiveByUser(user);
    }

    public List<Loan> getAllActiveLoans() {
        return loanRepository.findAllActive();
    }

    @Inject
    private ReservationRepository reservationRepository;

    public void createLoanFromReservation(Long reservationId) throws Exception {
        Reservation reservation = reservationRepository.findById(reservationId);
        if (reservation == null) {
            throw new Exception("Rezerwacja nie istnieje");
        }

        if (reservation.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new Exception("Rezerwacja wygasła");
        }

        Loan loan = new Loan();
        loan.setUser(reservation.getUser());
        loan.setBook(reservation.getBook());
        loan.setLoanDate(LocalDateTime.now());
        loan.setDueDate(LocalDateTime.now().plusDays(30));

        try {
            loanRepository.save(loan);
            reservationRepository.delete(reservationId);
        } catch (Exception e) {
            throw new Exception("Nie udało się utworzyć wypożyczenia: " + e.getMessage());
        }
    }
}