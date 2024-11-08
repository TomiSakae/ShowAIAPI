package com.tomisakae.showai.service;

import com.tomisakae.showai.entity.Tag;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface TagService {
    Tag createTag(String name);
    Tag getTagById(Long id);
    Tag getTagByName(String name);
    List<Tag> getAllTags();
    List<Tag> searchTags(String keyword);
    List<Tag> getPopularTags(int limit);
    Tag updateTag(Long id, String name);
    void deleteTag(Long id);
}
