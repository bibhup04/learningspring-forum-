package com.prodapt.learningspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.learningspring.entity.Post;
import com.prodapt.learningspring.repository.PostRepository;

import java.util.List;

@Service
public class PostService {

    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getPostsByUserId(int userId) {
        // Implement the logic to fetch posts by user ID from the repository
        return postRepository.findByAuthorId(userId);
    }

    public Post getPostsById(int postId) {
        return postRepository.findById(postId).get();
    }
}

