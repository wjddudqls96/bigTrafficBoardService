package com.example.boardserver.controller;

import com.example.boardserver.aop.LoginCheck;
import com.example.boardserver.aop.LoginCheck.UserType;
import com.example.boardserver.dto.CategoryDto;
import com.example.boardserver.dto.CategoryDto.SortStatus;
import com.example.boardserver.service.impl.CategoryServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@Log4j2
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = UserType.ADMIN)
    public void registerCategory(String accountId, @RequestBody CategoryDto categoryDto) {
        categoryService.register(accountId, categoryDto);
    }

    @PatchMapping("{categoryId}")
    @LoginCheck(type = UserType.ADMIN)
    public void updateCategory(String accountId,
            @PathVariable(name = "categoryId") int categoryId,
            @RequestBody CategoryRequest categoryRequest) {
        CategoryDto categoryDto = new CategoryDto(categoryId, categoryRequest.getName(), SortStatus.NEWEST, 10, 1);
        categoryService.update(categoryDto);
    }

    @DeleteMapping("{categoryId}")
    @LoginCheck(type = UserType.ADMIN)
    public void deleteCategory(String accountId, @PathVariable(name = "categoryId") int categoryId) {
        categoryService.delete(categoryId);
    }

    // Request 객체
    @Getter
    @Setter
    private static class CategoryRequest {

        private int id;
        private String name;
    }
}
