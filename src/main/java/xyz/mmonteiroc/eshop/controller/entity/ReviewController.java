package xyz.mmonteiroc.eshop.controller.entity;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.mmonteiroc.eshop.entity.Item;
import xyz.mmonteiroc.eshop.entity.Review;
import xyz.mmonteiroc.eshop.entity.User;
import xyz.mmonteiroc.eshop.exceptions.ItemNotFoundException;
import xyz.mmonteiroc.eshop.exceptions.ReviewNotFoundException;
import xyz.mmonteiroc.eshop.exceptions.TokenNotValidException;
import xyz.mmonteiroc.eshop.manager.ItemManager;
import xyz.mmonteiroc.eshop.manager.ReviewManager;
import xyz.mmonteiroc.eshop.manager.TokenManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * Code created by: mmonteiroc
 * Email: miguelmonteiroclaveri@gmail.com
 * Github: https://github.com/mmonteiroc
 * LinkedIn: https://www.linkedin.com/in/mmonteiroc/?locale=en_US
 * Date of creation: 29/08/2020
 * Package: xyz.mmonteiroc.eshop.controller.entity
 * Project: eshop
 */
@RestController
public class ReviewController {

    @Autowired
    private ReviewManager reviewManager;

    @Autowired
    private Gson gson;

    @Autowired
    private ItemManager itemManager;

    @Autowired
    private TokenManager tokenManager;

    @GetMapping("/reviews")
    public ResponseEntity<Set<Review>> findAllReviews() {
        return ResponseEntity.ok(this.reviewManager.findAll());
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<Review> findById(@PathVariable(name = "id") Long id) throws ReviewNotFoundException {
        return ResponseEntity.ok(this.reviewManager.findById(id));
    }

    @PostMapping("/reviews")
    public ResponseEntity<String> newReview(@RequestBody String json, HttpServletRequest request) throws ItemNotFoundException, TokenNotValidException {
        Review review = gson.fromJson(json, Review.class);

        if (review.getStars() == null
                || review.getStars() > 5
                || review.getStars() < 1) {
            return ResponseEntity.badRequest().body("Number of stars not accepted");
        } else if (review.getIdItem() == null) return ResponseEntity.badRequest().body("Needed id of item");
        Item item = this.itemManager.findById(review.getIdItem());
        User user = this.tokenManager.getUsuarioFromToken((String) request.getAttribute("userToken"));
        Review reviewVerifyNotDone = this.reviewManager.findByItemAndUser(item, user);

        if (reviewVerifyNotDone != null) {
            return ResponseEntity.badRequest().body("You already reviewed this item");
        }

        review.setItem(item);
        review.setUser(user);
        this.reviewManager.createOrUpdate(review);
        return ResponseEntity.ok("Review created successfully");
    }
}
