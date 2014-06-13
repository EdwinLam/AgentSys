/** 
 * 
 * @author geloin 
 * @date 2012-5-5 上午10:24:43 
 */
package com.geloin.spring.entity;

/**
 * 
 * @author geloin
 * @date 2012-5-5 上午10:24:43
 */
public class Menu {
	/**
	 * 惟一标识
	 */
	private Integer id;
	/**
	 * 父ID
	 */
	private Integer parentId;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 对应的地址
	 */
	private String url;
	/**
	 * 是否显示在左侧
	 */
	private Integer isShowLeft;

	/**
	 * 
	 * @author geloin
	 * @date 2012-5-5 上午10:26:19
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 
	 * @author geloin
	 * @date 2012-5-5 上午10:26:19
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 
	 * @author geloin
	 * @date 2012-5-5 上午10:26:19
	 * @return the parentId
	 */
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * 
	 * @author geloin
	 * @date 2012-5-5 上午10:26:19
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	/**
	 * 
	 * @author geloin
	 * @date 2012-5-5 上午10:26:19
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @author geloin
	 * @date 2012-5-5 上午10:26:19
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @author geloin
	 * @date 2012-5-5 上午10:26:19
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 
	 * @author geloin
	 * @date 2012-5-5 上午10:26:19
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 
	 * @author geloin
	 * @date 2012-5-5 上午10:26:19
	 * @return the isShowLeft
	 */
	public Integer getIsShowLeft() {
		return isShowLeft;
	}

	/**
	 * 
	 * @author geloin
	 * @date 2012-5-5 上午10:26:19
	 * @param isShowLeft
	 *            the isShowLeft to set
	 */
	public void setIsShowLeft(Integer isShowLeft) {
		this.isShowLeft = isShowLeft;
	}

}