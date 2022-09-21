package com.netz00.hibernatesearch6example.services.api;

import com.netz00.hibernatesearch6example.dto.ProjectDTO;
import com.netz00.hibernatesearch6example.model.enums.ProjectSort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {


    Page<ProjectDTO> findAll(Pageable pageable);

    Page<ProjectDTO> searchProjects(String query, ProjectSort sort, Boolean ascending, int page, int size);

    Page<ProjectDTO> searchProjectsEntities(String query, ProjectSort sort, Boolean ascending, int page, int size);

    ProjectDTO save(ProjectDTO project);

    ProjectDTO delete(Long id);
}
