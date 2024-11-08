package com.tomisakae.showai.controller;

import com.tomisakae.showai.dto.CommentRequest;
import com.tomisakae.showai.entity.Comment;
import com.tomisakae.showai.entity.User;
import com.tomisakae.showai.service.CommentService;
import com.tomisakae.showai.service.PostService;
import com.tomisakae.showai.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Comment> createComment(
            @PathVariable Long postId,
            @RequestHeader("Firebase-UID") String firebaseUid,
            @RequestBody CommentRequest request) {
        User user = userService.getUserByFirebaseUid(firebaseUid);
        
        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setUser(user);
        comment.setPost(postService.getPostById(postId));
        
        if (request.getParentId() != null) {
            comment.setParent(commentService.getCommentById(request.getParentId()));
        }
        
        return ResponseEntity.ok(commentService.createComment(comment));
    }

    @GetMapping
    public ResponseEntity<Page<Comment>> getComments(
            @PathVariable Long postId,
            Pageable pageable) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId, pageable));
    }

    @GetMapping("/root")
    public ResponseEntity<List<Comment>> getRootComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getRootCommentsByPostId(postId));
    }

    @GetMapping("/{commentId}/replies")
    public ResponseEntity<List<Comment>> getReplies(@PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.getRepliesByCommentId(commentId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable Long postId,
            @PathVariable Long id,
            @RequestHeader("Firebase-UID") String firebaseUid,
            @RequestBody CommentRequest request) {
        Comment comment = commentService.getCommentById(id);
        User user = userService.getUserByFirebaseUid(firebaseUid);
        
        if (!comment.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        return ResponseEntity.ok(commentService.updateComment(id, request.getContent()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long id,
            @RequestHeader("Firebase-UID") String firebaseUid) {
        Comment comment = commentService.getCommentById(id);
        User user = userService.getUserByFirebaseUid(firebaseUid);
        
        if (!comment.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }
}
