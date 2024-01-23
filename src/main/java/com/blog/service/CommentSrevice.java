package com.blog.service;

import com.blog.payload.CommentDto;

import java.util.List;

public interface CommentSrevice {
    public CommentDto createComment(long postId, CommentDto commentDto);

    void deleteComment(long commentId);

    List<CommentDto> getCommentByPostId(long postId);

    List<CommentDto> getAllComments();
}
