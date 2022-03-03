package com.stackroute.favouriteservice.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Document
public class Nutrients {
	
	@Id
	private NutrientComponent nutrientComponent;	//Carbs, vitamins, minerals, protein, fat, iron, calcium, calories
	private String description;
	private String perServing;
	private String unit;
	@JsonSerialize(using = ToStringSerializer.class)
	private LocalDateTime publishedAt;
	
	public Nutrients() {
		
	}

	public Nutrients(NutrientComponent nutrientComponent, String description, String perServing, String unit,
			LocalDateTime publishedAt) {
		super();
		this.nutrientComponent = nutrientComponent;
		this.description = description;
		this.perServing = perServing;
		this.unit = unit;
		this.publishedAt = publishedAt;
	}

	public NutrientComponent getNutrientComponent() {
		return nutrientComponent;
	}

	public void setNutrientComponent(NutrientComponent nutrientComponent) {
		this.nutrientComponent = nutrientComponent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPerServing() {
		return perServing;
	}

	public void setPerServing(String perServing) {
		this.perServing = perServing;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public LocalDateTime getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(LocalDateTime publishedAt) {
		this.publishedAt = publishedAt;
	}

	@Override
	public String toString() {
		return "Nutrients [nutrientComponent=" + nutrientComponent + ", description=" + description + ", perServing="
				+ perServing + ", unit=" + unit + ", publishedAt=" + publishedAt + "]";
	}
	
}
