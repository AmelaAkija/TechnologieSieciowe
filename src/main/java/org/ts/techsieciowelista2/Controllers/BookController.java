package org.ts.techsieciowelista2.Controllers;

import org.ts.techsieciowelista2.Repositories.BookRepository;
import org.ts.techsieciowelista2.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Book")
public class BookController {
    private final BookRepository bookRepository;
    @Autowired
    public BookController(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
    @PostMapping("/Add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }
    @GetMapping("/GetAll")
    public @ResponseBody Iterable<Book> getAllBooks(){
        return bookRepository.findAll();
    }
}
