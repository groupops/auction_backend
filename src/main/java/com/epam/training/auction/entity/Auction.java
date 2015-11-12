package com.epam.training.auction.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "AUCTIONS")
public final class Auction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne(cascade = CascadeType.MERGE, fetch=FetchType.LAZY)
    @JoinColumn(name="USER_ID")
    private User sellerUser;

    @ManyToOne(cascade = CascadeType.MERGE, fetch=FetchType.LAZY)
    private User winnerUser;

    @Column(name = "FINAL_PRICE")
    private Double finalPrice;

    @Column(name = "IS_ACTIVE")
    private Boolean active;

    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "UPDATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Auction() {
    }

    public Auction(String title, String description, User sellerUser) {
        this.title = title;
        this.description = description;
        this.sellerUser = sellerUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getSellerUser() {
        return sellerUser;
    }

    public void setSellerUser(User sellerUser) {
        this.sellerUser = sellerUser;
    }

    public User getWinnerUser() {
        return winnerUser;
    }

    public void setWinnerUser(User winnerUser) {
        this.winnerUser = winnerUser;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Auction [id=" + id + ", title=" + title + ", description=" + description + ", sellerUser=" + sellerUser
                + ", finalPrice=" + finalPrice + ", active=" + active
                + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
    }
}
