package com.school.commentmanager.controller;

import com.school.commentmanager.model.Category;
import com.school.commentmanager.model.dto.CategoryDto;
import com.school.commentmanager.repo.CategoryRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

  private final CategoryRepository categoryRepository;

  public CategoryController(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @GetMapping
  private List<Category> getCategories() {
    Iterable<Category> iterableCategories = categoryRepository.findAll();
    List<Category> result = new ArrayList<>();
    iterableCategories.iterator().forEachRemaining(result::add);
    return result;
  }

  @PutMapping("/admin")
  private void editPrice(@RequestBody List<CategoryDto> categoryDtoList) {
    categoryRepository.deleteAll();
    categoryDtoList.forEach(categoryDto -> {
      Category category = new Category();
      category.setName(categoryDto.getName());
      category.setPrice(categoryDto.getPrice());
      categoryRepository.save(category);
    });
  }
}
