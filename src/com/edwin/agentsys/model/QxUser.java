package com.edwin.agentsys.model;

/**
 * QxUser entity. @author MyEclipse Persistence Tools
 */

public class QxUser implements java.io.Serializable {

	// Fields

	private Integer id;
	private String account;
	private String name;

	// Constructors

	/** default constructor */
	public QxUser() {
	}

	/** full constructor */
	public QxUser(String account, String name) {
		this.account = account;
		this.name = name;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}