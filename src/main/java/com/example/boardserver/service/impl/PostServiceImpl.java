package com.example.boardserver.service.impl;

import com.example.boardserver.dto.PostDto;
import com.example.boardserver.dto.UserDto;
import com.example.boardserver.mapper.PostMapper;
import com.example.boardserver.mapper.UserProfileMapper;
import com.example.boardserver.service.PostService;
import java.util.Date;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Override
    public void register(String id, PostDto postDto) {
        UserDto memberInfo = userProfileMapper.getUserProfile(id);
        postDto.setUserId(memberInfo.getId());
        postDto.setCreateTime(new Date());

        if(memberInfo != null) {
            postMapper.register(postDto);
        }
        else {
            log.error("register ERROR! {}", postDto);
            throw new RuntimeException("register ERROR! 게시글 등록 메서드를 확인해주세요" + postDto);
        }
    }

    @Override
    public List<PostDto> getMyPosts(int accountId) {
        return postMapper.selectMyPosts(accountId);
    }

    @Override
    public void updatePosts(PostDto postDto) {
        if (postDto != null && postDto.getId() != 0) {
            postMapper.updatePosts(postDto);
        }
        else {
            log.error("updatePosts ERROR! {}", postDto);
            throw new RuntimeException("updatePosts ERROR! 게시글 수정 메서드를 확인해주세요" + postDto);
        }
    }

    @Override
    public void deletePosts(int userId, int postId) {
        if(userId != 0 && postId != 0) {
            postMapper.deletePosts(postId);
        }
        else {
            log.error("deletePosts ERROR! {}", postId);
            throw new RuntimeException("deletePosts ERROR! 게시글 삭제 메서드를 확인해주세요" + postId);
        }
    }
}
