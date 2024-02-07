package com.example.boardserver.controller;

import com.example.boardserver.aop.LoginCheck;
import com.example.boardserver.aop.LoginCheck.UserType;
import com.example.boardserver.dto.PostDto;
import com.example.boardserver.dto.UserDto;
import com.example.boardserver.dto.response.CommonResponse;
import com.example.boardserver.service.impl.PostServiceImpl;
import com.example.boardserver.service.impl.UserServiceImpl;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@Log4j2
@RequiredArgsConstructor
public class PostController {

    private final UserServiceImpl userService;
    private final PostServiceImpl postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = UserType.USER)
    public ResponseEntity<CommonResponse<PostDto>> registerPost(String accountId,
            @RequestBody PostDto postDto) {
        UserDto memberInfo = userService.getUserInfo(accountId);
        postDto.setUserId(memberInfo.getId());
        postService.register(accountId, postDto);
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, "SUCCESS", "registerPost",
                postDto);
        return ResponseEntity.ok(commonResponse);
    }

    @GetMapping("my-posts")
    @LoginCheck(type = UserType.USER)
    public ResponseEntity<CommonResponse<List<PostDto>>> myPostInfo(String accountId) {
        UserDto memberInfo = userService.getUserInfo(accountId);
        List<PostDto> postDtoList = postService.getMyPosts(memberInfo.getId());
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, "SUCCESS", "myPostInfo",
                postDtoList);
        return ResponseEntity.ok(commonResponse);
    }

    @PatchMapping("{postId}")
    @LoginCheck(type = UserType.USER)
    public ResponseEntity<CommonResponse<PostDto>> updatePosts(String accountId,
            @PathVariable(name = "postId") int postId, @RequestBody PostRequest postReqeust) {

        UserDto memberInfo = userService.getUserInfo(accountId);
        postReqeust.setUserId(memberInfo.getId());
        PostDto postDto = PostDto.builder()
                .id(postId)
                .name(postReqeust.getName())
                .contents(postReqeust.getContents())
                .views(postReqeust.getViews())
                .categoryId(postReqeust.getCategoryId())
                .userId(postReqeust.getUserId())
                .fileId(postReqeust.getFileId())
                .updateTime(new Date())
                .build();

        postService.updatePosts(postDto);


        CommonResponse<PostDto> commonResponse = new CommonResponse(HttpStatus.OK, "SUCCESS", "updatePosts",
                postDto);
        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping("{postId}")
    @LoginCheck(type = UserType.USER)
    public ResponseEntity<CommonResponse<PostDeleteRequest>> deletePosts(String accountId,
            @PathVariable(name = "postId") int postId, @RequestBody PostDeleteRequest postDeleteRequest) {
        UserDto memberInfo = userService.getUserInfo(accountId);
        postService.deletePosts(memberInfo.getId(), postId);

        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, "SUCCESS", "deletePosts",
                postDeleteRequest);
        return ResponseEntity.ok(commonResponse);
    }

    // Response 객체
    @Getter
    @AllArgsConstructor
    private static class PostResponse {

        private List<PostDto> postDtos;
    }

    // Reqeust 객체
    @Setter
    @Getter
    private static class PostRequest {

        private String name;
        private String contents;
        private int views;
        private int categoryId;
        private int userId;
        private int fileId;
        private Date updateTime;
    }

    @Setter
    @Getter
    private static class PostDeleteRequest {

        private int id;
        private int accountId;
    }
}
