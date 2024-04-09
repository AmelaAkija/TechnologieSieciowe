package org.ts.techsieciowelista2.Controllers;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.server.ResponseStatusException;
import org.ts.techsieciowelista2.Book;
import org.ts.techsieciowelista2.Repositories.BookRepository;
import org.ts.techsieciowelista2.Repositories.LoanRepository;
import org.ts.techsieciowelista2.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/Loan")
public class LoanController {
    private final LoanRepository loanRepository;
    private BookRepository bookRepository;
    @Autowired
    public LoanController(LoanRepository loanRepository, BookRepository bookRepository){
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
    }
    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping("/Add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody Loan addLoan(@RequestBody Loan loan) {
        Loan savedLoan = loanRepository.save(loan);
        Optional<Book> optBook = bookRepository.findById(loan.getBookLoan().getBookId());
        if (optBook.isPresent()) {
            Book book = optBook.get();
            if(book.getAvailableCopies()<=0){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,"No available copies of book");
            }
            else{
            int availableCopies = book.getAvailableCopies() - 1;
            book.setAvailableCopies(availableCopies);
            bookRepository.save(book);}
        }
        return savedLoan;
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping("/GetAll")
    public @ResponseBody Iterable<Loan> getAllLoans(){
        return loanRepository.findAll();
    }

    @DeleteMapping("/deleteLoan/{loanId}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    String removeLoan(@PathVariable Integer loanId) {
        if (loanRepository.existsById(loanId)) {
            loanRepository.deleteById(loanId);
            return "Loan " + loanId + " has been successfully deleted";}
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan with id " + loanId + " not found");}
//        Loan loan = loanRepository.findByLoanId(loanId);
//        Date startdate = loan.getLoanDateStart();
//        LocalDate localDate = startdate.toLocalDate();
//        LocalDate expectedReturnDate = localDate.plusDays(loan.getLoanPeriod());
//        LocalDate returnDate = loan.getLoanDateEnd().toLocalDate();
//        if (loanRepository.existsById(loanId)) {
//            loanRepository.deleteById(loanId);
//            if(returnDate.compareTo(expectedReturnDate)>0){
//                return "Book returned after allowed period";
//            }
//            else{
//                return "Book returned in time";
//            }}
//        else{
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan with id " + loanId + " not found");}

    }
    @PutMapping("/{loanId}")
    @Transactional
    @PreAuthorize("hasRole('LIBRARIAN')")
    public String updateLoan(@PathVariable Integer loanId, @RequestBody Loan loan) {
//        loan = loanRepository.findByLoanId(loanId);
//        Date startdate = loan.getLoanDateStart();
//        LocalDate localDate = startdate.toLocalDate();
//        LocalDate expectedReturnDate = localDate.plusDays(loan.getLoanPeriod());
//        LocalDate returnDate = loan.getLoanDateEnd().toLocalDate();
        if (loanRepository.existsById(loanId)) {
            loanRepository.updateLoan(loanId, loan.getLoanDateEnd());
            loan = loanRepository.findByLoanId(loanId);
            Date startdate = loan.getLoanDateStart();
            LocalDate localDate = startdate.toLocalDate();
            LocalDate expectedReturnDate = localDate.plusDays(loan.getLoanPeriod());
            LocalDate returnDate = loan.getLoanDateEnd().toLocalDate();
            if(returnDate.compareTo(expectedReturnDate)>0){
                return "Book returned after allowed period";
            }
            else{
                return "Book returned in time";
            }}
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan with id " + loanId + " not found");}

    }

}
