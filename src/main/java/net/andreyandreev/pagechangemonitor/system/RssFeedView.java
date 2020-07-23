package net.andreyandreev.pagechangemonitor.system;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Content;
import com.rometools.rome.feed.rss.Guid;
import com.rometools.rome.feed.rss.Item;
import net.andreyandreev.pagechangemonitor.change.ChangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RssFeedView extends AbstractRssFeedView {

  @Value("${spring.application.name}")
  private String appName;

  @Value("${app.rss-item-count}")
  private Integer rssItemCount;

  @Autowired private ChangeRepository changes;

  @Override
  protected void buildFeedMetadata(
      Map<String, Object> model, Channel feed, HttpServletRequest request) {
    feed.setTitle(appName);
    feed.setDescription(appName);
    feed.setLink(ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString());
    feed.setPubDate(new Date());
    feed.setUri("https://example.com/");
  }

  @Override
  protected List<Item> buildFeedItems(
      Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
    Pageable pageable = PageRequest.of(0, rssItemCount, Sort.by("id").descending());

    return changes.findAll(pageable).stream()
        .map(
            change -> {
              Item item = new Item();

              Guid guid = new Guid();
              guid.setValue("change/" + change.getId());
              guid.setPermaLink(true);

              Content content = new Content();
              content.setType(Content.TEXT);
              content.setValue(change.getTextContent());

              item.setGuid(guid);
              item.setTitle(change.getExtendedTitle());
              item.setLink(change.getPage().getUrl());
              item.setPubDate(
                  Date.from(change.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant()));
              item.setContent(content);

              return item;
            })
        .collect(Collectors.toList());
  }
}
