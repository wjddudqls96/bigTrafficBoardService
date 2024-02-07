package com.example.boardserver.mapper;

import com.example.boardserver.dto.PostDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {
    public int register(PostDto postDto);
    public List<PostDto> selectMyPosts(int accountId);
    public int updatePosts(PostDto postDto);
    public void deletePosts(int postId);
}
