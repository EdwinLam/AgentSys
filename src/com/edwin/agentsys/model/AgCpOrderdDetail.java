package com.edwin.agentsys.model;

/**
 * AgCpOrderdDetail entity. @author MyEclipse Persistence Tools
 */

public class AgCpOrderdDetail implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer orderId;
	private Integer packageId;
	private Integer count;
	private Float price;

	// Constructors

	/** default constructor */
	public AgCpOrderdDetail() {
	}

	/** full constructor */
	public AgCpOrderdDetail(Integer orderId, Integer packageId, Integer count,
			Float price) {
		this.orderId = orderId;
		this.packageId = packageId;
		this.count = count;
		this.price = price;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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

	public Float getPrice() {
		return this.price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

}