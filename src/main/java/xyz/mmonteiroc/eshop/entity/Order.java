package xyz.mmonteiroc.eshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Code created by: mmonteiroc
 * Email: miguelmonteiroclaveri@gmail.com
 * Github: https://github.com/mmonteiroc
 * LinkedIn: https://www.linkedin.com/in/mmonteiroc/?locale=en_US
 * Date of creation: 21/08/2020
 * Package: xyz.mmonteiroc.eshop.entity
 * Project: eshop
 */
@Entity
@Table(name = "buy")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idorder")
    private Long idOrder;

    @Column(name = "datetime")
    private LocalDateTime dateTime;

    @Column(name = "totalprice")
    private double totalprice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<OrderItem> orderItems = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_iduser")
    @JsonBackReference
    private User user;

    @Column(name = "payed", columnDefinition = "bit")
    private boolean payed;

    public Order() {
    }

    public void addItem(Item item, Long quantity) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(this);
        orderItem.setItem(item);
        orderItem.setQuantity(quantity);
        orderItem.setPrice(item.getPrice());
        this.orderItems.add(orderItem);
    }

    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idBuy) {
        this.idOrder = idBuy;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {

        this.totalprice = totalprice;
    }

    public void addToTotalPrice(double add) {
        this.totalprice += add;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order buy = (Order) o;
        return Double.compare(buy.totalprice, totalprice) == 0 &&
                Objects.equals(idOrder, buy.idOrder) &&
                Objects.equals(dateTime, buy.dateTime);
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrder, dateTime, totalprice);
    }

    @Override
    public String toString() {
        return "Buy{" +
                "idOrder=" + idOrder +
                ", dateTime=" + dateTime +
                ", totalprice=" + totalprice +
                ", user id=" + user.getIdUser() +
                '}';
    }
}

