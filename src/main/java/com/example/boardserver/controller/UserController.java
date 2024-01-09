package com.example.boardserver.controller;

import com.example.boardserver.dto.UserDto;
import com.example.boardserver.dto.request.UserDeleteId;
import com.example.boardserver.dto.request.UserLoginRequest;
import com.example.boardserver.dto.request.UserUpdatePasswordRequest;
import com.example.boardserver.dto.response.LoginResponse;
import com.example.boardserver.dto.response.UserInfoResponse;
import com.example.boardserver.service.UserService;
import com.example.boardserver.service.impl.UserServiceImpl;
import com.example.boardserver.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Log4j2
public class UserController {

    private final UserServiceImpl userService;
    private static final ResponseEntity<LoginResponse> FAIL_RESPONSE = new ResponseEntity<LoginResponse>(HttpStatus.BAD_REQUEST);
    private static LoginResponse loginResponse;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @PostMapping("sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody UserDto userDto) {
        System.out.println(userDto);
        if (UserDto.hasNullDataBeforeSignup(userDto)) {
            throw new NullPointerException("회원가입시 필수 데이터를 모두 입력해야 합니다.");
        }
        userService.register(userDto);
    }

    @PostMapping("sign-in")
    public HttpStatus login(@RequestBody UserLoginRequest loginRequest,
            HttpSession session) {
        ResponseEntity<LoginResponse> responseEntity = null;
        String id = loginRequest.getUserId();
        String password = loginRequest.getPassword();
        UserDto userInfo = userService.login(id, password);

        if (userInfo == null) {
            return HttpStatus.NOT_FOUND;
        } else if (userInfo != null) {
            loginResponse = LoginResponse.success(userInfo);
            if (userInfo.getStatus() == (UserDto.Status.ADMIN))
                SessionUtil.setLoginAdminId(session, id);
            else
                SessionUtil.setLoginMemberId(session, id);

            responseEntity = new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
        } else {
            throw new RuntimeException("Login Error! 유저 정보가 없거나 지워진 유저 정보입니다.");
        }

        return HttpStatus.OK;
    }

    @GetMapping("my-info")
    public UserInfoResponse memberInfo(HttpSession session) {
        String id = SessionUtil.getLoginMemberId(session);
        if (id == null) {
            id = SessionUtil.getLoginAdminId(session);
        }

        System.out.println(id);
        UserDto memberInfo = userService.getUserInfo(id);
        return new UserInfoResponse(memberInfo);
    }

    @PutMapping("logout")
    public void logout(String accountId, HttpSession session) {
        SessionUtil.clear(session);
    }

    @PatchMapping("password")
    public ResponseEntity<LoginResponse> updateUserPassword(@RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest,
            HttpSession session) {
        ResponseEntity<LoginResponse> responseEntity = null;
        String Id = SessionUtil.getLoginMemberId(session);
        String beforePassword = userUpdatePasswordRequest.getBeforePassword();
        String afterPassword = userUpdatePasswordRequest.getAfterPassword();

        try {
            userService.updatePassword(Id, beforePassword, afterPassword);
            ResponseEntity.ok(new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK));
        } catch (IllegalArgumentException e) {
            log.error("updatePassword 실패", e);
            responseEntity = FAIL_RESPONSE;
        }
        return responseEntity;
    }

    @DeleteMapping
    public ResponseEntity<LoginResponse> deleteId(@RequestBody UserDeleteId userDeleteId,
            HttpSession session) {
        ResponseEntity<LoginResponse> responseEntity = null;
        String Id = SessionUtil.getLoginMemberId(session);

        try {
            userService.deleteId(Id, userDeleteId.getPassword());
            responseEntity = new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.info("deleteID 실패");
            responseEntity = FAIL_RESPONSE;
        }
        return responseEntity;
    }
}
