package com.example.boardserver.service.impl;

import static com.example.boardserver.util.SHA256Util.encryptSHA256;

import com.example.boardserver.dto.UserDto;
import com.example.boardserver.exception.DuplicateIdException;
import com.example.boardserver.mapper.UserProfileMapper;
import com.example.boardserver.service.UserService;
import com.example.boardserver.util.SHA256Util;
import java.util.Date;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    private UserProfileMapper userProfileMapper;

    public UserServiceImpl(UserProfileMapper userProfileMapper) {
        this.userProfileMapper = userProfileMapper;
    }

    @Override
    public UserDto getUserInfo(String userId) {
        return userProfileMapper.getUserProfile(userId);
    }

    @Override
    public void register(UserDto userDTO) {
        boolean duplIdResult = isDuplicatedId(userDTO.getUserId());
        if (duplIdResult) {
            throw new DuplicateIdException("중복된 아이디입니다.");
        }
        userDTO.setCreateTime(new Date());
        userDTO.setPassword(SHA256Util.encryptSHA256(userDTO.getPassword()));
        int insertCount = userProfileMapper.register(userDTO);

        if (insertCount != 1) {
            log.error("insertMember ERROR! {}", userDTO);
            throw new RuntimeException(
                    "insertUser ERROR! 회원가입 메서드를 확인해주세요\n" + "Params : " + userDTO);
        }
    }
    @Override
    public UserDto login(String id, String password) {
        String cryptoPassword = SHA256Util.encryptSHA256(password);
        UserDto memberInfo = userProfileMapper.findByIdAndPassword(id, cryptoPassword);
        return memberInfo;
    }

    @Override
    public boolean isDuplicatedId(String id) {
        return userProfileMapper.idCheck(id) == 1;
    }

    @Override
    public void updatePassword(String id, String beforePassword, String afterPassword) {
        String cryptoPassword = SHA256Util.encryptSHA256(beforePassword);
        UserDto memberInfo = userProfileMapper.findByIdAndPassword(id, cryptoPassword);

        if (memberInfo != null) {
            memberInfo.setPassword(SHA256Util.encryptSHA256(afterPassword));
            int insertCount = userProfileMapper.updatePassword(memberInfo);
        } else {
            log.error("updatePasswrod ERROR! {}", memberInfo);
            throw new IllegalArgumentException("updatePasswrod ERROR! 비밀번호 변경 메서드를 확인해주세요\n" + "Params : " + memberInfo);
        }
    }

    @Override
    public void deleteId(String id, String passWord) {
        String cryptoPassword = SHA256Util.encryptSHA256(passWord);
        UserDto memberInfo = userProfileMapper.findByIdAndPassword(id, cryptoPassword);

        if (memberInfo != null) {
            userProfileMapper.deleteUserProfile(memberInfo.getUserId());
        } else {
            log.error("deleteId ERROR! {}", memberInfo);
            throw new RuntimeException("deleteId ERROR! id 삭제 메서드를 확인해주세요\n" + "Params : " + memberInfo);
        }
    }
}
