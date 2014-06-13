/** 
 * 
 * @author geloin 
 * @date 2012-5-5 上午10:29:22 
 */
package com.geloin.spring.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.geloin.spring.entity.Menu;
import com.geloin.spring.mapper.MenuMapper;
import com.geloin.spring.service.MenuService;

/**
 * 
 * @author geloin
 * @date 2012-5-5 上午10:29:22
 */
@Repository(value = "menuService")
@Transactional
public class MenuServiceImpl implements MenuService {

	@Resource(name = "menuMapper")
	private MenuMapper menuMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.geloin.spring.service.MenuService#find()
	 */
	@Override
	public List<Menu> find() {
		String sql = "select * from tb_system_menu";
		return this.menuMapper.operateReturnBeans(sql);
	}

}