package acazia.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import acazia.demo.entity.Category;
import acazia.demo.repo.CategoryRepo;
import acazia.demo.service.CategorySerivce;

@Service("categoryService")
public class CategoryServiceImpl implements CategorySerivce {

	@Autowired
	CategoryRepo categoryRepo;

	@Override
	public Category insert(Category cate) {
		return categoryRepo.saveAndFlush(cate);
	}

	@Override
	public void update(Category cate) {
		categoryRepo.save(cate);
	}

	@Override
	public Category findByTag(String tag) {
		return categoryRepo.findByTag(tag);
	}

	@Override
	public Category findById(Long id) {
		return categoryRepo.findById(id).get();
	}

	@Override
	public boolean existById(Long id) {
		return categoryRepo.existsById(id);
	}

	@Override
	public Page<Category> pageCategory(String name, String tag, Long id, Pageable pageable) {
		return categoryRepo.pageCategory(name, tag, id,  pageable);
	}

	@Override
	public void delete(Long id) {
		categoryRepo.deleteById(id);
	}
	
	
	
}
