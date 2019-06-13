package com.example.demo.repository;

import com.example.demo.model.Comment;
import com.example.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT count(*) FROM Comment c WHERE LOWER(c.createdBy.username) = LOWER(:username) and c.id = :commentId")
    public Integer belongsTo(@Param("commentId") Long commentId, @Param("username") String username);
    public List<Comment> getCommentsByOrder(Order order);
}
