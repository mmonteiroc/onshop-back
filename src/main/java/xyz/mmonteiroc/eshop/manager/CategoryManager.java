package xyz.mmonteiroc.eshop.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.mmonteiroc.eshop.entity.Category;
import xyz.mmonteiroc.eshop.exceptions.CategoryNotFoundException;
import xyz.mmonteiroc.eshop.repository.CategoryRepository;

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
public class CategoryManager {

    @Autowired
    private CategoryRepository categoryRepository;


    public Set<Category> findAll() {
        List<Category> categories = this.categoryRepository.findAll();
        return new HashSet<>(categories);
    }

    public Category findById(Long id) throws CategoryNotFoundException {
        Category category = this.categoryRepository.findByIdCategory(id);
        if (category == null) throw new CategoryNotFoundException("Category with id {" + id + "} was not found");
        return category;
    }

    public void createOrUpdate(Category... categories) {
        Iterable<Category> iterable = Arrays.asList(categories);
        this.categoryRepository.saveAll(iterable);
    }

    public void delete(Category... categories) {
        Iterable<Category> iterable = Arrays.asList(categories);
        this.categoryRepository.deleteAll(iterable);
    }
}
