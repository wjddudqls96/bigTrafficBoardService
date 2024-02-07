package com.example.boardserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {
    private HttpStatus status;
    private String code;
    private String message;
    private T requestBody;
}
