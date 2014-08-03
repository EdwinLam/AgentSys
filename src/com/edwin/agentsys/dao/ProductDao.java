package com.edwin.agentsys.dao;

import java.util.List;

import com.edwin.agentsys.model.Product;

public interface ProductDao {

	public  void insertProduct(Product product);

	public  void deleteById(long id);

	public  List<Product> findAll();

	public  void Update(Product product);
	
	public Product findById(long id);
	
	public List<Product> indexFind(int offset,int pageSize,int typeId,String name);
	
	public int indexFindTotal(int typeId,String name);

}