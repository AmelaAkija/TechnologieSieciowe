package org.ts.techsieciowelista2.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.ts.techsieciowelista2.Book;

@Repository
public interface BookRepository extends CrudRepository<Book,Integer> {
    Book findByIsbn(String isbn);
    Iterable<Book> findByTitle(String title);
    Iterable<Book> findByAuthor(String author);
    @Modifying
    @Transactional
    @Query("update Book b set b.title = :title, b.author= :author, b.publisher =:publisher, " +
            "b.publishYear=:publishYear, b.availableCopies = :availableCopies where b.bookId = :id")
    void updateBook(@Param(value = "id") int id, @Param(value = "title") String title,
                    @Param(value = "author") String author, @Param(value = "publisher") String publisher,
                    @Param(value = "publishYear") Integer publishYear, @Param(value = "availableCopies") Integer availableCopies);

}
