package org.ts.techsieciowelista2.Controllers;

import org.springframework.web.server.ResponseStatusException;
import org.ts.techsieciowelista2.Repositories.ReviewRepository;
import org.ts.techsieciowelista2.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


/**
 * Review controller
 */
@RestController
@RequestMapping("/Review")
public class ReviewController {
    private final ReviewRepository reviewRepository;

    /**
     * @param reviewRepository
     */
    @Autowired
    public ReviewController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    /**
     * @param review to add
     * @return review saved in review repository
     */
    @PostMapping("/Add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody Review addReview(@RequestBody Review review) {
        return reviewRepository.save(review);
    }

    /**
     * @return all reviews
     */
    @GetMapping("/GetAll")
    public @ResponseBody Iterable<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
}
