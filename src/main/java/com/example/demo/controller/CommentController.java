package com.example.demo.controller;

import com.example.demo.model.Comment;
import com.example.demo.model.Response;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/comment")
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Secured("ROLE_ADMIN")
    @GetMapping(path = "/getAll")
    public Response getAll(){
        return new Response(true, "All comments", commentService.getAllComments());
    }
    @Secured("ROLE_USER")
    @GetMapping(path = "/commentHistory")
    public Response getMyHistory(Principal principal){
        return new Response(true, "All comments created by user '" + principal.getName()+ "'", commentService.getCommentHistory(userRepository.findByUsername(principal.getName())));
    }
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping(path = "/getByOrderId/{id}")
    public Response getAllByOrderId(Principal principal, @PathVariable Long id){
        if(orderRepository.belongsTo(id,principal.getName())==1 || orderRepository.findById(id).get().getCourier().getUser().getUsername().equals(principal.getName())){
            return new Response(true, "All comments for Order with id = " + id, commentService.getAllCommentsByOrderId(id));
        }
        return new Response(false, "Current user doesn't have access to this order", null);
    }
    @Secured("ROLE_ADMIN")
    @GetMapping(path = "/getByUserId/{id}")
    public Response getAllByUserId(@PathVariable Long id) {
        return new Response(true, "All comments by User with id = " + id, commentService.getAllCommentsByUser(id));
    }
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping(path = "/getById/{id}")
    public Response getById(@PathVariable Long id){
        return new Response(true, "Comment with id = " + id , commentService.getCommentById(id));
    }
    @Secured("ROLE_USER")
    @PostMapping(path = "/create")
    public Response create(@RequestBody Comment comment){
        return new Response(true, "Comment created successfully",commentService.createComment(comment));
    }
    @Secured("ROLE_USER")
    @PutMapping(path = "/editMessage")
    public Response edit(Principal principal, @RequestBody Comment comment){
        if(commentRepository.belongsTo(comment.getId(),principal.getName())==0){
            return new Response(false, "This comment doesn't belong to the current user", null);
        }
        return new Response(true, "Comment edited successfully", commentService.editComment(comment));
    }
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @DeleteMapping(path = "/delete/{id}")
    public Response deleteComment(Principal principal,@PathVariable Long id){
        if(commentRepository.belongsTo(id,principal.getName())==0){
            return new Response(false, "This comment doesn't belong to the current user", null);
        }
        commentService.deleteComment(id);
        return new Response(true, "Comment was deleted successfully!", null);
    }
}
