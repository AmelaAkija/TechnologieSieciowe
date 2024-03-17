package org.ts.techsieciowelista2.Controllers;

import org.ts.techsieciowelista2.Repositories.LoanRepository;
import org.ts.techsieciowelista2.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Loan")
public class LoanController {
    private final LoanRepository loanRepository;
    @Autowired
    public LoanController(LoanRepository loanRepository){
        this.loanRepository = loanRepository;
    }
    @PostMapping("/Add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody Loan addLoan(@RequestBody Loan loan) {
        return loanRepository.save(loan);
    }
    @GetMapping("/GetAll")
    public @ResponseBody Iterable<Loan> getAllLoans(){
        return loanRepository.findAll();
    }
}
