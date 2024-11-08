package com.tomisakae.showai.dto;

import lombok.Data;

@Data
public class CommentRequest {
    private String content;
    private Long parentId; // null nếu là root comment
}
