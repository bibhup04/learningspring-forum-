package com.prodapt.learningspring.aspect;


import java.util.Optional;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.prodapt.learningspring.controller.binding.AddPostForm;
import com.prodapt.learningspring.controller.exception.ResourceNotFoundException;
import com.prodapt.learningspring.entity.Post;
import com.prodapt.learningspring.entity.User;
import com.prodapt.learningspring.repository.CommentRepository;
import com.prodapt.learningspring.repository.PostRepository;
import com.prodapt.learningspring.repository.UserRepository;
import com.prodapt.learningspring.service.NotificationService;

@Aspect
@Component
public class LogSuccessfulExecutionAspect {
	User sourceUser;
	User targetUser;
	Post post;
	String notificationType = "";
	String name = "";
	String message ="";

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	@AfterReturning(value = "@annotation(com.prodapt.learningspring.aspect.LogNotification)", returning = "result")
	public void logSuccessfulExecution(JoinPoint joinPoint, Object result) throws ResourceNotFoundException {
		
		String methodName = joinPoint.getSignature().getName();
		
		System.out.println("Method '" + methodName + "' was successfully executed.");
		
		Object[] args = joinPoint.getArgs();

		if (methodName.equals("addNewPost") && (args != null)) {
				AddPostForm addPostForm = (AddPostForm) args[0];
				Object userDetails = args[2];
				Optional<Post> lastPostId = postRepository.findLastPostId();
				UserDetails ud = (UserDetails) userDetails;
				
				sourceUser = userRepository.findById(addPostForm.getUserId()).get();
				post = lastPostId.get();
				notificationType = "POST";
				message =  "You, added a post (" + addPostForm.getContent() + ").";
		
		}

		else if (methodName.equals("submitComment") && (args != null && args.length >= 2)) {
				Object postId = args[0];
				Object comment = args[1];
				Object thirdParam = args[2];
				
				UserDetails ud = (UserDetails) thirdParam;

				sourceUser = postRepository.findById((int) postId).get().getAuthor();
				targetUser = userRepository.findByName(ud.getUsername()).get();

				if (sourceUser.getId() == targetUser.getId()) {
					name = "You";
				} else {
					name = ud.getUsername();
				}
				
				post = postRepository.findById((int) postId).get();
				notificationType = "COMMENT";
				message = name + ", commented on your post (" + comment.toString() + ").";
		}

		else if (methodName.equals("postLike") && (args != null && args.length >= 2)) {
				Object postId = args[0];
				Object userDetails = args[3];
				UserDetails ud = (UserDetails) userDetails;

				sourceUser = postRepository.findById((int) postId).get().getAuthor();
				targetUser = userRepository.findByName(ud.getUsername()).get();
					
				if (sourceUser.getId() == targetUser.getId()) {
					name = "You";
				} else {
					name = ud.getUsername();
				}
				
				post = postRepository.findById((int) postId).get();
				notificationType = "LIKE";
				message = name + ", liked your post ("+ postRepository.findById((int) postId).get().getContent() + ").";

		}

		else if (methodName.equals("postReply") && (args != null && args.length >= 4)) {
				Object postId = args[0];
				Object commentId = args[1];
				Object commenterId = args[3];
				Object userDetails = args[4];
				UserDetails ud = (UserDetails) userDetails;
				
				sourceUser = commentRepository.findById((int) commentId).get().getUser();
				targetUser = userRepository.findByName(ud.getUsername()).get();

				if (targetUser.getId() == ((int) commenterId)) {
					name = "You";
				} else {
					name = ud.getUsername();
				}
				post = postRepository.findById((int) postId).get();
				notificationType = "REPLY";
				message = name + " replied on your comment ("+ commentRepository.findById((int) commentId).get().getCommentText() + ").";
		}
		else{
			throw new ResourceNotFoundException("method doesnot exist or arguments doesn't match");
		}

		notificationService.createNotification(sourceUser, post, notificationType, message);
	}
}
