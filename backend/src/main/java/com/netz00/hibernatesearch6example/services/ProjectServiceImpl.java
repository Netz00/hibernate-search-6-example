package com.netz00.hibernatesearch6example.services;


import com.netz00.hibernatesearch6example.dto.ProjectDTO;
import com.netz00.hibernatesearch6example.exceptions.ResourceNotFoundException;
import com.netz00.hibernatesearch6example.model.Project;
import com.netz00.hibernatesearch6example.model.enums.ProjectSort;
import com.netz00.hibernatesearch6example.model.mapper.ProjectMapper;
import com.netz00.hibernatesearch6example.repository.ProjectRepository;
import com.netz00.hibernatesearch6example.services.api.ProjectService;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectMapper projectMapper;

    EntityManager entityManager;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, EntityManager entityManager) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.entityManager = entityManager;

    }


    @Override
    public Page<ProjectDTO> findAll(Pageable pageable) {
        return projectRepository.findAll(pageable).map(projectMapper::toDto);
    }


    /**
     * Search method capable for search and sort.
     * Uses projections and fetched results directly from Elasticsearch.
     *
     * @param query     the string containing searched term, can be od part of project name
     * @param sort      should results be sorted
     * @param ascending change the order if sorting used
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<ProjectDTO> searchProjects(String query, ProjectSort sort, Boolean ascending, int page, int size) {

        int length = query.length();

        SearchSession searchSession = Search.session(entityManager);

        SearchResult<List<?>> result = searchSession.search(Project.class)
                .select(
                        f -> f.composite(f.id(Long.class),
                                f.field("name", String.class),
                                f.field("date_created", Date.class)
                        )
                )
                .where(f -> f.match()
                        .fields("name")
                        .matching(query)
                        .fuzzy(length > 3 ? 1 : 0))      // if query longer than 3 chars allow fuzzy for the edit distance of 1
                .sort(sort == null ?
                        f -> f.score()
                        :
                        f -> ascending ?
                                f.field(sort.toString().toLowerCase())
                                :
                                f.field(sort.toString().toLowerCase()).desc()
                )
                .fetch(page * size, size);      // fetch single page of results

        List<ProjectDTO> projectDTOS = result.hits().stream().map(s -> {
            ProjectDTO projectDTO = new ProjectDTO();

            projectDTO.setId((Long) s.get(0));
            projectDTO.setName((String) s.get(1));
            projectDTO.setDateCreated((Date) s.get(2));

            return projectDTO;
        }).collect(Collectors.toList());

        long totalHitCount = result.total().hitCount();
        Pageable paging = PageRequest.of(page, size);

        return new PageImpl<>(projectDTOS, paging, totalHitCount);
    }

    /**
     * Same as previous but without using Projections
     *
     * @param query
     * @param sort
     * @param ascending
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<ProjectDTO> searchProjectsEntities(String query, ProjectSort sort, Boolean ascending, int page, int size) {

        int length = query.length();

        SearchSession searchSession = Search.session(entityManager);

        SearchResult<Project> result = searchSession.search(Project.class)
                .where(f -> f.match()
                        .fields("name")
                        .matching(query)
                        .fuzzy(length > 3 ? 1 : 0))      // if query longer than 3 chars allow fuzzy for the edit distance of 1
                .sort(sort == null ?
                        f -> f.score()
                        :
                        f -> ascending ?
                                f.field(sort.toString().toLowerCase())
                                :
                                f.field(sort.toString().toLowerCase()).desc()
                )
                .fetch(page * size, size);      // fetch single page of results


        long totalHitCount = result.total().hitCount();
        Pageable paging = PageRequest.of(page, size);

        return new PageImpl<>(projectMapper.toDto(result.hits()), paging, totalHitCount);
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
