package com.netz00.hibernatesearch6example.controller;


import com.netz00.hibernatesearch6example.controller.endpoints.RestEndpoints;
import com.netz00.hibernatesearch6example.controller.endpoints.RestEndpointsParameters;
import com.netz00.hibernatesearch6example.dto.CategoryDTO;
import com.netz00.hibernatesearch6example.dto.CommentDTO;
import com.netz00.hibernatesearch6example.dto.FreelancerDTO;
import com.netz00.hibernatesearch6example.model.mapper.CategoryMapper;
import com.netz00.hibernatesearch6example.model.mapper.ProjectMapper;
import com.netz00.hibernatesearch6example.model.mapper.CommentMapper;
import com.netz00.hibernatesearch6example.model.mapper.FreelancerMapper;
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
    private final FreelancerMapper freelancerMapper;
    private final CommentMapper commentMapper;

    private final CategoryMapper categoryMapper;

    private final ProjectMapper clientMapper;


    public FreelancerController(FreelancerService freelancerService, FreelancerMapper freelancerMapper, CommentMapper commentMapper, CategoryMapper categoryMapper, ProjectMapper clientMapper) {
        this.freelancerService = freelancerService;
        this.freelancerMapper = freelancerMapper;
        this.commentMapper = commentMapper;
        this.categoryMapper = categoryMapper;
        this.clientMapper = clientMapper;
    }

    @GetMapping()
    public ResponseEntity<Page<FreelancerDTO>> getAllFreelancers(Pageable pageable) {
        return new ResponseEntity<>(freelancerService.findAll(pageable).map(freelancerMapper::toDto), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<FreelancerDTO> createFreelancer(@Valid @RequestBody FreelancerDTO freelancerDTO) {

        return new ResponseEntity<>(freelancerMapper.toDto(freelancerService.save(freelancerMapper.toEntity(freelancerDTO))), HttpStatus.CREATED);
    }

    @DeleteMapping(RestEndpointsParameters.FREELANCER_ID)
    public ResponseEntity<FreelancerDTO> deleteFreelancer(@PathVariable("id") final Long id) {
        return new ResponseEntity<>(freelancerMapper.toDto(freelancerService.delete(id)), HttpStatus.OK);
    }

    // --------------------- COMMENTS ---------------------

    @GetMapping(RestEndpointsParameters.FREELANCER_ID + RestEndpoints.API_FREELANCER_COMMENT)
    public ResponseEntity<Page<CommentDTO>> getAllFreelancerComments(@PathVariable("id") final Long id, Pageable pageable) {
        return new ResponseEntity<>(freelancerService.findAllComments(id, pageable).map(commentMapper::toDto), HttpStatus.OK);
    }

    @PostMapping(RestEndpointsParameters.FREELANCER_ID + RestEndpoints.API_FREELANCER_COMMENT)
    public ResponseEntity<CommentDTO> createFreelancerComment(@PathVariable("id") final Long id, @Valid @RequestBody CommentDTO commentDTO) {

        return new ResponseEntity<>(commentMapper.toDto(freelancerService.saveComment(id, commentMapper.toEntity(commentDTO))), HttpStatus.CREATED);
    }

    @PatchMapping(RestEndpointsParameters.FREELANCER_ID + RestEndpoints.API_FREELANCER_COMMENT + RestEndpointsParameters.COMMENT_ID)
    public ResponseEntity<CommentDTO> patchFreelancerComment(@PathVariable("commentId") final Long id, @Valid @RequestBody CommentDTO commentDTO) {

        return new ResponseEntity<>(commentMapper.toDto(freelancerService.patchComment(id, commentMapper.toEntity(commentDTO))), HttpStatus.OK);
    }

    @DeleteMapping(RestEndpointsParameters.FREELANCER_ID + RestEndpoints.API_FREELANCER_COMMENT + RestEndpointsParameters.COMMENT_ID)
    public ResponseEntity<CommentDTO> deleteFreelancerComment(@PathVariable("id") final Long id, @PathVariable("commentId") final Long commentId) {
        return new ResponseEntity<>(commentMapper.toDto(freelancerService.deleteComment(id, commentId)), HttpStatus.OK);
    }

    // --------------------- CATEGORY ---------------------

    @PostMapping(RestEndpointsParameters.FREELANCER_ID + RestEndpoints.API_FREELANCER_CATEGORY + RestEndpointsParameters.CATEGORY_ID)
    public ResponseEntity<CategoryDTO> addFreelancerCategory(@PathVariable("id") final Long id, @PathVariable("categoryId") final Long categoryId) {

        return new ResponseEntity<>(categoryMapper.toDto(freelancerService.addCategory(id, categoryId)), HttpStatus.CREATED);
    }


    @DeleteMapping(RestEndpointsParameters.FREELANCER_ID + RestEndpoints.API_FREELANCER_CATEGORY + RestEndpointsParameters.CATEGORY_ID)
    public ResponseEntity<CategoryDTO> removeFreelancerCategory(@PathVariable("id") final Long id, @PathVariable("categoryId") final Long categoryId) {

        return new ResponseEntity<>(categoryMapper.toDto(freelancerService.removeCategory(id, categoryId)), HttpStatus.OK);
    }



    /*
    // --------------------- CLIENT ---------------------

    @PostMapping(RestEndpointsParameters.FREELANCER_ID + RestEndpoints.API_FREELANCER_CLIENT + RestEndpointsParameters.CLIENT_ID)
    public ResponseEntity<ClientDTO> addFreelancerClient(@PathVariable("id") final Long id, @PathVariable("clientId") final Long clientId) {

        return new ResponseEntity<>(clientMapper.toDto(freelancerService.addClient(id, clientId)), HttpStatus.CREATED);
    }


    @DeleteMapping(RestEndpointsParameters.FREELANCER_ID + RestEndpoints.API_FREELANCER_CLIENT + RestEndpointsParameters.CLIENT_ID)
    public ResponseEntity<ClientDTO> removeFreelancerClient(@PathVariable("id") final Long id, @PathVariable("clientId") final Long clientId) {

        return new ResponseEntity<>(clientMapper.toDto(freelancerService.removeClient(id, clientId)), HttpStatus.OK);
    }
*/

}
