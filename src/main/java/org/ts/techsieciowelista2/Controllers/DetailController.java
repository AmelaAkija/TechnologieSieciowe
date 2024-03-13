package org.ts.techsieciowelista2.Controllers;

import org.ts.techsieciowelista2.Repositories.BookRepository;
import org.ts.techsieciowelista2.Detail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.ts.techsieciowelista2.Repositories.DetailRepository;

@RestController
@RequestMapping("/Detail")
public class DetailController {
    private final DetailRepository detailRepository;
    @Autowired
    public DetailController(DetailRepository detailRepository){
        this.detailRepository = detailRepository;
    }
    @PostMapping("/Add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Detail addBook(@RequestBody Detail detail) {
        return detailRepository.save(detail);
    }
    @GetMapping("/GetAll")
    public @ResponseBody Iterable<Detail> getAllDetails(){
        return detailRepository.findAll();
    }
}
