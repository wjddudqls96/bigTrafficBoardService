package com.example.boardserver.mapper;

import com.example.boardserver.dto.PostDto;
import com.example.boardserver.dto.request.PostSearchRequest;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostSearchMapper {
    public List<PostDto> selectPosts(PostSearchRequest postSearchRequest);
}
