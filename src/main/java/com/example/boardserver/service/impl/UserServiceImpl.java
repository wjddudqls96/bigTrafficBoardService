package com.example.boardserver.service.impl;

import com.example.boardserver.dto.UserDto;
import com.example.boardserver.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public void register(UserDto userProfile) {

    }

    @Override
    public UserDto login(String id, String password) {
        return null;
    }

    @Override
    public boolean isDuplicated(String id) {
        return false;
    }

    @Override
    public UserDto getUserInfo(String userId) {
        return null;
    }

    @Override
    public void updatePassword(String id, String beforePassword, String afterPassword) {

    }

    @Override
    public void deleteId(String id, String password) {

    }
}
