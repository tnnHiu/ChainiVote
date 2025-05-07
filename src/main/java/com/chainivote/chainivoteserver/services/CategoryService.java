package com.chainivote.chainivoteserver.services;

import com.chainivote.chainivoteserver.dtos.response.CategoryResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Page<CategoryResponseDTO> getAllCategory(Pageable pageable);

}
