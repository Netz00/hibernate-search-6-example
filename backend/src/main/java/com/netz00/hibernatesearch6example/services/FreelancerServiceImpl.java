package com.netz00.hibernatesearch6example.services;

import com.netz00.hibernatesearch6example.dto.CategoryDTO;
import com.netz00.hibernatesearch6example.dto.CommentDTO;
import com.netz00.hibernatesearch6example.dto.FreelancerDTO;
import com.netz00.hibernatesearch6example.dto.ProjectDTO;
import com.netz00.hibernatesearch6example.exceptions.ResourceNotFoundException;
import com.netz00.hibernatesearch6example.model.Category;
import com.netz00.hibernatesearch6example.model.Comment;
import com.netz00.hibernatesearch6example.model.Freelancer;
import com.netz00.hibernatesearch6example.model.Project;
import com.netz00.hibernatesearch6example.model.mapper.CategoryMapper;
import com.netz00.hibernatesearch6example.model.mapper.CommentMapper;
import com.netz00.hibernatesearch6example.model.mapper.FreelancerMapper;
import com.netz00.hibernatesearch6example.model.mapper.ProjectMapper;
import com.netz00.hibernatesearch6example.repository.CategoryRepository;
import com.netz00.hibernatesearch6example.repository.CommentRepository;
import com.netz00.hibernatesearch6example.repository.FreelancerRepository;
import com.netz00.hibernatesearch6example.repository.ProjectRepository;
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
    private final ProjectRepository projectRepository;

    private final FreelancerMapper freelancerMapper;
    private final CommentMapper commentMapper;
    private final CategoryMapper categoryMapper;
    private final ProjectMapper projectMapper;

    public FreelancerServiceImpl(FreelancerRepository freelancerRepository, CommentRepository commentRepository, CategoryRepository categoryRepository, ProjectRepository projectRepository, FreelancerMapper freelancerMapper, CommentMapper commentMapper, CategoryMapper categoryMapper, ProjectMapper projectMapper) {
        this.freelancerRepository = freelancerRepository;
        this.commentRepository = commentRepository;
        this.categoryRepository = categoryRepository;
        this.projectRepository = projectRepository;
        this.freelancerMapper = freelancerMapper;
        this.commentMapper = commentMapper;
        this.categoryMapper = categoryMapper;
        this.projectMapper = projectMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FreelancerDTO> findAll(Pageable pageable) {

        return freelancerRepository.findAll(pageable).map(freelancerMapper::toDtoRemoveCommentsAndProjects);
    }

    @Override
    @Transactional(readOnly = true)
    public FreelancerDTO findById(Long id) {

        return freelancerMapper.toDto(freelancerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Freelancer not found with id :" + id)));
    }

    @Override
    public FreelancerDTO save(FreelancerDTO freelancerDTO) {

        return freelancerMapper.toDto(freelancerRepository.save(freelancerMapper.toEntity(freelancerDTO)));
    }

    @Override
    public FreelancerDTO delete(Long id) throws ResourceNotFoundException {

        Freelancer freelancer = freelancerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Freelancer not found with id :" + id));
        freelancerRepository.delete(freelancer);

        return freelancerMapper.toDto(freelancer);
    }

    // --------------------- COMMENTS ---------------------

    @Override
    public Page<CommentDTO> findAllComments(Long id, Pageable pageable) {
        Freelancer freelancer = freelancerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Freelancer not found with id :" + id));

        return freelancerRepository.findBy(freelancer, pageable).map(commentMapper::toDto);
    }

    @Override
    public CommentDTO saveComment(Long id, CommentDTO commentDTO) {

        Freelancer freelancer = freelancerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Freelancer not found with id :" + id));
        freelancer.addComment(commentMapper.toEntity(commentDTO));

        return commentDTO;
    }

    @Override
    public CommentDTO patchComment(Long commentId, CommentDTO commentDTO) {

        Comment commentOld = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id :" + commentId));
        commentOld.setValue(commentMapper.toEntity(commentDTO).getValue());

        return commentMapper.toDto(commentOld);
    }

    @Override
    public CommentDTO deleteComment(Long id, Long commentId) {

        Freelancer freelancer = freelancerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Freelancer not found with id :" + id));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id :" + commentId));
        freelancer.removeComment(comment);

        return commentMapper.toDto(comment);
    }


    // --------------------- CATEGORIES ---------------------

    @Override
    public CategoryDTO addCategory(Long id, Long categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with id :" + id));
        Freelancer freelancer = freelancerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Freelancer not found with id :" + id));
        freelancer.addCategory(category);

        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDTO removeCategory(Long id, Long categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with id :" + id));
        Freelancer freelancer = freelancerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Freelancer not found with id :" + id));
        freelancer.removeCategory(category);

        return categoryMapper.toDto(category);
    }

    // --------------------- PROJECTS ---------------------

    @Override
    public ProjectDTO addProject(Long id, Long projectId) {

        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project not found with id :" + id));
        Freelancer freelancer = freelancerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Freelancer not found with id :" + id));
        freelancer.addProject(project);

        return projectMapper.toDtoWithoutFreelancers(project);
    }

    @Override
    public ProjectDTO removeProject(Long id, Long projectId) {

        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project not found with id :" + id));
        Freelancer freelancer = freelancerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Freelancer not found with id :" + id));
        freelancer.removeProject(project);

        return projectMapper.toDtoWithoutFreelancers(project);
    }

}
