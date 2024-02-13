package com.example.boardserver.mapper;

import com.example.boardserver.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
    public int register(CommentDto commentDto);
    public void updateComment(CommentDto commentDto);
    public void deleteComment(int commentId);
}
