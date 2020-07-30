package net.andreyandreev.pagechangemonitor.system.pageProcessChain;

import net.andreyandreev.pagechangemonitor.page.Page;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
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

		WebClient webClient = WebClient.builder()
				.exchangeStrategies(ExchangeStrategies.builder()
						.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024)).build())
				.baseUrl(parsedUrl.getHost()).defaultHeader("User-Agent", "Mozilla/5.0").build();

		String content = webClient.get().uri(parsedUrl.toURI()).retrieve().bodyToMono(String.class).block();

		context.put("originalContent", content);
		context.put("content", content);

		return false;
	}

}
