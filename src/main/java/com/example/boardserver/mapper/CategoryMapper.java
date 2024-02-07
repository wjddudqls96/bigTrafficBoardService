package com.example.boardserver.mapper;

import com.example.boardserver.dto.CategoryDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
    public int register(CategoryDto categoryDto);
    public int updateCategory(CategoryDto categoryDto);
    public void deleteCategory(int categoryId);
}
