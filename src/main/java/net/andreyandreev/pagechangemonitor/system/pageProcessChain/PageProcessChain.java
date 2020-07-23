package net.andreyandreev.pagechangemonitor.system.pageProcessChain;

import org.apache.commons.chain.impl.ChainBase;

public class PageProcessChain extends ChainBase {

  public PageProcessChain() {
    super();
    addCommand(new FetchPageContent());
    addCommand(new ConvertHtmlToText());
    addCommand(new FindSubstring());
    addCommand(new ConvertTextToLines());
  }
}
