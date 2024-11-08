package com.tomisakae.showai.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private UserResponse user;
    private CategoryResponse category;
    private Set<TagResponse> tags;
    private int viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
