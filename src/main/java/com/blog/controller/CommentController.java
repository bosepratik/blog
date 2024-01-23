package com.blog.controller;

import com.blog.payload.CommentDto;
import com.blog.service.CommentSrevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentSrevice commentService;
    @PostMapping
    public ResponseEntity<CommentDto> createComment(
            @RequestParam("postId") long postId,
            @RequestBody CommentDto commentDto
    ){
        CommentDto dto = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable long commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<>("Comment is deleted!!!",HttpStatus.OK);
    }
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentByPostId(@PathVariable long postId){
        List<CommentDto> commentDto = commentService.getCommentByPostId(postId);
        return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments(){
        List<CommentDto> commentDtos = commentService.getAllComments();
        return new ResponseEntity<>(commentDtos,HttpStatus.OK);
    }
}
