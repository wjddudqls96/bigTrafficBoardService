package com.example.boardserver.service.impl;

import com.example.boardserver.dto.PostDto;
import com.example.boardserver.dto.request.PostSearchRequest;
import com.example.boardserver.mapper.PostSearchMapper;
import com.example.boardserver.service.PostSearchService;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PostSearchServiceImpl implements PostSearchService {

    @Autowired
    private PostSearchMapper postSearchMapper;

    @Cacheable(value = "getPosts", key = "'getPosts' + #postSearchRequest.getName() + #postSearchRequest.getCategoryId()")
    @Override
    public List<PostDto> getPosts(PostSearchRequest postSearchRequest) {
        List<PostDto> postDtoList = null;

        try {
            postDtoList = postSearchMapper.selectPosts(postSearchRequest);
        } catch (RuntimeException e) {
            log.error("selectPosts 메서드 실패", e.getMessage());
        }

        return postDtoList;
    }

    @Override
    public List<PostDto> getPostsByTagName(String tagName) {
        List<PostDto> postDtoList = null;

        try {
            postDtoList = postSearchMapper.getPostsByTag(tagName);
        } catch (RuntimeException e) {
            log.error("selectPosts 메서드 실패", e.getMessage());
        }

        return postDtoList;
    }
}
