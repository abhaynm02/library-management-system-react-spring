package com.abhaynm.library_management.service.serviceImp;

import com.abhaynm.library_management.dto.ProfileDto;
import com.abhaynm.library_management.dto.TransactionDto;
import com.abhaynm.library_management.exceptions.coustomexceptions.BookNotFoundException;
import com.abhaynm.library_management.model.Book;
import com.abhaynm.library_management.model.Transaction;
import com.abhaynm.library_management.model.TransactionStatus;
import com.abhaynm.library_management.model.UserEntity;
import com.abhaynm.library_management.repository.BookRepository;
import com.abhaynm.library_management.repository.UserRepository;
import com.abhaynm.library_management.service.JwtService;
import com.abhaynm.library_management.service.UserService;
import com.abhaynm.library_management.utility.IDGenerator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BookRepository bookRepository;




    @Override
    public void editUserProfile(String token, String name) {
        UserEntity user=findUserWhitJwt(token);
        user.setName(name);
        userRepository.save(user);
    }

    @Override
    public List<TransactionDto> findTransactionForUsers(String token) {
        UserEntity user=findUserWhitJwt(token);
        return user.getTransactions().stream()
                .map(transaction -> new TransactionDto(
                        transaction.getId(),
                        transaction.getBook().getId(),
                        transaction.getBook().getBookName(),
                        transaction.getBorrowedDate(),
                        transaction.getReturnedDate(),
                        transaction.getStatus().name()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public String borrowBook(String token, String bookId) {

        UserEntity user = findUserWhitJwt(token); // Ensure this method throws an exception if the user is not found

        // Check if the user has already borrowed 5 books
        if (userHasReachedBorrowLimit(user)) {
            return "You have reached the maximum borrow limit of 5 books.";
        }

        // Check if the user has already borrowed the specific book
        if (userHasAlreadyBorrowedBook(user, bookId)) {
            return "You have already borrowed this book.";
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found with this ID"));
        if (book.getAvailableCopies()<=0){
            return "Book is not currently available";
        }

        // Create and add the new transaction
        createBorrowTransaction(user, book);

        return "Book borrowed successfully.";
    }

    private boolean userHasReachedBorrowLimit(UserEntity user) {
        long borrowedCount = user.getTransactions().stream()
                .filter(t -> t.getStatus().equals(TransactionStatus.BORROWED))
                .count();
        return borrowedCount >= 5;
    }

    private boolean userHasAlreadyBorrowedBook(UserEntity user, String bookId) {
        return user.getTransactions().stream()
                .anyMatch(t -> t.getBook().getId().equals(bookId) && t.getStatus().equals(TransactionStatus.BORROWED));
    }

    private void createBorrowTransaction(UserEntity user, Book book) {
        Transaction transaction = new Transaction();
        transaction.setId(IDGenerator.generateUniqueId());
        transaction.setBook(book);
        transaction.setUser(user);
        transaction.setBorrowedDate(LocalDate.now());
        transaction.setStatus(TransactionStatus.BORROWED);

        book.setAvailableCopies(book.getTotalCopies()-1);

        user.getTransactions().add(transaction);
        book.getTransactions().add(transaction);

        bookRepository.save(book);  // Ensure the book status is updated
        userRepository.save(user);  // Ensure the user transactions are saved
    }

    @Override
    public String returnBook(String token, String bookId) {

        UserEntity user = findUserWhitJwt(token);
        List<Transaction> transactions = user.getTransactions();

        Transaction borrowedTransaction = transactions.stream()
                .filter(t -> t.getBook().getId().equals(bookId) && t.getStatus().equals(TransactionStatus.BORROWED))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("This book has not been borrowed by you or does not exist."));

        borrowedTransaction.setStatus(TransactionStatus.RETURNED);
        borrowedTransaction.setReturnedDate(LocalDate.now());
        Book book = borrowedTransaction.getBook();
        book.setAvailableCopies(book.getAvailableCopies()+1);
        if (book.getAvailableCopies()>book.getTotalCopies()){
            return "please check the book limit is not correct";
        }
        bookRepository.save(book);
        userRepository.save(user);

        return "Book returned successfully.";
    }

    @Override
    public ProfileDto getProfile(String token) {
        UserEntity user=findUserWhitJwt(token);
        return new ProfileDto(user.getId(),user.getName(),user.getEmail(),user.getRole(),user.getTransactions().size());
    }

    @Override
    public List<ProfileDto> getAllUsers() {
        List<UserEntity>allUsers=userRepository.findAll();
      return   allUsers.stream().map(user->{
            return new ProfileDto(user.getId(),user.getName(),user.getEmail(),user.getRole(),user.getTransactions().size());
        }).toList();
    }

    @Override
    public List<TransactionDto> findTransactionsForAdmin(String userName) {
        UserEntity user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new BookNotFoundException("User not found with this email"));

        return user.getTransactions().stream()
                .map(transaction -> new TransactionDto(
                        transaction.getId(),
                        transaction.getBook().getId(),
                        transaction.getBook().getBookName(),
                        transaction.getBorrowedDate(),
                        transaction.getReturnedDate(),
                        transaction.getStatus().name()
                ))
                .collect(Collectors.toList());
    }


    private UserEntity findUserWhitJwt(String token){
        String userName=jwtService.extractUsername(token.substring(7));
        Optional<UserEntity> user=userRepository.findByEmail(userName);
        if (user.isPresent()){
            return user.get();
        }else {
            throw new RuntimeException("user not found with this email");
        }

    }
}
