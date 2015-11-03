package com.epam.training.auction_backend.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "AUCTIONS")
public final class Auction implements Serializable {
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    private User sellerUser;
    
    private User winnerUser;

    @Column(name = "FINAL_PRICE")
    private double finalPrice;

    @Column(name = "IS_ACTIVE")
    private boolean isActive;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    public Auction() {
    }

    public Auction(String title, String description, User sellerUser) {
        this.title = title;
        this.description = description;
        this.sellerUser = sellerUser;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne(cascade = CascadeType.ALL)
    public User getSellerUser() {
        return sellerUser;
    }

    public void setSellerUser(User sellerUser) {
        this.sellerUser = sellerUser;
    }

    @ManyToOne(cascade = CascadeType.ALL)
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Auction [id=" + id + ", title=" + title + ", description=" + description + ", sellerUser=" + sellerUser
                + ", finalPrice=" + finalPrice + ", isActive=" + isActive
                + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
    }
}
