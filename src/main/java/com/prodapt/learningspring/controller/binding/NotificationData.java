package com.prodapt.learningspring.controller.binding;

import com.prodapt.learningspring.entity.Post;
import com.prodapt.learningspring.entity.User;
import lombok.Data;

@Data
public class NotificationData {
    private User user;
    private Post post;
    private String eventType;
    private String message;
    
    // public NotificationData(User user, Post post, String eventType, String message){
    //     this.user = user;
    //     this.post = post;
    //     this.eventType = eventType;
    //     this.message = message;
    // }
}
