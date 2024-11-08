package com.tomisakae.showai.controller;

import com.tomisakae.showai.dto.PostRequest;
import com.tomisakae.showai.entity.Post;
import com.tomisakae.showai.entity.User;
import com.tomisakae.showai.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final TagService tagService;

    @PostMapping
    public ResponseEntity<Post> createPost(
            @RequestHeader("Firebase-UID") String firebaseUid,
            @RequestBody PostRequest request) {
        User user = userService.getUserByFirebaseUid(firebaseUid);
        
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUser(user);
        post.setCategory(categoryService.getCategoryById(request.getCategoryId()));
        
        // Xử lý tags
        if (request.getTags() != null) {
            request.getTags().forEach(tagName -> {
                try {
                    post.getTags().add(tagService.getTagByName(tagName));
                } catch (Exception e) {
                    post.getTags().add(tagService.createTag(tagName));
                }
            });
        }
        
        return ResponseEntity.ok(postService.createPost(post));
    }

    @GetMapping
    public ResponseEntity<Page<Post>> getAllPosts(Pageable pageable) {
        return ResponseEntity.ok(postService.getAllPosts(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        postService.incrementViewCount(id);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<Post>> getPostsByCategory(
            @PathVariable Long categoryId,
            Pageable pageable) {
        return ResponseEntity.ok(postService.getPostsByCategory(categoryId, pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Post>> searchPosts(
            @RequestParam String keyword,
            Pageable pageable) {
        return ResponseEntity.ok(postService.searchPosts(keyword, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(
            @PathVariable Long id,
            @RequestHeader("Firebase-UID") String firebaseUid,
            @RequestBody PostRequest request) {
        Post existingPost = postService.getPostById(id);
        User user = userService.getUserByFirebaseUid(firebaseUid);
        
        if (!existingPost.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        existingPost.setTitle(request.getTitle());
        existingPost.setContent(request.getContent());
        existingPost.setCategory(categoryService.getCategoryById(request.getCategoryId()));
        
        return ResponseEntity.ok(postService.updatePost(id, existingPost));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long id,
            @RequestHeader("Firebase-UID") String firebaseUid) {
        Post post = postService.getPostById(id);
        User user = userService.getUserByFirebaseUid(firebaseUid);
        
        if (!post.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }
}
