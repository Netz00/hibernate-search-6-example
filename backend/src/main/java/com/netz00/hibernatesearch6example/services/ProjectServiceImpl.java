package com.netz00.hibernatesearch6example.services;


import com.netz00.hibernatesearch6example.dto.ProjectDTO;
import com.netz00.hibernatesearch6example.exceptions.ResourceNotFoundException;
import com.netz00.hibernatesearch6example.model.Project;
import com.netz00.hibernatesearch6example.model.mapper.ProjectMapper;
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

    private final ProjectMapper projectMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }


    @Override
    public Page<ProjectDTO> findAll(Pageable pageable) {
        return projectRepository.findAll(pageable).map(projectMapper::toDto);
    }

    @Override
    public ProjectDTO save(ProjectDTO project) {
        return projectMapper.toDto(projectRepository.save(projectMapper.toEntity(project)));
    }

    @Override
    public ProjectDTO delete(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found with id :" + id));
        projectRepository.delete(project);
        return projectMapper.toDto(project);
    }


}
