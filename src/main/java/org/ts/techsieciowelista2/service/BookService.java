package org.ts.techsieciowelista2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ts.techsieciowelista2.Book;
import org.ts.techsieciowelista2.Repositories.BookRepository;

@Service
public class BookService {
    private BookRepository bookRepository;
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> getAllBooks(){
        return bookRepository.findAll();
    }
}
