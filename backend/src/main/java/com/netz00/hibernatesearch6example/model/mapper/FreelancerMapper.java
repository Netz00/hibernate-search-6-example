package com.netz00.hibernatesearch6example.model.mapper;

import com.netz00.hibernatesearch6example.dto.FreelancerDTO;
import com.netz00.hibernatesearch6example.model.Freelancer;
import org.mapstruct.Mapper;


/**
 * Mapper for the entity {@link Freelancer} and its DTO {@link FreelancerDTO}.
 */
@Mapper(componentModel = "spring")
public interface FreelancerMapper extends EntityMapper<FreelancerDTO, Freelancer> {

}




