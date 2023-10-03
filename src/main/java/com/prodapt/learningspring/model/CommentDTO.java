package com.prodapt.learningspring.model;

import java.util.List;

import com.prodapt.learningspring.entity.User;

import lombok.Data;

@Data
public class CommentDTO {
    private String commentText;
    private List<User> taggedUsers;
    private int postId;
    
}
