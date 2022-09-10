package com.netz00.hibernatesearch6example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Data
public class FreelancerDTO {

    private Long id;

    @NotEmpty(message = "username must not be empty")
    @JsonProperty("username")
    private String username;

    @NotEmpty(message = "firstName must not be empty")
    @JsonProperty("firstName")
    private String firstName;

    @NotEmpty(message = "lastName must not be empty")
    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("comments")
    private Set<CommentDTO> comments = new HashSet<>();

    @JsonProperty("categories")
    private Set<CategoryDTO> categories = new HashSet<>();

    @JsonProperty("projects")
    private Set<ProjectDTO> projects = new HashSet<>();
}
