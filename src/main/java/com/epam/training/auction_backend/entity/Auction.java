package com.epam.training.auction_backend.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AUCTIONS")
public class Auction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "SELLER_USER_ID")
	private long sellerUserId;
	
	@Column(name = "WINNER_USER_ID")
	private long winnerUserId;
	
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
	
	public Auction(String title, String description, long sellerUserId) {
		this.title = title;
		this.description = description;
		this.sellerUserId = sellerUserId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public long getSellerUserId() {
		return sellerUserId;
	}

	public void setSellerUserId(long sellerUserId) {
		this.sellerUserId = sellerUserId;
	}

	public long getWinnerUserId() {
		return winnerUserId;
	}

	public void setWinnerUserId(long winnerUserId) {
		this.winnerUserId = winnerUserId;
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
		return "Auction [id=" + id + ", title=" + title + ", description=" + description + ", sellerUserId="
				+ sellerUserId + ", winnerUserId=" + winnerUserId + ", finalPrice=" + finalPrice + ", isActive="
				+ isActive + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
}
