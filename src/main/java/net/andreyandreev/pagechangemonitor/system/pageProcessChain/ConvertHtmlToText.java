package net.andreyandreev.pagechangemonitor.system.pageProcessChain;

import net.andreyandreev.pagechangemonitor.system.HtmlToMarkdown;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

public class ConvertHtmlToText implements Command {

	@Override
	public boolean execute(Context context) throws Exception {
		String html = (String) context.get("content");

		String markdown = HtmlToMarkdown.convert(html);

		context.put("content", markdown);

		return false;
	}

}
