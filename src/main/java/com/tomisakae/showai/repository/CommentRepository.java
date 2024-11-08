package com.tomisakae.showai.repository;

import com.tomisakae.showai.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPostId(Long postId, Pageable pageable);
    List<Comment> findByPostIdAndParentIsNull(Long postId); // Lấy comments gốc
    List<Comment> findByParentId(Long parentId); // Lấy replies của comment
    int countByPostId(Long postId);
}
