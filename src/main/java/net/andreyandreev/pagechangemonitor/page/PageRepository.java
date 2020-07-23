package net.andreyandreev.pagechangemonitor.page;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PageRepository extends CrudRepository<Page, Integer> {
  List<Page> findByIsActiveTrue();
}
