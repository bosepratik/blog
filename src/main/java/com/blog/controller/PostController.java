package com.blog.controller;

import com.blog.payload.PostDto;
import com.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts1")
public class PostController {
    @Autowired
    private PostService postService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deletePost(@PathVariable long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Pose Is Deleted",HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>>getAllPosts(
            @RequestParam(name = "pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(name = "pageSize",defaultValue = "3",required = false) int pageSize,
            @RequestParam(name = "sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(name = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        List<PostDto> postsDtos = postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(postsDtos,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<PostDto> updatePost(
            @RequestParam("postId") long postId,
            @RequestBody PostDto postDto
    ){
        PostDto dto = postService.updatePost(postId, postDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
}
