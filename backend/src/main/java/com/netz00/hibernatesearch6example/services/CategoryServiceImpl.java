package com.netz00.hibernatesearch6example.services;

import com.netz00.hibernatesearch6example.exceptions.ResourceNotFoundException;
import com.netz00.hibernatesearch6example.model.Category;
import com.netz00.hibernatesearch6example.repository.CategoryRepository;
import com.netz00.hibernatesearch6example.services.api.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category delete(Long id) throws ResourceNotFoundException {

        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id :" + id));
        categoryRepository.delete(category);
        return category;
    }

}
