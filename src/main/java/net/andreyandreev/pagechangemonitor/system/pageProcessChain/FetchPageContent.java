package net.andreyandreev.pagechangemonitor.system.pageProcessChain;

import net.andreyandreev.pagechangemonitor.page.Page;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URL;

public class FetchPageContent implements Command {

	@Override
	public boolean execute(Context context) throws Exception {
		Page page = (Page) context.get("page");
		Boolean fetchPageContent = (Boolean) context.get("fetchPageContent");

		if (!fetchPageContent) {
			return false;
		}

		URL parsedUrl = new URL(page.getUrl());

		String content = WebClient.create(parsedUrl.getHost()).get().uri(parsedUrl.toURI())
				.header("User-Agent", "Mozilla/5.0").retrieve().bodyToMono(String.class).block();

		context.put("originalContent", content);
		context.put("content", content);

		return false;
	}

}
