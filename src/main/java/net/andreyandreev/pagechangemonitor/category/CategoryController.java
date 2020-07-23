package net.andreyandreev.pagechangemonitor.category;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Map;

@Controller
class CategoryController {
  private final CategoryRepository categories;

  public CategoryController(CategoryRepository categoryRepository) {
    this.categories = categoryRepository;
  }

  @GetMapping("/categories")
  public String showCategoryList(Map<String, Object> model) {
    model.put("categories", this.categories.findAll());

    return "categories/index";
  }

  @GetMapping("/categories/new")
  public String initCreationForm(Map<String, Object> model) {
    Category category = new Category();
    model.put("category", category);

    return "categories/create";
  }

  @PostMapping("/categories/new")
  public String processCreationForm(@Valid Category category, BindingResult result) {
    if (result.hasErrors()) {
      return "categories/create";
    }

    this.categories.save(category);

    return "redirect:/categories";
  }

  @GetMapping("/categories/{categoryId}/edit")
  public String initUpdateCategoryForm(@PathVariable("categoryId") int categoryId, Model model) {
    Category category =
        this.categories
            .findById(categoryId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    model.addAttribute("category", category);

    return "categories/update";
  }

  @PostMapping("/categories/{categoryId}/edit")
  public String processUpdateCategoryForm(
      @Valid Category category, BindingResult result, @PathVariable("categoryId") int categoryId) {
    if (result.hasErrors()) {
      return "categories/update";
    }

    category.setId(categoryId);
    this.categories.save(category);

    return "redirect:/categories";
  }

  @DeleteMapping("/categories/{categoryId}")
  public String processDeleteCategory(@PathVariable("categoryId") int categoryId) {
    this.categories.deleteById(categoryId);

    return "redirect:/categories";
  }
}
