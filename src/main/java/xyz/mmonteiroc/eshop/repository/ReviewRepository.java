package xyz.mmonteiroc.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.mmonteiroc.eshop.entity.Item;
import xyz.mmonteiroc.eshop.entity.Review;
import xyz.mmonteiroc.eshop.entity.User;

/**
 * Code created by: mmonteiroc
 * Email: miguelmonteiroclaveri@gmail.com
 * Github: https://github.com/mmonteiroc
 * LinkedIn: https://www.linkedin.com/in/mmonteiroc/?locale=en_US
 * Date of creation: 29/08/2020
 * Package: xyz.mmonteiroc.eshop.repository
 * Project: eshop
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Review findByIdReview(Long id);

    Review findReviewByItemAndUser(Item item, User user);

}
