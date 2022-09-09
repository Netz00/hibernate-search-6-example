package com.netz00.hibernatesearch6example.services.api;

import com.netz00.hibernatesearch6example.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {


    Page<Project> findAll(Pageable pageable);


    Project save(Project project);

    Project delete(Long id);
}
