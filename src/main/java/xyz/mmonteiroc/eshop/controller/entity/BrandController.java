package xyz.mmonteiroc.eshop.controller.entity;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.mmonteiroc.eshop.entity.Brand;
import xyz.mmonteiroc.eshop.entity.Item;
import xyz.mmonteiroc.eshop.exceptions.BrandNotFoundException;
import xyz.mmonteiroc.eshop.exceptions.MandatoryParamsNotRecivedException;
import xyz.mmonteiroc.eshop.exceptions.ParamsNotPermitedException;
import xyz.mmonteiroc.eshop.manager.BrandManager;
import xyz.mmonteiroc.eshop.manager.ItemManager;

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
public class BrandController {

    @Autowired
    private BrandManager brandManager;

    @Autowired
    private ItemManager itemManager;

    @Autowired
    private Gson gson;

    @GetMapping("/brands")
    public ResponseEntity<Set<Brand>> findAllBrands() {
        return ResponseEntity.ok(this.brandManager.findAll());
    }

    @GetMapping("/brands/{id}")
    public ResponseEntity<Brand> findById(@PathVariable(name = "id") Long id) throws BrandNotFoundException {
        return ResponseEntity.ok(this.brandManager.findById(id));
    }

    @GetMapping("/brands/{id}/items")
    public ResponseEntity<Set<Item>> findItemsByBrand(@PathVariable(name = "id") Long id) throws BrandNotFoundException {
        Brand brand = this.brandManager.findById(id);
        return ResponseEntity.ok(this.itemManager.findByBrand(brand));
    }

    @PostMapping("/brands")
    public ResponseEntity<String> addBrand(@RequestBody String json) throws MandatoryParamsNotRecivedException, ParamsNotPermitedException {
        Brand brand = gson.fromJson(json, Brand.class);
        if (brand.getIdBrand() != null) throw new ParamsNotPermitedException();
        if (brand.getName() == null) throw new MandatoryParamsNotRecivedException("Param name not recived");
        this.brandManager.createOrUpdate(brand);
        return ResponseEntity.ok("Brand created correctly");
    }

    @PutMapping("/brands")
    public ResponseEntity<String> updateBrand(@RequestBody String json) throws MandatoryParamsNotRecivedException {
        Brand brand = gson.fromJson(json, Brand.class);
        if (brand.getIdBrand() == null || brand.getName() == null)
            throw new MandatoryParamsNotRecivedException("Param id not recived");

        this.brandManager.createOrUpdate(brand);
        return ResponseEntity.ok("Brand updated correctly");
    }

    @DeleteMapping("/brands/{id}")
    public ResponseEntity<String> deleteBrand(@PathVariable(name = "id") Long id) throws BrandNotFoundException {
        Brand brand = this.brandManager.findById(id);
        this.brandManager.delete(brand);
        return ResponseEntity.ok("Brand deleted correctly");
    }
}
