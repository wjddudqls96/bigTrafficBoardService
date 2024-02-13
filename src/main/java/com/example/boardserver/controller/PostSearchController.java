package com.example.boardserver.controller;

import com.example.boardserver.dto.PostDto;
import com.example.boardserver.dto.request.PostSearchRequest;
import com.example.boardserver.service.impl.PostSearchServiceImpl;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@Log4j2
@RequiredArgsConstructor
public class PostSearchController {
    private final PostSearchServiceImpl postSearchService;

    @PostMapping
    public PostSearchResponse search(@RequestBody PostSearchRequest postSearchRequest) {
        List<PostDto> postDtoList = postSearchService.getPosts(postSearchRequest);
        return new PostSearchResponse(postDtoList);
    }

    @GetMapping
    public PostSearchResponse searchByTagName(@RequestParam(name = "tagName") String tagName) {
        System.out.println(tagName);
        List<PostDto> postDtoList = postSearchService.getPostsByTagName(tagName);
        return new PostSearchResponse(postDtoList);
    }

    @Getter
    @AllArgsConstructor
    private static class PostSearchResponse {
        private List<PostDto> postDtoList;
    }
}
