package com.example.boardserver.mapper;

import com.example.boardserver.dto.TagDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagMapper {
    public int register(TagDto tagDto);
    public void updateTag(TagDto tagDto);
    public void deleteTag(int tagId);
    public void createPostTag(Integer tagId, Integer postId);
}
