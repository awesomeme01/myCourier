package com.example.demo.service;

import com.example.demo.model.Comment;
import com.example.demo.model.User;

import java.security.Principal;
import java.util.List;

public interface CommentService {
    List<Comment> getAllComments();
    List<Comment> getCommentHistory(User user);
    List<Comment> getAllCommentsByOrderId(Long id);
    List<Comment> getAllCommentsByUser(Long id);
    Comment getCommentById(Long id);
    Comment createComment(Comment comment);
    Comment editComment(Comment comment);
    void deleteComment(Long id);

}
