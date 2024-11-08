package com.tomisakae.showai.service;

import com.tomisakae.showai.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Post createPost(Post post);
    Post getPostById(Long id);
    Page<Post> getAllPosts(Pageable pageable);
    Page<Post> getPostsByCategory(Long categoryId, Pageable pageable);
    Page<Post> getPostsByUser(Long userId, Pageable pageable);
    Page<Post> searchPosts(String keyword, Pageable pageable);
    Post updatePost(Long id, Post post);
    void deletePost(Long id);
    void incrementViewCount(Long id);
}
