package com.netz00.hibernatesearch6example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class ProjectDTO {

    private Long id;

    @NotEmpty(message = "name must not be empty")
    @JsonProperty("name")
    private String name;

    @NotEmpty(message = "description must not be empty")
    @JsonProperty("description")
    private String description;

    @JsonProperty("date_created")
    private Date dateCreated;

    @JsonProperty("freelancers")
    private Set<FreelancerDTO> freelancerDTOS = new HashSet<>();

}
