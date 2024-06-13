package org.ts.techsieciowelista2.dto;

import java.sql.Date;

public class LoanDto {
    private Integer loanId;
    private Date loanDateStart;
    private Date loanDateEnd;
    private Integer loanPeriod;
    private String title;

    public LoanDto() {

    }

    public LoanDto(Integer loanId, Date loanDateStart, Date loanDateEnd, Integer loanPeriod, String title) {
        this.loanId = loanId;
        this.loanDateStart = loanDateStart;
        this.loanDateEnd = loanDateEnd;
        this.loanPeriod = loanPeriod;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getLoanDateEnd() {
        return loanDateEnd;
    }

    public void setLoanDateEnd(Date loanDateEnd) {
        this.loanDateEnd = loanDateEnd;
    }
}
