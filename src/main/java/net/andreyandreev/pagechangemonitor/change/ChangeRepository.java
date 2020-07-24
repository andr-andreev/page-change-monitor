package net.andreyandreev.pagechangemonitor.change;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ChangeRepository extends PagingAndSortingRepository<Change, Integer> {

	@Override
	@Query("SELECT change FROM Change change " +
			"LEFT JOIN change.page " +
			"WHERE change.page.isActive = true")
	@NotNull
	Page<Change> findAll(Pageable pageable);

}
