package xyz.mmonteiroc.eshop.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.mmonteiroc.eshop.entity.Brand;
import xyz.mmonteiroc.eshop.exceptions.BrandNotFoundException;
import xyz.mmonteiroc.eshop.repository.BrandRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Code created by: mmonteiroc
 * Email: miguelmonteiroclaveri@gmail.com
 * Github: https://github.com/mmonteiroc
 * LinkedIn: https://www.linkedin.com/in/mmonteiroc/?locale=en_US
 * Date of creation: 22/08/2020
 * Package: xyz.mmonteiroc.eshop.manager
 * Project: eshop
 */
@Service
public class BrandManager {

    @Autowired
    private BrandRepository brandRepository;

    public Set<Brand> findAll() {
        List<Brand> brands = this.brandRepository.findAll();
        return new HashSet<>(brands);
    }

    public Brand findById(Long id) throws BrandNotFoundException {
        Brand brand = this.brandRepository.findByIdBrand(id);
        if (brand == null) throw new BrandNotFoundException("Brand by id {" + id + "} was not found");
        return brand;
    }

    public void createOrUpdate(Brand... brands) {
        Iterable<Brand> iterable = Arrays.asList(brands);
        this.brandRepository.saveAll(iterable);
    }

    public void delete(Brand... brands) {
        Iterable<Brand> iterable = Arrays.asList(brands);
        this.brandRepository.deleteAll(iterable);
    }

}
