/** 
 * 
 * @author geloin 
 * @date 2012-5-5 上午10:28:42 
 */
package com.geloin.spring.service;

import java.util.List;

import com.geloin.spring.entity.Menu;

/**
 * 
 * @author geloin
 * @date 2012-5-5 上午10:28:42
 */
public interface MenuService {
	/**
	 * 查询所有
	 * 
	 * @author geloin
	 * @date 2012-5-5 上午10:28:55
	 * @return
	 */
	List<Menu> find();
}