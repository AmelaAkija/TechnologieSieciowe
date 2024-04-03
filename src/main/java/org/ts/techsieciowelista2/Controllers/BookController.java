package org.ts.techsieciowelista2.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.server.ResponseStatusException;
import org.ts.techsieciowelista2.Repositories.BookRepository;
import org.ts.techsieciowelista2.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.ts.techsieciowelista2.User;

@RestController
@RequestMapping("/Book")
public class BookController {
    private final BookRepository bookRepository;
    @Autowired
    public BookController(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping("/Add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Book addBook(@RequestBody Book book) {
        Book bookExists = bookRepository.findByIsbn(book.getIsbn());
        if (bookExists != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Book " + book.getIsbn() + " already in database.");
        }
        return bookRepository.save(book);
    }
    @GetMapping("/GetAll")
    public @ResponseBody Iterable<Book> getAllBooks(){
        return bookRepository.findAll();
    }
}
