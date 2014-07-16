package com.edwin.agentsys.model;

/**
 * AgCpProduct entity. @author MyEclipse Persistence Tools
 */

public class AgCpProduct implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String imgUrl;
	private String introduce;
	private Integer defaultPackageId;
	private Integer typeId;

	// Constructors

	/** default constructor */
	public AgCpProduct() {
	}

	/** full constructor */
	public AgCpProduct(String name, String imgUrl, String introduce,
			Integer defaultPackageId, Integer typeId) {
		this.name = name;
		this.imgUrl = imgUrl;
		this.introduce = introduce;
		this.defaultPackageId = defaultPackageId;
		this.typeId = typeId;
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

	public String getImgUrl() {
		return this.imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public Integer getDefaultPackageId() {
		return this.defaultPackageId;
	}

	public void setDefaultPackageId(Integer defaultPackageId) {
		this.defaultPackageId = defaultPackageId;
	}

	public Integer getTypeId() {
		return this.typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

}