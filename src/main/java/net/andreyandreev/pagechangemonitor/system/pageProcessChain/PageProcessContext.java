package net.andreyandreev.pagechangemonitor.system.pageProcessChain;

import net.andreyandreev.pagechangemonitor.page.Page;
import org.apache.commons.chain.impl.ContextBase;

import java.util.List;

public class PageProcessContext extends ContextBase {
  Page page;

  String originalContent;

  String content;

  List<String> lines;

  Boolean fetchPageContent;
}
