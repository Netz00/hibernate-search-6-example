package com.netz00.hibernatesearch6example.services.api;

import com.netz00.hibernatesearch6example.dto.CategoryDTO;
import com.netz00.hibernatesearch6example.dto.CommentDTO;
import com.netz00.hibernatesearch6example.dto.FreelancerDTO;
import com.netz00.hibernatesearch6example.dto.ProjectDTO;
import com.netz00.hibernatesearch6example.model.enums.FreelancerSort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FreelancerService {


    Page<FreelancerDTO> findAll(Pageable pageable);

    FreelancerDTO findById(Long id);

    Page<FreelancerDTO> searchCourses(String query, FreelancerSort sort, List<String> categories, Boolean ascending, int page, int size);

    FreelancerDTO save(FreelancerDTO category);

    FreelancerDTO delete(Long id);


    CommentDTO saveComment(Long id, CommentDTO comment);

    Page<CommentDTO> findAllComments(Long id, Pageable pageable);

    CommentDTO deleteComment(Long id, Long commentId);

    CommentDTO patchComment(Long commentId, CommentDTO comment);


    CategoryDTO addCategory(Long id, Long categoryId);

    CategoryDTO removeCategory(Long id, Long categoryId);

    ProjectDTO addProject(Long id, Long projectId);

    ProjectDTO removeProject(Long id, Long projectId);
}
