package com.blog.service.impl;

import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CommentDto;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.service.CommentSrevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class CommentServiceImpl implements CommentSrevice {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not find with id: " + postId)
        );
        Comment comment = new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        CommentDto dto = new CommentDto();
        dto.setId(savedComment.getId());
        dto.setBody(savedComment.getBody());
        dto.setEmail(savedComment.getEmail());
        dto.setName(savedComment.getName());
        return dto;
    }

    @Override
    public void deleteComment(long commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment not found with id: " + commentId)
        );
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDto> commnetDtos = comments.stream().map(c -> maptoDto(c)).collect(Collectors.toList());
        return commnetDtos;
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentDto> dtos = comments.stream().map(c -> maptoDto(c)).collect(Collectors.toList());
        return dtos;
    }

    CommentDto maptoDto(Comment comment){
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setBody(comment.getBody());
        dto.setName(comment.getName());
        dto.setEmail(comment.getEmail());
        return dto;
    }
}
