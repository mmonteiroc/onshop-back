package xyz.mmonteiroc.eshop.controller.entity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.mmonteiroc.eshop.entity.Item;
import xyz.mmonteiroc.eshop.entity.Order;
import xyz.mmonteiroc.eshop.exceptions.ItemNotFoundException;
import xyz.mmonteiroc.eshop.manager.ItemManager;
import xyz.mmonteiroc.eshop.manager.OrderManager;
import xyz.mmonteiroc.eshop.manager.TokenManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Code created by: mmonteiroc
 * Email: miguelmonteiroclaveri@gmail.com
 * Github: https://github.com/mmonteiroc
 * LinkedIn: https://www.linkedin.com/in/mmonteiroc/?locale=en_US
 * Date of creation: 21/08/2020
 * Package: xyz.mmonteiroc.eshop.controller.entity
 * Project: eshop
 */
@RestController
public class OrderController {

    @Autowired
    private Gson gson;

    @Autowired
    private ItemManager itemManager;

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private TokenManager tokenManager;


    /*
    * Example of JSON to recive
    [
        {
            idItem:4,
            quantity:3
        },
        {
            idItem:1,
            quantity:20
        }
    ]
    * */
    @PostMapping("/order")
    public ResponseEntity<String> createOrder(@RequestBody String json, HttpServletRequest request) throws ItemNotFoundException {
        JsonArray jsonObject = gson.fromJson(json, JsonArray.class);
        Order order = new Order();

        for (JsonElement itemToBuy : jsonObject) {
            JsonObject obj = itemToBuy.getAsJsonObject();
            synchronized (this) {
                Item item = this.itemManager.findById(obj.get("idItem").getAsLong());
                /*
                 * We dont place this item in the order since we dont have stock
                 * */
                if (item.getStockQuantity() == 0) continue;


                Long quantity = obj.get("quantity").getAsLong();

                if (item.getStockQuantity() - quantity < 0) {
                    order.addItem(item, item.getStockQuantity());
                    item.setStockQuantity(0L);
                } else {
                    order.addItem(item, quantity);
                    item.setStockQuantity(item.getStockQuantity() - quantity);
                }
                this.itemManager.createOrUpdate(item);
                order.addToTotalPrice(item.getPrice());
            }
        }

        String token = (String) request.getAttribute("userToken");
        //User user = this.tokenManager.getUsuarioFromToken(token);

        //order.setUser(user);
        order.setPayed(false);

        this.orderManager.createOrUpdate(order);
        return ResponseEntity.ok("Order placed correctly");
    }

}