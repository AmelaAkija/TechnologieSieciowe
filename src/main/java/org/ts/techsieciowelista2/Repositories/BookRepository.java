package org.ts.techsieciowelista2.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.ts.techsieciowelista2.Book;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book,Integer> {
    Book findById(int bookId);
    Book findByIsbn(String isbn);
    @Query("SELECT b FROM Book b WHERE LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))")
    Iterable<Book> findByAuthorContaining(@Param("author") String author);

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    Iterable<Book> findByTitleContaining(@Param("title") String title);
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
