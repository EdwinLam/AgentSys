package com.edwin.agentsys.model;

/**
 * AgCpCart entity. @author MyEclipse Persistence Tools
 */

public class AgCpCart implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userId;
	private Integer packageId;
	private Integer count;

	// Constructors

	/** default constructor */
	public AgCpCart() {
	}

	/** full constructor */
	public AgCpCart(Integer userId, Integer packageId, Integer count) {
		this.userId = userId;
		this.packageId = packageId;
		this.count = count;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPackageId() {
		return this.packageId;
	}

	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}