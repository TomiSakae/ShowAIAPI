package com.tomisakae.showai.repository;

import com.tomisakae.showai.entity.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
    boolean existsByName(String name);
    
    @Query("SELECT t FROM Tag t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Tag> searchTags(String keyword);
    
    @Query("SELECT t FROM Tag t JOIN t.posts p GROUP BY t ORDER BY COUNT(p) DESC")
    List<Tag> findPopularTags(Pageable pageable);
}
