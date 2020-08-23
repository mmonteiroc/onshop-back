package xyz.mmonteiroc.eshop.controller.entity;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.mmonteiroc.eshop.entity.Category;
import xyz.mmonteiroc.eshop.exceptions.CategoryNotFoundException;
import xyz.mmonteiroc.eshop.exceptions.MandatoryParamsNotRecivedException;
import xyz.mmonteiroc.eshop.exceptions.ParamsNotPermitedException;
import xyz.mmonteiroc.eshop.manager.CategoryManager;

import java.util.Set;

/**
 * Code created by: mmonteiroc
 * Email: miguelmonteiroclaveri@gmail.com
 * Github: https://github.com/mmonteiroc
 * LinkedIn: https://www.linkedin.com/in/mmonteiroc/?locale=en_US
 * Date of creation: 22/08/2020
 * Package: xyz.mmonteiroc.eshop.controller.entity
 * Project: eshop
 */
@RestController
public class CategoryController {

    @Autowired
    private CategoryManager categoryManager;

    @Autowired
    private Gson gson;

    @GetMapping("/categories")
    public ResponseEntity<Set<Category>> findAllCategories() {
        return ResponseEntity.ok(this.categoryManager.findAll());
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> findCategoryById(@PathVariable(name = "id") Long id) throws CategoryNotFoundException {
        return ResponseEntity.ok(this.categoryManager.findById(id));
    }

    @PostMapping("/categories")
    public ResponseEntity<String> createCategory(@RequestBody String json) throws MandatoryParamsNotRecivedException, ParamsNotPermitedException, CategoryNotFoundException {
        Category category = gson.fromJson(json, Category.class);
        if (category.getIdCategory() != null) throw new ParamsNotPermitedException();
        if (category.getName() == null) throw new MandatoryParamsNotRecivedException("Param name not recived");
        if (category.getIdParentCategory() != null) {
            Category parent = this.categoryManager.findById(category.getIdParentCategory());
            category.setParentCategory(parent);
        }
        this.categoryManager.createOrUpdate(category);
        return ResponseEntity.ok("Category created successfully");
    }

    @PutMapping("/categories")
    public ResponseEntity<String> updateCategory(@RequestBody String json) throws MandatoryParamsNotRecivedException, ParamsNotPermitedException, CategoryNotFoundException {
        Category category = gson.fromJson(json, Category.class);
        if (category.getIdCategory() == null)
            throw new MandatoryParamsNotRecivedException("Id of category not recived");
        if (category.getIdParentCategory() != null) {
            Category parent = this.categoryManager.findById(category.getIdParentCategory());
            category.setParentCategory(parent);
        }
        this.categoryManager.createOrUpdate(category);
        return ResponseEntity.ok("Category created successfully");
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable(name = "id") Long id) throws CategoryNotFoundException {
        Category category = this.categoryManager.findById(id);
        this.categoryManager.delete(category);
        return ResponseEntity.ok("Category removed successfully");
    }
}
