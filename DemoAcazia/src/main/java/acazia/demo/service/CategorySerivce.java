package acazia.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import acazia.demo.entity.Category;

public interface CategorySerivce {

	public Category insert(Category cate);
	
	public void update(Category cate);
	
	public Category findByTag(String tag);
	
	public Category findById(Long id);
	
	public boolean existById(Long id);

	public Page<Category> pageCategory(String name, String tag, Long id, Pageable pageable);
	
	public void delete(Long id);
}
