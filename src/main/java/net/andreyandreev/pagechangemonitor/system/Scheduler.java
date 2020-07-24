package net.andreyandreev.pagechangemonitor.system;

import net.andreyandreev.pagechangemonitor.change.Change;
import net.andreyandreev.pagechangemonitor.change.ChangeRepository;
import net.andreyandreev.pagechangemonitor.page.Page;
import net.andreyandreev.pagechangemonitor.page.PageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Scheduler {

	private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);

	@Autowired
	private PageChangeService pageChangeService;

	@Autowired
	private PageRepository pageRepository;

	@Autowired
	private ChangeRepository changeRepository;

	@Scheduled(cron = "${app.cron-expression}")
	public void processPages() {
		List<Page> pages = pageRepository.findByIsActiveTrue();

		pages.forEach(page -> {
			logger.info("Processing", page.getUrl());

			try {
				DiffResultDto diffResultDto = pageChangeService.getDiff(page);

				if (diffResultDto.diff.length() == 0) {
					logger.info("No changes have been found.", diffResultDto.diff);

					return;
				}

				logger.info("Changes have been found.", diffResultDto.diff);

				page.setLastContent(diffResultDto.originalContent);
				pageRepository.save(page);

				Change change = new Change();
				change.setPage(page);
				change.setDiff(diffResultDto.diff);
				changeRepository.save(change);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

}
