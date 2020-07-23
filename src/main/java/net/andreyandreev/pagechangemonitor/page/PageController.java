package net.andreyandreev.pagechangemonitor.page;

import net.andreyandreev.pagechangemonitor.category.Category;
import net.andreyandreev.pagechangemonitor.category.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
class PageController {

  private final PageRepository pages;

  private final CategoryRepository categories;

  public PageController(PageRepository pageRepository, CategoryRepository categoryRepository) {
    this.pages = pageRepository;
    this.categories = categoryRepository;
  }

  @ModelAttribute("categories")
  public Map<Integer, String> populateCategories() {
    return this.categories.findCategories().stream()
        .collect(Collectors.toMap(Category::getId, Category::getName));
  }

  @GetMapping("/pages")
  public String showPageList(Map<String, Object> model) {
    model.put("pages", this.pages.findAll());

    return "pages/index";
  }

  @GetMapping("/pages/new")
  public String initCreationForm(Map<String, Object> model) {
    Page page = new Page();
    page.setIsActive(true);

    model.put("page", page);

    return "pages/create";
  }

  @PostMapping("/pages/new")
  public String processCreationForm(@Valid Page page, BindingResult result) {
    if (result.hasErrors()) {
      return "pages/create";
    }

    page.setLastContent("");
    this.pages.save(page);

    return "redirect:/pages/" + page.getId();
  }

  @GetMapping("/pages/{pageId}/edit")
  public String initUpdatePageForm(@PathVariable("pageId") int pageId, Model model) {
    Page page =
        this.pages
            .findById(pageId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    model.addAttribute("page", page);

    return "pages/update";
  }

  @PostMapping("/pages/{pageId}/edit")
  public String processUpdatePageForm(
      @Valid Page page, BindingResult result, @PathVariable("pageId") int pageId, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("page", page);

      return "pages/update";
    }

    page.setId(pageId);
    page.setLastContent("");
    this.pages.save(page);

    return "redirect:/pages/{pageId}";
  }

  @GetMapping("/pages/{pageId}")
  public ModelAndView showPage(@PathVariable("pageId") int pageId) {
    ModelAndView mav = new ModelAndView("pages/view");

    Page page =
        this.pages
            .findById(pageId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    mav.addObject(page);

    return mav;
  }

  @DeleteMapping("/pages/{pageId}")
  public String processDeleteCategory(@PathVariable("pageId") int pageId) {
    this.pages.deleteById(pageId);

    return "redirect:/pages";
  }
}
