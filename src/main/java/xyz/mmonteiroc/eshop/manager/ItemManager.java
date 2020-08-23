package xyz.mmonteiroc.eshop.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.mmonteiroc.eshop.entity.Brand;
import xyz.mmonteiroc.eshop.entity.Item;
import xyz.mmonteiroc.eshop.exceptions.ItemNotFoundException;
import xyz.mmonteiroc.eshop.repository.ItemRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Code created by: mmonteiroc
 * Email: miguelmonteiroclaveri@gmail.com
 * Github: https://github.com/mmonteiroc
 * LinkedIn: https://www.linkedin.com/in/mmonteiroc/?locale=en_US
 * Date of creation: 20/08/2020
 * Package: xyz.mmonteiroc.eshop.manager
 * Project: eshop
 */
@Service
public class ItemManager {

    @Autowired
    private ItemRepository itemRepository;

    public Item findById(Long id) throws ItemNotFoundException {
        Item item = this.itemRepository.findByIdItem(id);
        if (item == null) throw new ItemNotFoundException("Item with id {" + id + "} was not found");
        return item;
    }

    public Set<Item> findAll() {
        List<Item> items = this.itemRepository.findAll();
        return new HashSet<>(items);
    }

    public Set<Item> findByBrand(Brand brand) {
        return this.itemRepository.findByBrand(brand);
    }

    public Set<Item> findAllMatches(String search) {
        return this.itemRepository.findByNameContainsOrDescriptionContains(search, search);
    }

    public void createOrUpdate(Item... items) {
        Iterable<Item> iterable = Arrays.asList(items);
        this.itemRepository.saveAll(iterable);
    }

    public void delete(Item... items) {
        Iterable<Item> iterable = Arrays.asList(items);
        this.itemRepository.deleteAll(iterable);
    }


}
