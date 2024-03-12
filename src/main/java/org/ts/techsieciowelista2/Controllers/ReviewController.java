package org.ts.techsieciowelista2.Controllers;

import org.ts.techsieciowelista2.Repositories.ReviewRepository;
import org.ts.techsieciowelista2.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/Review")
public class ReviewController {
    private final ReviewRepository reviewRepository;


    @Autowired
    public ReviewController(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }
    @PostMapping("/Add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody Review addReview(@RequestBody Review review) {
        return reviewRepository.save(review);
    }
    @GetMapping("/GetAll")
    public @ResponseBody Iterable<Review> getAllReviews(){
        return reviewRepository.findAll();
    }
}
