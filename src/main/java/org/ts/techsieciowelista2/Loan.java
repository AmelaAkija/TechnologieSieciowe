package org.ts.techsieciowelista2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Date;


/**
 * Loan entity
 */
@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer loanId;
    private Date loanDateStart;
    private Integer loanPeriod;
    private Date loanDateEnd;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore
    @JoinColumn(name = "user_id",insertable = false, updatable = false)
    private User userLoan;

    @Column(name="user_id")//
    private Integer loanUserId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore
    @JoinColumn(name = "book_id",insertable = false, updatable = false)
    private Book bookLoan;
    @Column(name="book_id")//,insertable = false, updatable = false)
    private Integer loanBookId;

    public Date getLoanDateStart() {
        return loanDateStart;
    }

    public void setLoanDateStart(Date loanDateStart) {
        this.loanDateStart = loanDateStart;
    }

    public Date getLoanDateEnd() {
        return loanDateEnd;
    }

    public void setLoanDateEnd(Date loanDateEnd) {
        this.loanDateEnd = loanDateEnd;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }



    public Integer getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(Integer loanPeriod) {
        this.loanPeriod = loanPeriod;
    }



    public User getUserLoan() {
        return userLoan;
    }

    public void setUserLoan(User userLoan) {
        this.userLoan = userLoan;
    }

    public Book getBookLoan() {
        return bookLoan;
    }

    public void setBookLoan(Book bookLoan) {
        this.bookLoan = bookLoan;
    }

    public Integer getLoanUserId() {
        return loanUserId;
    }

    public void setLoanUserId(Integer loanUserId) {
        this.loanUserId = loanUserId;
    }

    public Integer getLoanBookId() {
        return loanBookId;
    }

    public void setLoanBookId(Integer loanBookId) {
        this.loanBookId = loanBookId;
    }
}
