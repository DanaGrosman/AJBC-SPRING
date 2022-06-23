package ajcb.spring.dao;

import ajbc.spring.models.Product;

public interface ProductDao {

	// CRUD operations
	public void addProduct(Product product) throws DaoExeption;
	
	// Queries
	
	public long count(); // what is the number of products in the db?
}
