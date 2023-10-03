package com.prodapt.learningspring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.prodapt.learningspring.entity.Comment;


public interface CommentRepository extends CrudRepository<Comment, Integer>{
    

    List<Comment> findAll();

    List<Comment> findByPostId(int postId);

    // @Query("SELECT c FROM Comment c WHERE c.post_id = ?1 AND c.parent_id IS NULL")
    // List<Comment> findTopLevelCommentsByPostId( int postId);
}
