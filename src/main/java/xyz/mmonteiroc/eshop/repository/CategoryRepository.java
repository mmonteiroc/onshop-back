package xyz.mmonteiroc.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.mmonteiroc.eshop.entity.Category;

/**
 * Code created by: mmonteiroc
 * Email: miguelmonteiroclaveri@gmail.com
 * Github: https://github.com/mmonteiroc
 * LinkedIn: https://www.linkedin.com/in/mmonteiroc/?locale=en_US
 * Date of creation: 22/08/2020
 * Package: xyz.mmonteiroc.eshop.repository
 * Project: eshop
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByIdCategory(Long id);

}
