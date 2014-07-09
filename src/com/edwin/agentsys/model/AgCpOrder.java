package com.edwin.agentsys.model;

import java.util.Date;

/**
 * AgCpOrder entity. @author MyEclipse Persistence Tools
 */

public class AgCpOrder implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userId;
	private Date createTime;
	private Float totalPrice;
	private Integer status;
	private String address;
	private String phone;

	// Constructors

	/** default constructor */
	public AgCpOrder() {
	}

	/** full constructor */
	public AgCpOrder(Integer userId, Date createTime, Float totalPrice,
			Integer status, String address, String phone) {
		this.userId = userId;
		this.createTime = createTime;
		this.totalPrice = totalPrice;
		this.status = status;
		this.address = address;
		this.phone = phone;
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

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Float getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}