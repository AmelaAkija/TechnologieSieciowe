package org.ts.techsieciowelista2.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.ts.techsieciowelista2.Book;
import org.ts.techsieciowelista2.User;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book,Integer> {
    Book findByIsbn(String isbn);
    Iterable<Book> findByTitle(String title);
    Iterable<Book> findByAuthor(String author);

}
