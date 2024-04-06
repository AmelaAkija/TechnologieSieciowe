package org.ts.techsieciowelista2.Repositories;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.ts.techsieciowelista2.Book;
import org.ts.techsieciowelista2.Loan;

@Repository
public interface LoanRepository extends CrudRepository<Loan,Integer> {
    Loan findByLoanId(Integer loanId);
}
