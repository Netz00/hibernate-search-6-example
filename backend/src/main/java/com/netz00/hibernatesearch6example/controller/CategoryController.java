package com.netz00.hibernatesearch6example.controller;


import com.netz00.hibernatesearch6example.controller.endpoints.RestEndpoints;
import com.netz00.hibernatesearch6example.controller.endpoints.RestEndpointsParameters;
import com.netz00.hibernatesearch6example.dto.CategoryDTO;
import com.netz00.hibernatesearch6example.services.api.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(RestEndpoints.API_CATEGORY)
@Validated
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;

    }


    @GetMapping()
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(categoryService.save(categoryDTO), HttpStatus.CREATED);
    }


    @DeleteMapping(RestEndpointsParameters.CATEGORY_ID)
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable("categoryId") final Long id) {
        return new ResponseEntity<>(categoryService.delete(id), HttpStatus.OK);
    }

}
