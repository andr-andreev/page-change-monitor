package net.andreyandreev.pagechangemonitor.system.pageProcessChain;

import net.andreyandreev.pagechangemonitor.page.Page;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.lang3.StringUtils;

public class FindSubstring implements Command {
  @Override
  public boolean execute(Context context) throws Exception {
    Page page = (Page) context.get("page");
    String text = (String) context.get("content");

    if (!page.hasFilter()) {
      return false;
    }

    text = StringUtils.substringBetween(text, page.getFilterFrom(), page.getFilterTo());

    context.put("content", StringUtils.defaultString(text));

    return false;
  }
}
