package com.example.boardserver.service;

import com.example.boardserver.dto.PostDto;
import com.example.boardserver.dto.request.PostSearchRequest;
import java.util.List;

public interface PostSearchService {
    List<PostDto> getPosts(PostSearchRequest postSearchRequest);
    List<PostDto> getPostsByTagName(String tagName);
}
