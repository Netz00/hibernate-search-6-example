package com.netz00.hibernatesearch6example.services;

import com.netz00.hibernatesearch6example.exceptions.ResourceNotFoundException;
import com.netz00.hibernatesearch6example.model.Category;
import com.netz00.hibernatesearch6example.model.Comment;
import com.netz00.hibernatesearch6example.model.Freelancer;
import com.netz00.hibernatesearch6example.repository.CategoryRepository;
import com.netz00.hibernatesearch6example.repository.CommentRepository;
import com.netz00.hibernatesearch6example.repository.FreelancerRepository;
import com.netz00.hibernatesearch6example.services.api.FreelancerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FreelancerServiceImpl implements FreelancerService {

    private final FreelancerRepository freelancerRepository;
    private final CommentRepository commentRepository;

    private final CategoryRepository categoryRepository;

    public FreelancerServiceImpl(FreelancerRepository freelancerRepository, CommentRepository commentRepository, CategoryRepository categoryRepository) {
        this.freelancerRepository = freelancerRepository;
        this.commentRepository = commentRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Freelancer> findAll(Pageable pageable) {

        return freelancerRepository.findAll(pageable);
    }

    @Override
    public Freelancer save(Freelancer freelancer) {
        return freelancerRepository.save(freelancer);
    }

    @Override
    public Freelancer delete(Long id) throws ResourceNotFoundException {

        Freelancer freelancer = freelancerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Freelancer not found with id :" + id));
        freelancerRepository.delete(freelancer);

        return freelancer;
    }

    // --------------------- COMMENTS ---------------------

    @Override
    public Page<Comment> findAllComments(Long id, Pageable pageable) {
        Freelancer freelancer = freelancerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Freelancer not found with id :" + id));

        return freelancerRepository.findBy(freelancer, pageable);
    }

    @Override
    public Comment saveComment(Long id, Comment comment) {

        Freelancer freelancer = freelancerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Freelancer not found with id :" + id));
        freelancer.addComment(comment);

        return comment;
    }

    @Override
    public Comment patchComment(Long commentId, Comment comment) {

        Comment commentOld = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id :" + commentId));
        commentOld.setValue(comment.getValue());

        return commentOld;
    }

    @Override
    public Comment deleteComment(Long id, Long commentId) {

        Freelancer freelancer = freelancerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Freelancer not found with id :" + id));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id :" + commentId));
        freelancer.removeComment(comment);

        return comment;
    }


    // --------------------- CATEGORIES ---------------------

    @Override
    public Category addCategory(Long id, Long categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with id :" + id));
        Freelancer freelancer = freelancerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Freelancer not found with id :" + id));
        freelancer.addCategory(category);

        return category;
    }

    @Override
    public Category removeCategory(Long id, Long categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with id :" + id));
        Freelancer freelancer = freelancerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Freelancer not found with id :" + id));
        freelancer.removeCategory(category);

        return category;
    }

}
