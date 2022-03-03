package com.stackroute.favouriteservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class NutrientComponent {
	
	@Id
	private String name;
	private String value;
	private String unit;
	
	public NutrientComponent() {
		
	}
	
	public NutrientComponent(String name, String value, String unit) {
		super();
		this.name = name;
		this.value = value;
		this.unit = unit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public String toString() {
		return "NutrientComponent [name=" + name + ", value=" + value + ", unit=" + unit + "]";
	}
	
}
