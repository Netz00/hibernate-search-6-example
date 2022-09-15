package com.netz00.hibernatesearch6example.services.api;

import com.netz00.hibernatesearch6example.dto.CategoryDTO;
import com.netz00.hibernatesearch6example.exceptions.ResourceNotFoundException;

import java.util.List;

public interface CategoryService {


    List<CategoryDTO> findAll();


    CategoryDTO save(CategoryDTO category);

    CategoryDTO delete(Long id) throws ResourceNotFoundException;
}
