package com.prodapt.learningspring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.learningspring.entity.Comment;
import com.prodapt.learningspring.entity.CommentTag;
import com.prodapt.learningspring.entity.Post;
import com.prodapt.learningspring.entity.User;
import com.prodapt.learningspring.model.CommentDTO;
import com.prodapt.learningspring.repository.CommentTagRepository;

@Service
public class CommentTagService {

    @Autowired
    private UserService userService;

    private final CommentTagRepository commentTagRepository;

    @Autowired
    public CommentTagService(CommentTagRepository commentTagRepository) {
        this.commentTagRepository = commentTagRepository;
    }

    public void createCommentTagFromList(CommentDTO commentDTO, Comment comment){

        for(int i = 0; i < commentDTO.getTaggedUsers().size() ; i++){
            CommentTag commentTag = new CommentTag();
            commentTag.setComment(comment);
            commentTag.setTaggedUser(commentDTO.getTaggedUsers().get(i));
            commentTagRepository.save(commentTag);
        }
    } 


    public CommentTag createCommentTag(CommentTag commentTag) {
        return commentTagRepository.save(commentTag);
    }


    public List<CommentTag> getAllCommentTags() {
        return commentTagRepository.findAll();
    }

 
    public CommentTag getCommentTagById(int id) {
        return commentTagRepository.findById(id).orElse(null);
    }

    public CommentTag updateCommentTag(CommentTag commentTag) {
        return commentTagRepository.save(commentTag);
    }

    public void deleteCommentTag(int id) {
        commentTagRepository.deleteById(id);
    }
}