package com.edwin.agentsys.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edwin.agentsys.dao.ProductDao;
import com.edwin.agentsys.model.Product;


/*
 * Service层，在此可以进行事物管理
 */
@Component("productService")
public class ProductService {
	@Resource(name="productDao")
	private ProductDao dao;
	@Transactional
	public boolean insertProduct(Product product){
		dao.insertProduct(product);
		return true;
	}
	@Transactional
	public boolean deleteById(long id){
		dao.deleteById(id);
		return true;
	}
	@Transactional
	public boolean  updateProduct(Product product){
		dao.Update(product);
		return true;
	}
	@Transactional
	public List<Product> findAll(){
		return dao.findAll();
	}

	@Transactional
	public Product findById(long id){
		return dao.findById(id);
	}
	
	@Transactional
	public  List<Product> indexFind(int offset,int pageSize,int typeId,String name){
		return dao.indexFind(offset, pageSize, typeId,name);
	}
	
	@Transactional
	public int indexFindTotal(int typeId,String name){
		return dao.indexFindTotal(typeId, name);
	}
	}
