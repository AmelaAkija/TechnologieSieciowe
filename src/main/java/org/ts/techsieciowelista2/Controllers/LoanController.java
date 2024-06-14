package org.ts.techsieciowelista2.Controllers;

import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.server.ResponseStatusException;
import org.ts.techsieciowelista2.Book;
import org.ts.techsieciowelista2.User;
import org.ts.techsieciowelista2.exceptions.BookNotFoundException;
import org.ts.techsieciowelista2.exceptions.InvalidLoanStartDateException;
import org.ts.techsieciowelista2.Repositories.BookRepository;
import org.ts.techsieciowelista2.Repositories.LoanRepository;
import org.ts.techsieciowelista2.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.ts.techsieciowelista2.Repositories.UserRepository;
import org.ts.techsieciowelista2.dto.LoanDto;
import org.ts.techsieciowelista2.exceptions.UserAlreadyBorrowBookException;
import org.ts.techsieciowelista2.exceptions.UserNotFoundException;
import org.ts.techsieciowelista2.security.SecurityUtils;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Loan controller
 */
@RestController
@RequestMapping("/Loan")
public class LoanController {
    private final LoanRepository loanRepository;
    private final List<Loan> finishedLoans;
    private BookRepository bookRepository;
    private UserRepository userRepository;

    /**
     * @param loanRepository
     * @param bookRepository
     */
    @Autowired
    public LoanController(LoanRepository loanRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.finishedLoans = new ArrayList<>();
    }

    /**
     * @param loan to add
     * @return loan saved in loan repository
     * @throws ResponseStatusException If wanted book has no available copies
     */
    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping("/Add")
    @Transactional
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody Loan addLoan(@RequestBody Loan loan) throws UserAlreadyBorrowBookException, UserNotFoundException, BookNotFoundException {
        LocalDate currentDate = LocalDate.now();
        LocalDate StartDate = loan.getLoanDateStart().toLocalDate();
        if (loan.getLoanDateStart() == null || !StartDate.isEqual(currentDate)) {
            throw new InvalidLoanStartDateException("The start date of the loan must be the current date.");
        }
        if (loan.getBookLoan() != null) {
            loan.setLoanBookId(loan.getBookLoan().getBookId());
        }
        if (loan.getUserLoan() != null) {
            loan.setLoanUserId(loan.getUserLoan().getUserId());
        }

        if(loanRepository.existsByLoanUserIdAndLoanBookIdAndLoanDateEnd(loan.getLoanUserId(), loan.getLoanBookId(), null)) {
            throw new UserAlreadyBorrowBookException("User already borrow this book");
        }
        Optional<Book> bookOptional = bookRepository.findById(loan.getLoanBookId());
        if (!bookOptional.isPresent()) {
            throw new BookNotFoundException("Book with ID " + loan.getLoanBookId() + " not found.");
        }

        Optional<User> userOpt = userRepository.findById(loan.getLoanUserId());
        if (!userOpt.isPresent()) {
            throw new UserNotFoundException("User with ID " + loan.getLoanUserId() + " not found.");
        }


        Loan newLoan = loanRepository.save(loan);
        Optional<Book> bookOpt = bookRepository.findById(newLoan.getLoanBookId());
        if(bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            bookRepository.save(book);
        }



        return newLoan;
    }



    /**
     * @return all loans in database
     */
    @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping("/GetAll")
    public @ResponseBody Iterable<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    /**
     * @param loanId of loan to be deleted
     * @return deleted loan
     */
    @DeleteMapping("/deleteLoan/{loanId}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    String removeLoan(@PathVariable Integer loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan with id " + loanId + " not found"));


        Book book = loan.getBookLoan();


        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        loanRepository.deleteById(loanId);
        return "Loan " + loanId + " has been successfully deleted";

    }

    /**
     * @param loanId of loan to be updated
     * @return updated loan
     * @throws ResponseStatusException If loan with id is not in database
     */
    @PutMapping("/return/{loanId}")
    @Transactional
    @PreAuthorize("hasRole('LIBRARIAN')")
    public String updateLoan(@PathVariable Integer loanId, @RequestBody Loan loan) {
        if (loanRepository.existsById(loanId)) {
            loanRepository.updateLoan(loanId, loan.getLoanDateEnd());
            loan = loanRepository.findByLoanId(loanId);

            Book book = loan.getBookLoan();
            book.setAvailableCopies(book.getAvailableCopies() + 1);
            bookRepository.save(book);

            Date startdate = loan.getLoanDateStart();
            LocalDate localDate = startdate.toLocalDate();
            LocalDate expectedReturnDate = localDate.plusDays(loan.getLoanPeriod());
            LocalDate returnDate = loan.getLoanDateEnd().toLocalDate();
            if (returnDate.compareTo(expectedReturnDate) > 0) {
                return "Book returned after allowed period";
            } else {
                return "Book returned in time";
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan with id " + loanId + " not found");
        }
    }

    @GetMapping("/SearchBy/ID/{id}")
    public Optional<Loan> searchById(@PathVariable int id) {
        return loanRepository.findById(id);
    }

    /**
     * @return indexes of borrowed books along with the start date and period
     */
    @GetMapping("/GetUserLoans")
    public @ResponseBody Iterable<LoanDto> getLoansByUser() {
        Integer userId = SecurityUtils.getAuthenticatedUserId();
        return loanRepository.findAllByLoanUserId(userId)
                .stream()
                .map(loan -> new LoanDto(loan.getLoanId(), loan.getLoanDateStart(), loan.getLoanDateEnd(), loan.getLoanPeriod(), loan.getBookLoan().getTitle()))
                .toList();
    }

}


