package xyz.mmonteiroc.eshop.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.mmonteiroc.eshop.entity.Order;
import xyz.mmonteiroc.eshop.repository.OrderRepository;

import java.util.Arrays;

/**
 * Code created by: mmonteiroc
 * Email: miguelmonteiroclaveri@gmail.com
 * Github: https://github.com/mmonteiroc
 * LinkedIn: https://www.linkedin.com/in/mmonteiroc/?locale=en_US
 * Date of creation: 21/08/2020
 * Package: xyz.mmonteiroc.eshop.manager
 * Project: eshop
 */
@Service
public class OrderManager {

    @Autowired
    private OrderRepository orderRepository;

    public void createOrUpdate(Order... order) {
        Iterable<Order> iterable = Arrays.asList(order);
        this.orderRepository.saveAll(iterable);
    }

}
