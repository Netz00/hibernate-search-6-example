package com.netz00.hibernatesearch6example.services.api;

import com.netz00.hibernatesearch6example.model.Category;
import com.netz00.hibernatesearch6example.model.Comment;
import com.netz00.hibernatesearch6example.model.Freelancer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FreelancerService {


    Page<Freelancer> findAll(Pageable pageable);

    Freelancer save(Freelancer category);

    Freelancer delete(Long id);


    Comment saveComment(Long id, Comment comment);

    Page<Comment> findAllComments(Long id, Pageable pageable);

    Comment deleteComment(Long id, Long commentId);

    Comment patchComment(Long commentId, Comment comment);


    Category addCategory(Long id, Long categoryId);

    Category removeCategory(Long id, Long categoryId);

}
