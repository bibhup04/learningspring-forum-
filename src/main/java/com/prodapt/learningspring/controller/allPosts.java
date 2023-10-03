package com.prodapt.learningspring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prodapt.learningspring.controller.exception.ResourceNotFoundException;
import com.prodapt.learningspring.entity.Comment;
import com.prodapt.learningspring.entity.Post;
import com.prodapt.learningspring.entity.User;
import com.prodapt.learningspring.model.CommentDTO;
import com.prodapt.learningspring.model.PostDetailResponse;
import com.prodapt.learningspring.model.PostIdRequest;
import com.prodapt.learningspring.repository.CommentRepository;
import com.prodapt.learningspring.repository.LikeCRUDRepository;
import com.prodapt.learningspring.service.CommentService;
import com.prodapt.learningspring.service.CommentTagService;
import com.prodapt.learningspring.service.PostService;
import com.prodapt.learningspring.service.UserService;

@RestController
@RequestMapping("/forum/post")
@CrossOrigin
public class allPosts {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeCRUDRepository likeCRUDRepository;

    @Autowired
    private CommentTagService commentTagService;


    // @PostMapping("/user")
    // public ResponseEntity<List<Post>> getPostsByUserId(@RequestBody PostIdRequest postIdRequest) {
    //     int postId = postIdRequest.getPostId();
    //     var displayPost = postService.getPostsByUserId(postId);
    //     return ResponseEntity.status(HttpStatus.OK).body(displayPost);
    // }

    @GetMapping("/show/{id}")
    public ResponseEntity<PostDetailResponse> getPostById(@PathVariable int id){
        //int postId = postIdRequest.getPostId();
        var postOptional = postService.getPostsById(id);
        //return ResponseEntity.status(HttpStatus.OK).body(displayPost);

         List<Comment> commentList = commentService.findByPostId(id);
        //List<Comment> commentList = commentService.findTopLevelCommentsByPostId(id);
        int numLikes = likeCRUDRepository.countByLikeIdPost(postOptional);

        PostDetailResponse response = new PostDetailResponse(
            postOptional,
            postOptional.getAuthor().getName(),
            commentList,
            numLikes
        ); 

        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/user")
    public ResponseEntity<List<User>> showAllUser(){
        List<User> user = userService.getAllUsers();
        return ResponseEntity.ok(user);
    }


    @PostMapping("/addComment")
    public ResponseEntity<String> addComment(@RequestBody CommentDTO commentDTO) {
        
        User user = userService.findUserByName("bibhu").get();
        System.out.println(commentDTO.getTaggedUsers());
        Comment createdComment = commentService.createComment(commentDTO , user);
        commentTagService.createCommentTagFromList(commentDTO, createdComment);

        if (createdComment != null) {
            return ResponseEntity.ok("Comment created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create comment");
        }
    }
    
}
