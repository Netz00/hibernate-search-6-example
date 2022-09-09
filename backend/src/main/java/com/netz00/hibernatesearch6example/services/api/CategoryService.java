package com.netz00.hibernatesearch6example.services.api;

import com.netz00.hibernatesearch6example.exceptions.ResourceNotFoundException;
import com.netz00.hibernatesearch6example.model.Category;

import java.util.List;

public interface CategoryService {


    List<Category> findAll();


    Category save(Category category);

    Category delete(Long id) throws ResourceNotFoundException;
}
