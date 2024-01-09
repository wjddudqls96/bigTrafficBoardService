package com.example.boardserver.dto.response;

import com.example.boardserver.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class LoginResponse {
    enum LoginStatus {
        SUCCESS, FAIL, DELETED
    }

    @NonNull
    private LoginStatus result;
    private UserDto userDTO;

    private static final LoginResponse FAIL = new LoginResponse(LoginStatus.FAIL);

    public static LoginResponse success(UserDto userDTO) {
        return new LoginResponse(LoginStatus.SUCCESS, userDTO);
    }
}
