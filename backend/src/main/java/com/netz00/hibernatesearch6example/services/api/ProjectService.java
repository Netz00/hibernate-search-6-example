package com.netz00.hibernatesearch6example.services.api;

import com.netz00.hibernatesearch6example.dto.ProjectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {


    Page<ProjectDTO> findAll(Pageable pageable);


    ProjectDTO save(ProjectDTO project);

    ProjectDTO delete(Long id);
}
