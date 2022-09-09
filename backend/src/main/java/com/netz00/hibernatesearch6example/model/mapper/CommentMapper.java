package com.netz00.hibernatesearch6example.model.mapper;

import com.netz00.hibernatesearch6example.dto.CommentDTO;
import com.netz00.hibernatesearch6example.model.Comment;
import org.mapstruct.Mapper;


/**
 * Mapper for the entity {@link Comment} and its DTO {@link CommentDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {

}




