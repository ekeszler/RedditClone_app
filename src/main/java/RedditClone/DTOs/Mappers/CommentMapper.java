package RedditClone.DTOs.Mappers;

import RedditClone.DTOs.CommentRequestDTO;
import RedditClone.Model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {



    @Mapping(target = "id", ignore=true)
    @Mapping(target = "createdDate", expression = "java(java.time.LocalDateTime.now())")
    public abstract Comment mapCommentRequestDTOtoComment(CommentRequestDTO commentRequestDTO);
}
