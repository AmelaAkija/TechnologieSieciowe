package org.ts.techsieciowelista2.Controllers;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.server.ResponseStatusException;
import org.ts.techsieciowelista2.Book;
import org.ts.techsieciowelista2.InvalidLoanStartDateException;
import org.ts.techsieciowelista2.Repositories.BookRepository;
import org.ts.techsieciowelista2.Repositories.LoanRepository;
import org.ts.techsieciowelista2.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.ts.techsieciowelista2.Repositories.UserRepository;
import org.ts.techsieciowelista2.User;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    public @ResponseBody Loan addLoan(@RequestBody Loan loan) {
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

        return loanRepository.save(loan);
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
        if (loanRepository.existsById(loanId)) {
            loanRepository.deleteById(loanId);
            return "Loan " + loanId + " has been successfully deleted";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan with id " + loanId + " not found");
        }

    }

    /**
     * @param loanId of loan to be updated
     * @return updated loan
     * @throws ResponseStatusException If loan with id is not in database
     */
    @PutMapping("/{loanId}")
    @Transactional
    @PreAuthorize("hasRole('LIBRARIAN')")
    public String updateLoan(@PathVariable Integer loanId, @RequestBody Loan loan) {
        if (loanRepository.existsById(loanId)) {
            loanRepository.updateLoan(loanId, loan.getLoanDateEnd());
            loan = loanRepository.findByLoanId(loanId);
            Book book = loan.getBookLoan();
            book.setAvailableCopies(book.getAvailableCopies()+1);
            book.setAvailableCopies(book.getAvailableCopies()+1);
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

    /**
     * @param userId of the user
     * @return indexes of borrowed books along with the start date and period
     */
    @GetMapping("/GetLoansByUser/{userId}")
    public @ResponseBody Iterable<Object[]> getLoansByUser(@PathVariable int userId) {
        return loanRepository.findBorrowedBooks(userId);
    }

    }


