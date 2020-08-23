package xyz.mmonteiroc.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.mmonteiroc.eshop.entity.Brand;
import xyz.mmonteiroc.eshop.entity.Item;

import java.util.Set;

/**
 * Code created by: mmonteiroc
 * Email: miguelmonteiroclaveri@gmail.com
 * Github: https://github.com/mmonteiroc
 * LinkedIn: https://www.linkedin.com/in/mmonteiroc/?locale=en_US
 * Date of creation: 20/08/2020
 * Package: xyz.mmonteiroc.eshop.repository
 * Project: eshop
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByIdItem(Long id);

    Set<Item> findByNameContainsOrDescriptionContains(String query, String query1);

    Set<Item> findByBrand(Brand brand);
}
