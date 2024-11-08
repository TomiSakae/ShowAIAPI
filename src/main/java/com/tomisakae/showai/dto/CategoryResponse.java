package com.tomisakae.showai.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
}
