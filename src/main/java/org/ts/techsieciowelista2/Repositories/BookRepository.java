package org.ts.techsieciowelista2.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.ts.techsieciowelista2.Book;
import org.ts.techsieciowelista2.User;

@Repository
public interface BookRepository extends CrudRepository<Book,Integer> {
    Book findByIsbn(String isbn);

}
