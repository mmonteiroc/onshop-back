package xyz.mmonteiroc.eshop.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.mmonteiroc.eshop.entity.Item;
import xyz.mmonteiroc.eshop.entity.Review;
import xyz.mmonteiroc.eshop.entity.User;
import xyz.mmonteiroc.eshop.exceptions.ReviewNotFoundException;
import xyz.mmonteiroc.eshop.repository.ReviewRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Code created by: mmonteiroc
 * Email: miguelmonteiroclaveri@gmail.com
 * Github: https://github.com/mmonteiroc
 * LinkedIn: https://www.linkedin.com/in/mmonteiroc/?locale=en_US
 * Date of creation: 29/08/2020
 * Package: xyz.mmonteiroc.eshop.manager
 * Project: eshop
 */
@Service
public class ReviewManager {

    @Autowired
    private ReviewRepository reviewRepository;

    public Review findById(Long id) throws ReviewNotFoundException {
        Review toReturn = this.reviewRepository.findByIdReview(id);
        if (toReturn == null) throw new ReviewNotFoundException("Review with id {" + id + "} not found");
        return toReturn;
    }

    public Review findByItemAndUser(Item item, User user) {
        return this.reviewRepository.findReviewByItemAndUser(item, user);
    }

    public Set<Review> findAll() {
        List<Review> reviews = this.reviewRepository.findAll();
        return new HashSet<>(reviews);
    }

    public void createOrUpdate(Review... reviews) {
        Iterable<Review> iterable = Arrays.asList(reviews);
        this.reviewRepository.saveAll(iterable);
    }

    public void delete(Review... reviews) {
        Iterable<Review> iterable = Arrays.asList(reviews);
        this.reviewRepository.deleteAll(iterable);
    }


}
