package com.chainivote.chainivoteserver.controllers;


import com.chainivote.chainivoteserver.dtos.response.CategoryResponseDTO;
import com.chainivote.chainivoteserver.dtos.response.PollResponseDTO;
import com.chainivote.chainivoteserver.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/get-all-category")
    public Page<CategoryResponseDTO> getAllCategory(
            @PageableDefault(size = 5) Pageable pageable) {
        return categoryService.getAllCategory(pageable);
    }

}
