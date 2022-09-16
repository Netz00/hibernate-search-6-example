package com.netz00.hibernatesearch6example.model.mapper;

import com.netz00.hibernatesearch6example.dto.FreelancerDTO;
import com.netz00.hibernatesearch6example.model.Freelancer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


/**
 * Mapper for the entity {@link Freelancer} and its DTO {@link FreelancerDTO}.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class, CommentMapper.class, ProjectMapper.class})
public interface FreelancerMapper extends EntityMapper<FreelancerDTO, Freelancer> {

    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "projects", ignore = true)
    Freelancer toEntity(FreelancerDTO freelancerDTO);

    @Override
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "projects", qualifiedByName = "noFreelancers")
    FreelancerDTO toDto(Freelancer entity);

    @Named("removeCommentsAndProjects")
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "projects", ignore = true)
    FreelancerDTO toDtoRemoveCommentsAndProjects(Freelancer freelancer);

}




