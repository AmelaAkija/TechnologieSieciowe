package org.ts.techsieciowelista2;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bookId;
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private Integer publishYear;
    private Integer availableCopies;
    @OneToMany(mappedBy = "bookLoan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Loan> bookLoanList;

    @OneToMany(mappedBy = "bookReview", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> bookReviewList;

    public List<Review> getBookReviewList() {
        return bookReviewList;
    }

    public void setBookReviewList(List<Review> bookReviewList) {
        this.bookReviewList = bookReviewList;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(Integer publishYear) {
        this.publishYear = publishYear;
    }

    public Integer getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(Integer availableCopies) {
        this.availableCopies = availableCopies;
    }

    public List<Loan> getBookLoanList() {
        return bookLoanList;
    }

    public void setBookLoanList(List<Loan> bookLoanList) {
        this.bookLoanList = bookLoanList;
    }
}

