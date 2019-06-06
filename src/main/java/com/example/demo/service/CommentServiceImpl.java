package com.example.demo.service;

import com.example.demo.model.Comment;
import com.example.demo.model.Courier;
import com.example.demo.model.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.CourierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    CommentRepository commentRepository;
    CourierRepository courierRepository;

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comment editComment(Comment comment) {
        Comment newComment = commentRepository.findById(comment.getId()).get();
        newComment.setCommentText(comment.getCommentText());
        return commentRepository.save(newComment);
    }

    @Override
    public List<Comment> getCommentHistory(User user) {
//        if(user == null){
//            return null;
//        }
        return commentRepository.findAll().stream().filter(x -> x.getCreatedBy().equals(user)).collect(Collectors.toList());
    }

    @Override
    public List<Comment> getAllCommentsByOrderId(Long id) {
        return commentRepository.findAll().stream().filter(x->x.getOrder().getId().equals(id)).collect(Collectors.toList());
    }

    @Override
    public List<Comment> getAllCommentsByUser(Long id) {
        return commentRepository.findAll().stream().filter(x->x.getCreatedBy().getId().equals(id)).collect(Collectors.toList());
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).get();
    }

    @Override
    public Comment createComment(Comment comment) {
        if(comment.getFeedbackValue()!=0){
            if(comment.getFeedbackValue()>0){
                Courier courier = comment.getOrder().getCourier();
                int initial = comment.getOrder().getCourier().getRating();
                int oneMore = comment.getFeedbackValue();

                courier.setFeedbackCount(comment.getOrder().getCourier().getFeedbackCount()+1);
                courier.setRating(initial+oneMore/courier.getFeedbackCount());
                courierRepository.save(courier);
            }
        }
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).get();
        if(comment.getFeedbackValue()!=0){
            Courier courier = comment.getOrder().getCourier();
            courier.setFeedbackCount(courier.getFeedbackCount()-1);
            courier.setRating(courier.getRating()-comment.getFeedbackValue()/courier.getFeedbackCount());
            courierRepository.save(courier);
        }
    }
}
