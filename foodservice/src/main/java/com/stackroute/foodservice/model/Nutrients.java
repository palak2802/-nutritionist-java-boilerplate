package com.stackroute.foodservice.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Document
public class Nutrients {
	
	@Id
	private List<NutrientComponent> nutrientComponents;	//Carbs, vitamins, minerals, protein, fat, iron, calcium, calories
	private String description;
	private String perServing;
	private String unit;
	@JsonSerialize(using = ToStringSerializer.class)
	private LocalDateTime publishedAt;
	
	public Nutrients() {
		this.publishedAt = LocalDateTime.now();
	}

	public Nutrients(List<NutrientComponent> nutrientComponents, String description, String perServing, String unit,
			LocalDateTime publishedAt) {
		super();
		this.nutrientComponents = nutrientComponents;
		this.description = description;
		this.perServing = perServing;
		this.unit = unit;
		this.publishedAt = publishedAt;
	}

	public List<NutrientComponent> getNutrientComponentList() {
		return nutrientComponents;
	}

	public void setNutrientComponentList(List<NutrientComponent> nutrientComponents) {
		this.nutrientComponents = nutrientComponents;
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

	public void setPublishedAt() {
		this.publishedAt = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "Nutrients [nutrientComponents=" + nutrientComponents + ", description=" + description + ", perServing="
				+ perServing + ", unit=" + unit + ", publishedAt=" + publishedAt + "]";
	}
	
}
