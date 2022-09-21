package com.netz00.hibernatesearch6example.controller;


import com.netz00.hibernatesearch6example.controller.endpoints.RestEndpoints;
import com.netz00.hibernatesearch6example.controller.endpoints.RestEndpointsParameters;
import com.netz00.hibernatesearch6example.dto.FreelancerDTO;
import com.netz00.hibernatesearch6example.dto.ProjectDTO;
import com.netz00.hibernatesearch6example.model.enums.FreelancerSort;
import com.netz00.hibernatesearch6example.model.enums.ProjectSort;
import com.netz00.hibernatesearch6example.services.api.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping(RestEndpoints.API_PROJECT)
@Validated
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping()
    public ResponseEntity<Page<ProjectDTO>> getAllProjects(Pageable pageable) {
        return new ResponseEntity<>(projectService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProjectDTO>> searchFreelancers(
            @RequestParam(name = "query") @Size(min = 1, max = 15) String query,
            @RequestParam(name = "sort", required = false) ProjectSort sort,
            @RequestParam(name = "ascending", required = false, defaultValue = "true") Boolean ascending,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        return new ResponseEntity<>(projectService.searchProjects(query, sort, ascending, page, size), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ProjectDTO> createProject(@Valid @RequestBody ProjectDTO projectDTO) {

        return new ResponseEntity<>(projectService.save(projectDTO), HttpStatus.CREATED);
    }

    @DeleteMapping(RestEndpointsParameters.PROJECT_ID)
    public ResponseEntity<ProjectDTO> deleteProject(@PathVariable("projectId") final Long id) {
        return new ResponseEntity<>(projectService.delete(id), HttpStatus.OK);
    }


}
