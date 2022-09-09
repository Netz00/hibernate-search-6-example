package com.netz00.hibernatesearch6example.repository;

import com.netz00.hibernatesearch6example.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
