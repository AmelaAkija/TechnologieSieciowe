package org.ts.techsieciowelista2;

import jakarta.persistence.*;


@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer loanId;
    private String loanDateStart;
    private Integer loanPeriod;
    private String loanDateEnd;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User userLoan;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "book_id")
    private Book bookLoan;

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public String getLoanDateStart() {
        return loanDateStart;
    }

    public void setLoanDateStart(String loanDateStart) {
        this.loanDateStart = loanDateStart;
    }

    public Integer getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(Integer loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public String getLoanDateEnd() {
        return loanDateEnd;
    }

    public void setLoanDateEnd(String loanDateEnd) {
        this.loanDateEnd = loanDateEnd;
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
}
