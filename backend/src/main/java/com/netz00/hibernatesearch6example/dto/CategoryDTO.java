package com.netz00.hibernatesearch6example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CategoryDTO {

    private Long id;

    @NotNull
    @JsonProperty("title")
    private String title;

}
