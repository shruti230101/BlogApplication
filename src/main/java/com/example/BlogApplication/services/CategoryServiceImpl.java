package com.example.BlogApplication.services;

import com.example.BlogApplication.config.MessageConfig;
import com.example.BlogApplication.dtos.CategoryDTO;
import com.example.BlogApplication.entities.Category;
import com.example.BlogApplication.exceptions.ResourceNotFoundException;
import com.example.BlogApplication.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MessageConfig messageConfig;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = this.modelMapper.map(categoryDTO, Category.class);
        Category newCategory = this.categoryRepository.save(category);
        return this.modelMapper.map(newCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(this.messageConfig.RESOURCE_NOT_FOUND_MESSAGE_FORMAT,
                        this.messageConfig.RESOURCE_CATEGORY, this.messageConfig.RESOURCE_FIELD, categoryId));
        category.setCategoryTitle(categoryDTO.getCategoryTitle());
        category.setCategoryDescription(categoryDTO.getCategoryDescription());
        Category updatedCategory = this.categoryRepository.save(category);
        return this.modelMapper.map(updatedCategory, CategoryDTO.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(this.messageConfig.RESOURCE_NOT_FOUND_MESSAGE_FORMAT,
                        this.messageConfig.RESOURCE_CATEGORY, this.messageConfig.RESOURCE_FIELD, categoryId));
        this.categoryRepository.delete(category);
    }

    @Override
    public CategoryDTO getCategory(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(this.messageConfig.RESOURCE_NOT_FOUND_MESSAGE_FORMAT,
                        this.messageConfig.RESOURCE_CATEGORY, this.messageConfig.RESOURCE_FIELD, categoryId));
        return this.modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = this.categoryRepository.findAll();
        return categories.stream()
                        .map(category -> this.modelMapper.map(category, CategoryDTO.class))
                        .toList();
    }
}
