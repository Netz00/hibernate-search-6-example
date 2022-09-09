package com.netz00.hibernatesearch6example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class CommentDTO {

    private Long id;

    @NotEmpty(message = "comment must not be empty")
    @JsonProperty("value")
    @Size(max = 255, message = "Comment must be under 255 characters length")
    private String value;

    @JsonProperty("date_created")
    private Date dateCreated;

    @JsonProperty("date_modified")
    private Date dateModified;

}
