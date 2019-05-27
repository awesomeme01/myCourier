package com.example.demo.service;

import com.example.demo.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getAllComments();
    List<Comment> getAllCommentsByOrderId(Long id);
    List<Comment> getAllCommentsByUser(Long id);
    Comment getCommentById(Long id);
    Comment createComment(Comment comment);
    void deleteComment(Long id);

}
