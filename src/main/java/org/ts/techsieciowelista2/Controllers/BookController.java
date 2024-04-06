package org.ts.techsieciowelista2.Controllers;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import org.ts.techsieciowelista2.Repositories.BookRepository;
import org.ts.techsieciowelista2.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.ts.techsieciowelista2.User;
import org.ts.techsieciowelista2.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/Book")
public class BookController {
    private final BookRepository bookRepository;
    private BookService bookService;

    @Autowired
    public BookController(BookRepository bookRepository, BookService bookService){
        this.bookRepository = bookRepository;
        this.bookService = bookService;
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
        return bookService.getAllBooks();
    }
    @GetMapping("/SearchBy/Isbn/{isbn}")
    public Book searchByIsbn(@PathVariable String isbn) {
        return bookRepository.findByIsbn(isbn);
    }
    @GetMapping("/SearchBy/title/{title}")
    public @ResponseBody Iterable<Book> searchByTitle(@PathVariable String title) {
        return bookRepository.findByTitle(title);
    }
    @GetMapping("/SearchBy/author/{author}")
    public @ResponseBody Iterable<Book> searchByAuthor(@PathVariable String author) {
        return bookRepository.findByAuthor(author);
    }
    @PutMapping("/updateBook/{bookId}")
    @Transactional
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<String> updateBook(@PathVariable int bookId, @RequestBody Book book) {
        bookRepository.updateBook(bookId, book.getTitle(), book.getAuthor(), book.getPublisher(), book.getPublishYear(), book.getAvailableCopies());
        return ResponseEntity.ok("Book with id " + bookId + " has been updated");
    }
    @DeleteMapping("/deleteBook/{bookId}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    String removeBook(@PathVariable Integer bookId){
        if (bookRepository.existsById(bookId)) {
            bookRepository.deleteById(bookId);
            return "Book with id " + bookId + " has been successfully deleted";}
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with id " + bookId + " not found");
        }
}
}
