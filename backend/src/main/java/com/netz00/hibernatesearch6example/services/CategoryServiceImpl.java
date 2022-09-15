package com.netz00.hibernatesearch6example.services;

import com.netz00.hibernatesearch6example.dto.CategoryDTO;
import com.netz00.hibernatesearch6example.exceptions.ResourceNotFoundException;
import com.netz00.hibernatesearch6example.model.Category;
import com.netz00.hibernatesearch6example.model.mapper.CategoryMapper;
import com.netz00.hibernatesearch6example.repository.CategoryRepository;
import com.netz00.hibernatesearch6example.services.api.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        return categoryMapper.toDto(categoryRepository.findAll());
    }

    @Override
    public CategoryDTO save(CategoryDTO category) {
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(category)));
    }

    @Override
    public CategoryDTO delete(Long id) throws ResourceNotFoundException {

        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id :" + id));
        categoryRepository.delete(category);
        return categoryMapper.toDto(category);
    }

}
