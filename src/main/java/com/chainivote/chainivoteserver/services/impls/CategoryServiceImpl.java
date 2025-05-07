package com.chainivote.chainivoteserver.services.impls;


import com.chainivote.chainivoteserver.dtos.response.CategoryResponseDTO;
import com.chainivote.chainivoteserver.repositories.CategoryRepository;
import com.chainivote.chainivoteserver.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<CategoryResponseDTO> getAllCategory(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(category -> new CategoryResponseDTO(
                        category.getId(),
                        category.getName(),
                        category.getDescription(),
                        category.getUrlImage(),
                        category.getChainId()
                ));
    }

}
