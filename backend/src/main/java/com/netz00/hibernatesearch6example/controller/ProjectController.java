package com.netz00.hibernatesearch6example.controller;


import com.netz00.hibernatesearch6example.controller.endpoints.RestEndpoints;
import com.netz00.hibernatesearch6example.controller.endpoints.RestEndpointsParameters;
import com.netz00.hibernatesearch6example.dto.ProjectDTO;
import com.netz00.hibernatesearch6example.model.mapper.ProjectMapper;
import com.netz00.hibernatesearch6example.services.api.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(RestEndpoints.API_PROJECT)
@Validated
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectMapper projectMapper;

    public ProjectController(ProjectService projectService, ProjectMapper projectMapper) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
    }

    @GetMapping()
    public ResponseEntity<Page<ProjectDTO>> getAllProjects(Pageable pageable) {
        return new ResponseEntity<>(projectService.findAll(pageable).map(projectMapper::toDto), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ProjectDTO> createProject(@Valid @RequestBody ProjectDTO projectDTO) {

        return new ResponseEntity<>(projectMapper.toDto(projectService.save(projectMapper.toEntity(projectDTO))), HttpStatus.CREATED);
    }

    @DeleteMapping(RestEndpointsParameters.PROJECT_ID)
    public ResponseEntity<ProjectDTO> deleteProject(@PathVariable("projectId") final Long id) {
        return new ResponseEntity<>(projectMapper.toDto(projectService.delete(id)), HttpStatus.OK);
    }


}
