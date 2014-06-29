package com.edwin.agentsys.model;

/**
 * AgCpPackage entity. @author MyEclipse Persistence Tools
 */

public class AgCpPackage implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Float price;
	private Integer productId;

	// Constructors

	/** default constructor */
	public AgCpPackage() {
	}

	/** minimal constructor */
	public AgCpPackage(String name) {
		this.name = name;
	}

	/** full constructor */
	public AgCpPackage(String name, Float price, Integer productId) {
		this.name = name;
		this.price = price;
		this.productId = productId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return this.price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getProductId() {
		return this.productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

}