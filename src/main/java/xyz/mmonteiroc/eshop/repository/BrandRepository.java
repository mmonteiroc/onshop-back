package xyz.mmonteiroc.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.mmonteiroc.eshop.entity.Brand;

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
public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand findByIdBrand(Long id);

}
