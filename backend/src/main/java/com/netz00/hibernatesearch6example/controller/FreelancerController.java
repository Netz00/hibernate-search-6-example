package com.netz00.hibernatesearch6example.controller;


import com.netz00.hibernatesearch6example.controller.endpoints.RestEndpoints;
import com.netz00.hibernatesearch6example.controller.endpoints.RestEndpointsParameters;
import com.netz00.hibernatesearch6example.dto.CategoryDTO;
import com.netz00.hibernatesearch6example.dto.CommentDTO;
import com.netz00.hibernatesearch6example.dto.FreelancerDTO;
import com.netz00.hibernatesearch6example.dto.ProjectDTO;
import com.netz00.hibernatesearch6example.services.api.FreelancerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(RestEndpoints.API_FREELANCER)
@Validated
public class FreelancerController {

    private final FreelancerService freelancerService;


    public FreelancerController(FreelancerService freelancerService) {
        this.freelancerService = freelancerService;

    }

    @GetMapping()
    public ResponseEntity<Page<FreelancerDTO>> getAllFreelancers(Pageable pageable) {
        return new ResponseEntity<>(freelancerService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(RestEndpointsParameters.FREELANCER_ID)
    public ResponseEntity<FreelancerDTO> getFreelancer(@PathVariable("id") final Long id) {
        return new ResponseEntity<>(freelancerService.findById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<FreelancerDTO> createFreelancer(@Valid @RequestBody FreelancerDTO freelancerDTO) {

        return new ResponseEntity<>(freelancerService.save(freelancerDTO), HttpStatus.CREATED);
    }

    @DeleteMapping(RestEndpointsParameters.FREELANCER_ID)
    public ResponseEntity<FreelancerDTO> deleteFreelancer(@PathVariable("id") final Long id) {
        return new ResponseEntity<>(freelancerService.delete(id), HttpStatus.OK);
    }

    // --------------------- COMMENTS ---------------------

    @GetMapping(RestEndpointsParameters.FREELANCER_ID + RestEndpoints.API_FREELANCER_COMMENT)
    public ResponseEntity<Page<CommentDTO>> getAllFreelancerComments(@PathVariable("id") final Long id, Pageable pageable) {
        return new ResponseEntity<>(freelancerService.findAllComments(id, pageable), HttpStatus.OK);
    }

    @PostMapping(RestEndpointsParameters.FREELANCER_ID + RestEndpoints.API_FREELANCER_COMMENT)
    public ResponseEntity<CommentDTO> createFreelancerComment(@PathVariable("id") final Long id, @Valid @RequestBody CommentDTO commentDTO) {

        return new ResponseEntity<>(freelancerService.saveComment(id, commentDTO), HttpStatus.CREATED);
    }

    @PatchMapping(RestEndpointsParameters.FREELANCER_ID + RestEndpoints.API_FREELANCER_COMMENT + RestEndpointsParameters.COMMENT_ID)
    public ResponseEntity<CommentDTO> patchFreelancerComment(@PathVariable("commentId") final Long id, @Valid @RequestBody CommentDTO commentDTO) {

        return new ResponseEntity<>(freelancerService.patchComment(id, commentDTO), HttpStatus.OK);
    }

    @DeleteMapping(RestEndpointsParameters.FREELANCER_ID + RestEndpoints.API_FREELANCER_COMMENT + RestEndpointsParameters.COMMENT_ID)
    public ResponseEntity<CommentDTO> deleteFreelancerComment(@PathVariable("id") final Long id, @PathVariable("commentId") final Long commentId) {
        return new ResponseEntity<>(freelancerService.deleteComment(id, commentId), HttpStatus.OK);
    }

    // --------------------- CATEGORY ---------------------

    @PostMapping(RestEndpointsParameters.FREELANCER_ID + RestEndpoints.API_FREELANCER_CATEGORY + RestEndpointsParameters.CATEGORY_ID)
    public ResponseEntity<CategoryDTO> addFreelancerCategory(@PathVariable("id") final Long id, @PathVariable("categoryId") final Long categoryId) {

        return new ResponseEntity<>(freelancerService.addCategory(id, categoryId), HttpStatus.CREATED);
    }


    @DeleteMapping(RestEndpointsParameters.FREELANCER_ID + RestEndpoints.API_FREELANCER_CATEGORY + RestEndpointsParameters.CATEGORY_ID)
    public ResponseEntity<CategoryDTO> removeFreelancerCategory(@PathVariable("id") final Long id, @PathVariable("categoryId") final Long categoryId) {

        return new ResponseEntity<>(freelancerService.removeCategory(id, categoryId), HttpStatus.OK);
    }


    // --------------------- PROJECT ---------------------

    @PostMapping(RestEndpointsParameters.FREELANCER_ID + RestEndpoints.API_FREELANCER_PROJECT + RestEndpointsParameters.PROJECT_ID)
    public ResponseEntity<ProjectDTO> addFreelancerProject(@PathVariable("id") final Long id, @PathVariable("projectId") final Long projectId) {

        return new ResponseEntity<>(freelancerService.addProject(id, projectId), HttpStatus.CREATED);
    }


    @DeleteMapping(RestEndpointsParameters.FREELANCER_ID + RestEndpoints.API_FREELANCER_PROJECT + RestEndpointsParameters.PROJECT_ID)
    public ResponseEntity<ProjectDTO> removeFreelancerProject(@PathVariable("id") final Long id, @PathVariable("projectId") final Long projectId) {

        return new ResponseEntity<>(freelancerService.removeProject(id, projectId), HttpStatus.OK);
    }


}
