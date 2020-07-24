package net.andreyandreev.pagechangemonitor.system.pageProcessChain;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertTextToLines implements Command {

	@Override
	public boolean execute(Context context) throws Exception {
		String text = (String) context.get("content");

		List<String> lines = text.lines().collect(Collectors.toList());

		context.put("lines", lines);

		return false;
	}

}
