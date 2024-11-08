package com.tomisakae.showai.service.impl;

import com.tomisakae.showai.entity.Tag;
import com.tomisakae.showai.repository.TagRepository;
import com.tomisakae.showai.service.TagService;
import com.tomisakae.showai.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public Tag createTag(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTagById(Long id) {
        return tagRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tag not found"));
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name)
            .orElseThrow(() -> new ResourceNotFoundException("Tag not found"));
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> searchTags(String keyword) {
        return tagRepository.searchTags(keyword);
    }

    @Override
    public List<Tag> getPopularTags(int limit) {
        return tagRepository.findPopularTags(PageRequest.of(0, limit));
    }

    @Override
    @Transactional
    public Tag updateTag(Long id, String name) {
        Tag tag = getTagById(id);
        tag.setName(name);
        return tagRepository.save(tag);
    }

    @Override
    public void deleteTag(Long id) {
        Tag tag = getTagById(id);
        tagRepository.delete(tag);
    }
}
