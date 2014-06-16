package com.edwin.agentsys.model;

/**
 * AgCpPackage entity. @author MyEclipse Persistence Tools
 */

public class AgCpPackage implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;

	// Constructors

	/** default constructor */
	public AgCpPackage() {
	}

	/** full constructor */
	public AgCpPackage(String name) {
		this.name = name;
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

}