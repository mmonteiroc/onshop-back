package xyz.mmonteiroc.eshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Code created by: mmonteiroc
 * Email: miguelmonteiroclaveri@gmail.com
 * Github: https://github.com/mmonteiroc
 * LinkedIn: https://www.linkedin.com/in/mmonteiroc/?locale=en_US
 * Date of creation: 29/08/2020
 * Package: xyz.mmonteiroc.eshop.entity
 * Project: eshop
 */
@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idreview")
    private Long idReview;

    @Column(name = "number_stars")
    private Integer stars;

    @ManyToOne
    @JoinColumn(name = "user_iduser")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_iditem")
    @JsonBackReference
    private Item item;

    @Transient
    private Long idItem;

    public Review() {
    }

    public Long getIdReview() {
        return idReview;
    }

    public void setIdReview(Long idReview) {
        this.idReview = idReview;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Long getIdItem() {
        return idItem;
    }

    public void setIdItem(Long idItem) {
        this.idItem = idItem;
    }

    @Override
    public String toString() {
        return "Review{" +
                "idReview=" + idReview +
                ", stars=" + stars +
                ", idItem=" + idItem +
                '}';
    }
}
