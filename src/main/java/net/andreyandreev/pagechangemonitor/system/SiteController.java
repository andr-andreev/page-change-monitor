package net.andreyandreev.pagechangemonitor.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.View;

@Controller
class SiteController {
  @Autowired private RssFeedView view;

  @GetMapping("/")
  public String welcome() {
    return "redirect:/pages";
  }

  @GetMapping("/rss")
  public View getFeed() {
    return view;
  }
}
