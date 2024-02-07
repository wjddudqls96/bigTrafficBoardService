package com.example.boardserver.service;

import com.example.boardserver.dto.CategoryDto;

public interface CategoryService {
    void register(String accountId, CategoryDto categoryDto);
    void update(CategoryDto categoryDto);
    void delete(int categoryId);
}
