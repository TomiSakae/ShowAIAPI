package com.tomisakae.showai.mapper;

import com.tomisakae.showai.dto.PostResponse;
import com.tomisakae.showai.dto.UserResponse;
import com.tomisakae.showai.dto.CategoryResponse;
import com.tomisakae.showai.dto.TagResponse;
import com.tomisakae.showai.entity.Post;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class PostMapper {
    public PostResponse toResponse(Post post) {
        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setContent(post.getContent());
        
        UserResponse userResponse = new UserResponse();
        userResponse.setId(post.getUser().getId());
        userResponse.setUsername(post.getUser().getUsername());
        response.setUser(userResponse);
        
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(post.getCategory().getId());
        categoryResponse.setName(post.getCategory().getName());
        categoryResponse.setDescription(post.getCategory().getDescription());
        response.setCategory(categoryResponse);
        
        response.setTags(post.getTags().stream()
            .map(tag -> {
                TagResponse tagResponse = new TagResponse();
                tagResponse.setId(tag.getId());
                tagResponse.setName(tag.getName());
                return tagResponse;
            })
            .collect(Collectors.toSet()));
            
        response.setViewCount(post.getViewCount());
        response.setCreatedAt(post.getCreatedAt());
        response.setUpdatedAt(post.getUpdatedAt());
        
        return response;
    }
}
