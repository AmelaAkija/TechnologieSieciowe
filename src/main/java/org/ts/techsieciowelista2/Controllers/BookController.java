package org.ts.techsieciowelista2.Controllers;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.server.ResponseStatusException;
import org.ts.techsieciowelista2.Repositories.BookRepository;
import org.ts.techsieciowelista2.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import java.util.Optional;

/**
 * Book controller
 */
@RestController
@RequestMapping("/Book")
public class BookController {
    private final BookRepository bookRepository;


    /**
     * @param bookRepository
     */
    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * @param book to add
     * @return book saved in book repository
     * @throws ResponseStatusException If book with the same isbn is already in database
     */
//    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping("/Add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Book addBook(@RequestBody Book book) {
        Book bookExists = bookRepository.findByIsbn(book.getIsbn());
        if (bookExists != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Book with ISBN " + book.getIsbn() + " already in database.");
        }
        return bookRepository.save(book);
    }

    /**
     * @param isbn of wanted book
     * @return book with the given isbn
     */
    @GetMapping("/SearchBy/Isbn/{isbn}")
    public Book searchByIsbn(@PathVariable String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @GetMapping("/SearchBy/ID/{id}")
    public Book searchById(@PathVariable int id) {
        return bookRepository.findById(id);
    }

    /**
     * @param title of wanted book
     * @return book with the given title
     */
    @GetMapping("/SearchBy/title/{title}")
    public @ResponseBody Iterable<Book> searchByTitle(@PathVariable String title) {
        return bookRepository.findByTitle(title);
    }

    /**
     * @param author of wanted book
     * @return book with the given author
     */
    @GetMapping("/SearchBy/author/{author}")
    public @ResponseBody Iterable<Book> searchByAuthor(@PathVariable String author) {
        return bookRepository.findByAuthor(author);
    }

    /**
     * @return all books in database
     */
    @GetMapping("/GetAll")
    public @ResponseBody Iterable<Book> getAllBooks() {
        Iterable<Book> books = bookRepository.findAll();
        for (Book book : books) {
            book.setBookLoanList(null);
        }
        return books;
    }

    /**
     * @param bookId of book to be updated
     * @return updated book
     */

    @PutMapping("/updateBook/{bookId}")
    @Transactional
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<String> updateBook(@PathVariable int bookId, @RequestBody Book book) {
        Optional<Book> optionalBook = Optional.ofNullable(bookRepository.findById(bookId));
        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();

            // Update only if the new value is not null or empty
            if (book.getTitle() != null && !book.getTitle().isEmpty()) {
                existingBook.setTitle(book.getTitle());
            }
            if (book.getAuthor() != null && !book.getAuthor().isEmpty()) {
                existingBook.setAuthor(book.getAuthor());
            }
            if (book.getPublisher() != null && !book.getPublisher().isEmpty()) {
                existingBook.setPublisher(book.getPublisher());
            }
            if (book.getPublishYear() != null) {
                existingBook.setPublishYear(book.getPublishYear());
            }
            if (book.getAvailableCopies() != null) {
                existingBook.setAvailableCopies(book.getAvailableCopies());
            }

            bookRepository.save(existingBook);

            return ResponseEntity.ok("Book with id " + bookId + " has been updated");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * @param bookId of book to be deleted
     * @return deleted book
     * @throws ResponseStatusException If book with id is not in database
     */
    @DeleteMapping("/deleteBook/{bookId}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    String removeBook(@PathVariable Integer bookId) {
        if (bookRepository.existsById(bookId)) {
            bookRepository.deleteById(bookId);
            return "Book with id " + bookId + " has been successfully deleted";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with id " + bookId + " not found");
        }
    }
}
