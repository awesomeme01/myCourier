package com.example.demo.controller;

import com.example.demo.model.Comment;
import com.example.demo.Helper.Response;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.NoSuchElementException;

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
        if(!orderRepository.existsById(id)){
            return new Response(false, "There's no such order with id = " + id, null);
        }
        return new Response(true, "All comments for Order with id = " + id, commentService.getAllCommentsByOrderId(id));

//        return new Response(false, "Current user doesn't have access to this order", null);
    }
    @Secured("ROLE_ADMIN")
    @GetMapping(path = "/getByUserId/{id}")
    public Response getAllByUserId(@PathVariable Long id) {
        if(!userRepository.existsById(id)){
            return new Response(false, "There's no such user with id = " + id, null);
        }
        return new Response(true, "All comments by User with id = " + id, commentService.getAllCommentsByUser(id));
    }
    @Secured("ROLE_USER")
    @GetMapping(path = "/getById/{id}")
    public Response getById(@PathVariable Long id){
        try{
            return new Response(true, "Comment with id = " + id , commentService.getCommentById(id));
        }
        catch (NoSuchElementException ex){
            return new Response(false, "Comment with id = " + id + " doesn't exist", null);
        }
    }
    @Secured("ROLE_USER")
    @PostMapping(path = "/createForOrder/{id}")
    public Response create(Principal principal,@RequestBody Comment comment, @PathVariable Long id){
        if(!orderRepository.existsById(id)){
            return new Response(false, "There is no such order with id = " + id, null);
        }
        User user = userRepository.findByUsername(principal.getName());
        Order order = orderRepository.findById(id).get();
        if(order.getOrderedBy().equals(user)){
            if(order.getCourier()!=null){
                comment.setCreatedBy(user);
                comment.setOrder(order);
            }
            else{
                return new Response(false, "This order doesn't have a courier", order);
            }
        }
        else {
            return new Response(false, "You don't have access to this order!", null);
        }
        return new Response(true, "Comment created successfully",commentService.createComment(comment));
    }
    @Secured("ROLE_USER")
    @PutMapping(path = "/editMessage")
    public Response edit(Principal principal, @RequestBody Comment comment){
        if(!commentRepository.existsById(comment.getId())){
            return new Response(false, "There is no such comment with id = "+ comment.getId(), null);
        }
        if(commentRepository.belongsTo(comment.getId(),principal.getName())==0){
            return new Response(false, "This comment doesn't belong to the current user", null);
        }
        return new Response(true, "Comment edited successfully", commentService.editComment(comment));
    }
    @Secured("ROLE_USER")
    @DeleteMapping(path = "/delete/{id}")
    public Response deleteComment(Principal principal,@PathVariable Long id){
        if(!commentRepository.existsById(id)){
            return new Response(false, "There is no such comment with id = "+ id ,null);
        }
        if(commentRepository.belongsTo(id,principal.getName())==0){
            return new Response(false, "This comment doesn't belong to the current user", null);
        }
        commentService.deleteComment(id);
        return new Response(true, "Comment was deleted successfully!", null);
    }
    @Secured("ROLE_ADMIN")
    @DeleteMapping(path = "/forceDelete/{id}")
    public Response forceDeleteComment(@PathVariable Long id){
        try{
            commentService.deleteComment(id);
            return new Response(true, "The comment was forceDeleted!", null);
        }
        catch (NoSuchElementException ex){
            return new Response(false, "There is no such comment with id = " + id , null);
        }
    }
}
