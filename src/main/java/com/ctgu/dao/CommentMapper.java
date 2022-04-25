package com.ctgu.dao;

import com.ctgu.model.Comment;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface CommentMapper {
    int insertComment(Comment comment);

    List<Comment> getCommentsByPostId(@Param("postId") int postId);

    int getCount();

    List<Comment> getComments();

    int deleteCommentById(@Param("id") int id);

}