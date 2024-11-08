package com.tomisakae.showai.service;

import com.tomisakae.showai.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CommentService {
    Comment createComment(Comment comment);
    Comment getCommentById(Long id);
    Page<Comment> getCommentsByPostId(Long postId, Pageable pageable);
    List<Comment> getRootCommentsByPostId(Long postId);
    List<Comment> getRepliesByCommentId(Long commentId);
    Comment updateComment(Long id, String content);
    void deleteComment(Long id);
}
