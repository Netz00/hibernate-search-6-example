package com.netz00.hibernatesearch6example.services;


import com.netz00.hibernatesearch6example.exceptions.ResourceNotFoundException;
import com.netz00.hibernatesearch6example.model.Project;
import com.netz00.hibernatesearch6example.repository.ProjectRepository;
import com.netz00.hibernatesearch6example.services.api.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    @Override
    public Page<Project> findAll(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project delete(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found with id :" + id));
        projectRepository.delete(project);
        return project;
    }


}
