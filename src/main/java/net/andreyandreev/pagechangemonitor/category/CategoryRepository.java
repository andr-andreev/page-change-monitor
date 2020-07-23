package net.andreyandreev.pagechangemonitor.category;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

  @Query("SELECT category FROM Category category ORDER BY category.name")
  List<Category> findCategories();
}
