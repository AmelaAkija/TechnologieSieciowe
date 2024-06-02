package org.ts.techsieciowelista2;


import java.util.Date;

public class LoanProjection {
    private Integer loanId;
    private Date loanDateStart;
    private Integer loanPeriod;
    private Date loanDateEnd;
    private Integer userId;
    private Integer bookId;

    public LoanProjection(Integer loanId, Date loanDateStart, Integer loanPeriod, Date loanDateEnd, Integer userId, Integer bookId) {
        this.loanId = loanId;
        this.loanDateStart = loanDateStart;
        this.loanPeriod = loanPeriod;
        this.loanDateEnd = loanDateEnd;
        this.userId = userId;
        this.bookId = bookId;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Date getLoanDateStart() {
        return loanDateStart;
    }

    public void setLoanDateStart(Date loanDateStart) {
        this.loanDateStart = loanDateStart;
    }

    public Integer getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(Integer loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public Date getLoanDateEnd() {
        return loanDateEnd;
    }

    public void setLoanDateEnd(Date loanDateEnd) {
        this.loanDateEnd = loanDateEnd;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
}
