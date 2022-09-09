package com.netz00.hibernatesearch6example.repository;

import com.netz00.hibernatesearch6example.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
