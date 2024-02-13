package com.example.boardserver.service.impl;

import com.example.boardserver.dto.CommentDto;
import com.example.boardserver.dto.PostDto;
import com.example.boardserver.dto.TagDto;
import com.example.boardserver.dto.UserDto;
import com.example.boardserver.mapper.CommentMapper;
import com.example.boardserver.mapper.PostMapper;
import com.example.boardserver.mapper.TagMapper;
import com.example.boardserver.mapper.UserProfileMapper;
import com.example.boardserver.service.PostService;
import java.util.Date;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private TagMapper tagMapper;

    @CacheEvict(value = "getProducts", allEntries = true)
    @Override
    public void register(String id, PostDto postDto) {
        UserDto memberInfo = userProfileMapper.getUserProfile(id);
        postDto.setUserId(memberInfo.getId());
        postDto.setCreateTime(new Date());

        if(memberInfo != null) {
            postMapper.register(postDto);
            Integer postId = postDto.getId();
            for(int i = 0; i < postDto.getTagDtoList().size(); i++) {
                TagDto tagDto = postDto.getTagDtoList().get(i);
                tagMapper.register(tagDto);
                Integer tagId = tagDto.getId();
                tagMapper.createPostTag(tagId, postId);
            }
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

    @Override
    public void registerComment(CommentDto commentDto) {
        if(commentDto.getPostId() != 0) {
            System.out.println(commentDto);
            commentMapper.register(commentDto);
        }
        else {
            log.error("registerComment {} ", commentDto);
            throw new RuntimeException("registerComment" + commentDto);
        }

    }

    @Override
    public void updateComment(CommentDto commentDto) {
        if(commentDto != null) {
            commentMapper.updateComment(commentDto);
        }
        else {
            log.error("updateComment error");
            throw new RuntimeException("updateComment");
        }
    }

    @Override
    public void deletePostComment(int userId, int commentId) {
        if(userId != 0 && commentId != 0) {
            commentMapper.deleteComment(commentId);
        }
        else {
            log.error("deletePostComment error! {}", commentId);
            throw new RuntimeException("deletePostComment" + commentId);
        }
    }

    @Override
    public void registerTag(TagDto tagDto) {
        if(tagDto != null) {
            tagMapper.register(tagDto);
        }
        else {
            log.error("registerTag error!");
            throw new RuntimeException("deleteComment");
        }
    }

    @Override
    public void updateTag(TagDto tagDto) {
        if(tagDto != null) {
            tagMapper.updateTag(tagDto);
        }
        else {
            log.error("updateTag error!");
            throw new RuntimeException("updateTag");
        }
    }

    @Override
    public void deletePostTag(int userId, int tagId) {
        if(userId != 0 && tagId != 0) {
            tagMapper.deleteTag(tagId);
        }
        else {
            log.error("deletePostTag error! {}", tagId);
            throw new RuntimeException("deletePostTag" + tagId);
        }
    }
}
