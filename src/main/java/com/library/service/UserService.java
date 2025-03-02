package com.library.service;

import com.library.model.User;
import com.library.model.UserRole;
import com.library.repository.LoanRepository;
import com.library.repository.ReservationRepository;
import com.library.repository.UserRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;

@Stateless
public class UserService {
    @Inject
    private UserRepository userRepository;

    @Inject
    private LoanRepository loanRepository;

    @Inject
    private ReservationRepository reservationRepository;

    public User findByCardNumber(String cardNumber) {
        return userRepository.findByCardNumber(cardNumber);
    }

    public void createUser(User user) throws Exception {
        try {
            User existingUser = userRepository.findByCardNumber(user.getCardNumber());
            if (existingUser != null) {
                throw new Exception("Użytkownik o podanym numerze karty już istnieje");
            }

            userRepository.save(user);
        } catch (Exception e) {
            if (e.getMessage().equals("Użytkownik o podanym numerze karty już istnieje")) {
                throw e;
            }
            throw new Exception("Nie udało się utworzyć użytkownika");
        }
    }

    public List<User> getAllReaders() {
        List<User> readers = userRepository.findAllReaders();
        for (User reader : readers) {
            reader.setActiveLoansCount(loanRepository.countActiveLoansForUser(reader));
            reader.setActiveReservationsCount(reservationRepository.countActiveReservationsForUser(reader));
        }
        return readers;
    }

    public void updateReader(Long id, String cardNumber, String fullName, String password) throws Exception {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new Exception("Nie znaleziono użytkownika");
        }

        if (user.getRole() != UserRole.READER) {
            throw new Exception("Można edytować tylko konta czytelników");
        }

        User existingUser = userRepository.findByCardNumber(cardNumber);
        if (existingUser != null && !existingUser.getId().equals(id)) {
            throw new Exception("Podany numer karty jest już zajęty");
        }

        user.setCardNumber(cardNumber);
        user.setName(fullName);

        if (password != null && !password.trim().isEmpty()) {
            user.setPassword(password);
        }

        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new Exception("Nie udało się zaktualizować danych czytelnika: " + e.getMessage());
        }
    }

    public void deleteUser(Long id) throws Exception {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new Exception("Nie znaleziono użytkownika");
        }

        if (user.getRole() != UserRole.READER) {
            throw new Exception("Można usuwać tylko konta czytelników");
        }

        try {
            userRepository.delete(id);
        } catch (Exception e) {
            throw new Exception("Nie można usunąć użytkownika. " + e.getMessage());
        }
    }

}