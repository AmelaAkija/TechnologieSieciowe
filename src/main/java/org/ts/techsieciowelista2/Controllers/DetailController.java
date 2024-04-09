package org.ts.techsieciowelista2.Controllers;

import org.springframework.web.server.ResponseStatusException;
import org.ts.techsieciowelista2.Repositories.BookRepository;
import org.ts.techsieciowelista2.Detail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.ts.techsieciowelista2.Repositories.DetailRepository;

/**
 * Detail controller
 */
@RestController
@RequestMapping("/Detail")
public class DetailController {
    private final DetailRepository detailRepository;

    /**
     * @param detailRepository
     */
    @Autowired
    public DetailController(DetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }

    /**
     * @param detail to add
     * @return detail saved in detail repository
     */
    @PostMapping("/Add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Detail addBook(@RequestBody Detail detail) {
        return detailRepository.save(detail);
    }

    /**
     * @return all details of books in database
     */
    @GetMapping("/GetAll")
    public @ResponseBody Iterable<Detail> getAllDetails() {
        return detailRepository.findAll();
    }
}
