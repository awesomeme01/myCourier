package com.example.demo.controller;

import com.example.demo.model.Comment;
import com.example.demo.model.Response;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/comment")
public class CommentController {
    @Autowired
    CommentService commentService;
    @GetMapping(path = "/getAll")
    public Response getAll(){
        return new Response(true, "All comments", commentService.getAllComments());
    }
    @GetMapping(path = "/getByOrderId/{id}")
    public Response getAllByOrderId(@PathVariable Long id){
        return new Response(true, "All comments for Order with id = " + id, commentService.getAllCommentsByOrderId(id));
    }
    @GetMapping(path = "/getByUserId/{id}")
    public Response getAllByUserId(@PathVariable Long id) {
        return new Response(true, "All comments by User with id = " + id, commentService.getAllCommentsByUser(id));
    }
    @GetMapping(path = "/getById")
    public Response getById(@PathVariable Long id){
        return new Response(true, "Comment with id = " + id , commentService.getCommentById(id));
    }
    @PostMapping(path = "/create")
    public Response create(@RequestBody Comment comment){
        return new Response(true, "Comment created successfully",commentService.createComment(comment));
    }
    @DeleteMapping(path = "/delete/{id}")
    public Response deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
        return new Response(true, "Comment was deleted successfully!", null);
    }
}
