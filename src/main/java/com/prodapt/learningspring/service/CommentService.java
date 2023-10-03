package com.prodapt.learningspring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.learningspring.entity.Comment;
import com.prodapt.learningspring.entity.User;
import com.prodapt.learningspring.model.CommentDTO;
import com.prodapt.learningspring.repository.CommentRepository;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentTagService commentTagService;

    @Autowired
    private PostService postService;

    public List<Comment> findByPostId(int postId) {
        return commentRepository.findByPostId(postId);
    }

    public Comment getCommentById(int commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    // public List<Comment> findTopLevelCommentsByPostId(int postId) {
    //     return commentRepository.findTopLevelCommentsByPostId(postId);
    // }

    public Comment createComment(CommentDTO commentDTO, User user) {
        Comment comment = new Comment();
        comment.setCommentText(commentDTO.getCommentText());
        comment.setPost(postService.getPostsById(commentDTO.getPostId()));
        comment.setUser(user);

        //commentTagService.createCommentTagFromList(commentDTO, comment);
        
        return commentRepository.save(comment);
    }

   
}
