package xyz.mmonteiroc.eshop.controller.entity;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.mmonteiroc.eshop.entity.Item;
import xyz.mmonteiroc.eshop.exceptions.ItemNotFoundException;
import xyz.mmonteiroc.eshop.exceptions.MandatoryParamsNotRecivedException;
import xyz.mmonteiroc.eshop.manager.ItemManager;

import java.util.Set;

/**
 * Code created by: mmonteiroc
 * Email: miguelmonteiroclaveri@gmail.com
 * Github: https://github.com/mmonteiroc
 * LinkedIn: https://www.linkedin.com/in/mmonteiroc/?locale=en_US
 * Date of creation: 20/08/2020
 * Package: xyz.mmonteiroc.eshop.controller.entity
 * Project: eshop
 */
@RestController
public class ItemController {

    @Autowired
    private ItemManager itemManager;

    @Autowired
    private Gson gson;

    @GetMapping("/items")
    public ResponseEntity<Set<Item>> getAllItems() {
        return ResponseEntity.ok(this.itemManager.findAll());
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<Item> getThisItem(@PathVariable(name = "id") Long id) throws ItemNotFoundException {
        return ResponseEntity.ok(this.itemManager.findById(id));
    }

    @GetMapping("/items/search/{query}")
    public ResponseEntity<Set<Item>> getItemsQuery(@PathVariable(name = "query") String query) {
        return ResponseEntity.ok(this.itemManager.findAllMatches(query));
    }

    @PostMapping("/items")
    public ResponseEntity<String> createItem(@RequestBody String json) throws MandatoryParamsNotRecivedException {
        Item item = gson.fromJson(json, Item.class);
        /*
         * Check mandatory params are recived
         * */
        if (item.getName() == null
                || item.getDescription() == null
                || item.getPrice() == null) {
            throw new MandatoryParamsNotRecivedException("Params from ITEM mandatory weren't recived");
        }
        if (item.getIdItem() != null)
            return ResponseEntity.badRequest().body("Cannot update in POST, so remove the idItem");

        this.itemManager.createOrUpdate(item);
        return ResponseEntity.ok("Item saved correctly");
    }

    @PutMapping("/items")
    public ResponseEntity<String> updateItem(@RequestBody String json) throws MandatoryParamsNotRecivedException {
        Item item = gson.fromJson(json, Item.class);
        /*
         * Check mandatory params are recived
         * */
        if (item.getIdItem() == null)
            throw new MandatoryParamsNotRecivedException("Params from ITEM mandatory weren't recived");
        this.itemManager.createOrUpdate(item);

        return ResponseEntity.ok("Item updated correctly");
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable(name = "id") Long id) throws ItemNotFoundException {
        Item item = this.itemManager.findById(id);
        this.itemManager.delete(item);
        return ResponseEntity.ok("Item deleted correctly");
    }
}
