package com.example.boardserver.dto.response;

import com.example.boardserver.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponse {
    private UserDto userDTO;
}
