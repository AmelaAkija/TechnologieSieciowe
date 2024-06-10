package org.ts.techsieciowelista2.Repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.ts.techsieciowelista2.Loan;

import java.sql.Date;

@Repository
public interface LoanRepository extends CrudRepository<Loan,Integer> {
    Loan findByLoanId(Integer loanId);

    @Modifying
    @Query("update Loan l set l.loanDateEnd = :loanDateEnd where l.loanId = :loanId")
    void updateLoan(@Param("loanId") Integer loanId, @Param("loanDateEnd") Date loanDateEnd);

    @Query("SELECT b.title, l.loanDateStart, l.loanPeriod FROM Loan l JOIN l.bookLoan b WHERE l.userLoan.userId = :userId")
    Iterable<Object[]> findBorrowedBooks(@Param("userId") int userId);
}
