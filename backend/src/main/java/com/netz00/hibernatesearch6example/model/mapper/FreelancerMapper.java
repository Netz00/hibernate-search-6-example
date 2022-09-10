package com.netz00.hibernatesearch6example.model.mapper;

import com.netz00.hibernatesearch6example.dto.FreelancerDTO;
import com.netz00.hibernatesearch6example.model.Freelancer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


/**
 * Mapper for the entity {@link Freelancer} and its DTO {@link FreelancerDTO}.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class, CommentMapper.class})
public interface FreelancerMapper extends EntityMapper<FreelancerDTO, Freelancer> {

    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "projects", ignore = true)
    Freelancer toEntity(FreelancerDTO freelancerDTO);

}




