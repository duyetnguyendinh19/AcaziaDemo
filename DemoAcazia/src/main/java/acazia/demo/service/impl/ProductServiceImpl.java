package acazia.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import acazia.demo.entity.Product;
import acazia.demo.modl.ProductModel;
import acazia.demo.repo.ProductRepo;
import acazia.demo.service.ProductService;

@Service(value = "productService")
public class ProductServiceImpl implements ProductService {
	
    @Autowired
    ProductRepo productRepo;

	@Override
	public Product insert(Product model) {
		return productRepo.saveAndFlush(model);
	}

	@Override
	public void update(Product product) {
		productRepo.save(product);
	}

	@Override
	public boolean existById(Long id) {
		return productRepo.existsById(id);
	}

	@Override
	public Page<ProductModel> pageProduct(String name, String inputSearch, Long id, Pageable pageable) {
		return productRepo.pageProduct(name, inputSearch, id ,pageable);
	}

	@Override
	public void delete(Long id) {
		productRepo.deleteById(id);
	}

	@Override
	public int countByCategoryId(Long categoryId) {
		return productRepo.countByCategoryId(categoryId);
	}
   
}
