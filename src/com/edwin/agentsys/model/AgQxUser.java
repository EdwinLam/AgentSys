package com.edwin.agentsys.model;

/**
 * AgQxUser entity. @author MyEclipse Persistence Tools
 */

public class AgQxUser implements java.io.Serializable {

	// Fields

	private Integer id;
	private String account;
	private String name;
	private String psw;
	private Integer roleId;

	// Constructors

	/** default constructor */
	public AgQxUser() {
	}

	/** full constructor */
	public AgQxUser(String account, String name, String psw, Integer roleId) {
		this.account = account;
		this.name = name;
		this.psw = psw;
		this.roleId = roleId;
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

	public String getPsw() {
		return this.psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}