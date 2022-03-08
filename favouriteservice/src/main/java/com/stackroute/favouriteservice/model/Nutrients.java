package com.stackroute.favouriteservice.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Document
public class Nutrients {
	
	@Id
	private String totalProtein;
	private String totalCarbohydrates;
	private String totalFat;
	private String totalCalories;
	private String perServing;
	@JsonSerialize(using = ToStringSerializer.class)
	private LocalDateTime publishedAt;
	
	public Nutrients() {
		this.publishedAt = LocalDateTime.now();
	}

	public Nutrients(String totalProtein, String totalCarbohydrates, String totalFat, String totalCalories, 
			String perServing, LocalDateTime publishedAt) {
		super();
		this.totalProtein = totalProtein;
		this.totalCarbohydrates = totalCarbohydrates;
		this.totalFat = totalFat;
		this.totalCalories = totalCalories;
		this.perServing = perServing;
	}

	public String getTotalProtein() {
		return totalProtein;
	}

	public void setTotalProtein(String totalProtein) {
		this.totalProtein = totalProtein;
	}

	public String getTotalCarbohydrates() {
		return totalCarbohydrates;
	}

	public void setTotalCarbohydrates(String totalCarbohydrates) {
		this.totalCarbohydrates = totalCarbohydrates;
	}

	public String getTotalFat() {
		return totalFat;
	}

	public void setTotalFat(String totalFat) {
		this.totalFat = totalFat;
	}

	public String getTotalCalories() {
		return totalCalories;
	}

	public void setTotalCalories(String totalCalories) {
		this.totalCalories = totalCalories;
	}

	public String getPerServing() {
		return perServing;
	}

	public void setPerServing(String perServing) {
		this.perServing = perServing;
	}

	public LocalDateTime getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt() {
		this.publishedAt = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "Nutrients [totalProtein=" + totalProtein + ", totalCarbohydrates=" + totalCarbohydrates + ", totalFat="
				+ totalFat + ", totalCalories=" + totalCalories + ", perServing="
				+ perServing + ", publishedAt=" + publishedAt + "]";
	}

}
