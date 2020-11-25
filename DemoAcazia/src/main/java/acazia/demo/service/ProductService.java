package acazia.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import acazia.demo.entity.Product;
import acazia.demo.modl.ProductModel;

public interface ProductService {
	
	Product insert(Product model);

	void update(Product product);

	boolean existById(Long id);

	Page<ProductModel> pageProduct(String name, String categoryTag, Long id, Pageable pageable);

	void delete(Long id);
	
	int countByCategoryId(Long categoryId);

}
