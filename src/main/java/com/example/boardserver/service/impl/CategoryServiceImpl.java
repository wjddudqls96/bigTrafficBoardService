package com.example.boardserver.service.impl;

import com.example.boardserver.dto.CategoryDto;
import com.example.boardserver.mapper.CategoryMapper;
import com.example.boardserver.service.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void register(String accountId, CategoryDto categoryDto) {
        if(accountId != null) {
            categoryMapper.register(categoryDto);
        }
        else {
            log.error("register ERROR! {}", categoryDto);
            throw new RuntimeException("register ERROR! 게시글 카테고리 등록 메서드를 확인해주세요" + categoryDto);
        }
    }

    @Override
    public void update(CategoryDto categoryDto) {
        if(categoryDto != null) {
            categoryMapper.updateCategory(categoryDto);
        }
        else {
            log.error("update ERROR! {}", categoryDto);
            throw new RuntimeException("update ERROR! 게시글 카테고리 수정 메서드를 확인해주세요" + categoryDto);
        }
    }

    @Override
    public void delete(int categoryId) {
        if(categoryId != 0) {
            categoryMapper.deleteCategory(categoryId);
        }
        else {
            log.error("delete ERROR! {}", categoryId);
            throw new RuntimeException("delete ERROR! 게시글 카테고리 삭제 메서드를 확인해주세요" + categoryId);
        }
    }
}
