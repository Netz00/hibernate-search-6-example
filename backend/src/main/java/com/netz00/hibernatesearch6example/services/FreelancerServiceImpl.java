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
import com.netz00.hibernatesearch6example.model.enums.FreelancerSort;
import com.netz00.hibernatesearch6example.model.mapper.CategoryMapper;
import com.netz00.hibernatesearch6example.model.mapper.CommentMapper;
import com.netz00.hibernatesearch6example.model.mapper.FreelancerMapper;
import com.netz00.hibernatesearch6example.model.mapper.ProjectMapper;
import com.netz00.hibernatesearch6example.repository.CategoryRepository;
import com.netz00.hibernatesearch6example.repository.CommentRepository;
import com.netz00.hibernatesearch6example.repository.FreelancerRepository;
import com.netz00.hibernatesearch6example.repository.ProjectRepository;
import com.netz00.hibernatesearch6example.services.api.FreelancerService;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

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

    private final EntityManager entityManager;

    public FreelancerServiceImpl(FreelancerRepository freelancerRepository, CommentRepository commentRepository, CategoryRepository categoryRepository, ProjectRepository projectRepository, FreelancerMapper freelancerMapper, CommentMapper commentMapper, CategoryMapper categoryMapper, ProjectMapper projectMapper, EntityManager entityManager) {
        this.freelancerRepository = freelancerRepository;
        this.commentRepository = commentRepository;
        this.categoryRepository = categoryRepository;
        this.projectRepository = projectRepository;
        this.freelancerMapper = freelancerMapper;
        this.commentMapper = commentMapper;
        this.categoryMapper = categoryMapper;
        this.projectMapper = projectMapper;
        this.entityManager = entityManager;
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

    /**
     * Generic search method capable for any combination of search/filter/sort or none of them.
     * Uses projections and fetched results directly from Elasticsearch.
     *
     * @param query             the string containing searched term, can be entire username od part of first/last name
     * @param sort              should results be sorted
     * @param categoriesStrings filter results by categories
     * @param ascending         change the order if sorting used
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<FreelancerDTO> searchFreelancers(String query, FreelancerSort sort, List<String> categoriesStrings, Boolean ascending, int page, int size) {

        boolean noFilter = categoriesStrings == null || categoriesStrings.isEmpty();
        boolean noQuery = query == null || query.isEmpty();
        boolean searchAll = noQuery && noFilter;
        int length = noQuery ? 0 : query.length();

        SearchSession searchSession = Search.session(entityManager);

        SearchResult<List<?>> result = searchSession.search(Freelancer.class)
                .select(
                        f -> f.composite(f.id(Long.class),
                                f.field("username", String.class),
                                f.field("first_name", String.class),
                                f.field("last_name", String.class),
                                f.field("categories.title", String.class).multi()
                        )
                )
                .where(f -> searchAll ?
                        f.matchAll()
                        :
                        f.bool()
                                .filter(
                                        noFilter
                                                ?
                                                f.matchAll()    // don't use this condition
                                                :
                                                f.terms().field("categories.title")
                                                        .matchingAny(categoriesStrings)
                                )
                                .must(
                                        noQuery
                                                ?
                                                f.matchAll()    // don't use this condition
                                                :
                                                f.match().fields("first_name").boost(2.0f)
                                                        .fields("last_name").boost(3.0f)
                                                        .matching(query).fuzzy(length > 3 ? 1 : 0)  // if query longer than 3 chars allow fuzzy for the edit distance of 1)
                                )
                )
                .sort(sort == FreelancerSort.FIRST_LAST_NAME ?
                        f -> f.composite()
                                .add(f.field("first_name_sort").desc())
                                .add(f.field("last_name_sort").desc())
                                .add(
                                        ascending ?
                                                f.score()
                                                :
                                                f.score().desc()
                                )
                        :
                        searchAll ?
                                f -> f.field("id")  // sort them by ID
                                :
                                f -> f.score()         // sort results by score (DEFAULT)
                )
                .fetch(page * size, size);      // fetch single page of results


        List<FreelancerDTO> freelancerDTOS = result.hits().stream().map(s -> {
            FreelancerDTO freelancerDTO = new FreelancerDTO();

            freelancerDTO.setId((Long) s.get(0));
            freelancerDTO.setUsername((String) s.get(1));
            freelancerDTO.setFirstName((String) s.get(2));
            freelancerDTO.setLastName((String) s.get(3));

            List<String> categories = (List<String>) s.get(4);
            freelancerDTO.setCategories(
                    categories.stream().map(c -> {
                        CategoryDTO category = new CategoryDTO();
                        category.setTitle(c);
                        return category;
                    }).collect(Collectors.toSet())
            );

            return freelancerDTO;
        }).collect(Collectors.toList());

        long totalHitCount = result.total().hitCount();
        Pageable paging = PageRequest.of(page, size);

        return new PageImpl<>(freelancerDTOS, paging, totalHitCount);
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
