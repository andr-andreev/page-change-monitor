package net.andreyandreev.pagechangemonitor.system;

import com.github.difflib.DiffUtils;
import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.patch.Patch;
import net.andreyandreev.pagechangemonitor.page.Page;
import net.andreyandreev.pagechangemonitor.system.pageProcessChain.PageProcessChain;
import net.andreyandreev.pagechangemonitor.system.pageProcessChain.PageProcessContext;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PageChangeService {

  public DiffResultDto getDiff(Page page) throws Exception {
    Command pageProcessChain = new PageProcessChain();

    Context contextOriginal = new PageProcessContext();
    contextOriginal.put("page", page);
    contextOriginal.put("content", page.getLastContent());
    contextOriginal.put("fetchPageContent", false);

    Context contextCurrent = new PageProcessContext();
    contextCurrent.put("page", page);
    contextCurrent.put("content", page.getLastContent());
    contextCurrent.put("fetchPageContent", true);

    pageProcessChain.execute(contextOriginal);
    pageProcessChain.execute(contextCurrent);

    List<String> originalTextLines = (List<String>) contextOriginal.get("lines");
    List<String> newTextLines = (List<String>) contextCurrent.get("lines");

    Patch<String> patch = DiffUtils.diff(originalTextLines, newTextLines);

    List<String> diff =
        UnifiedDiffUtils.generateUnifiedDiff(
            "original-file.txt", "new-file.txt", originalTextLines, patch, 0);

    List<String> diffNoFileNames = diff.size() > 2 ? diff.subList(2, diff.size()) : diff;

    DiffResultDto resultDto = new DiffResultDto();
    resultDto.originalContent = (String) contextCurrent.get("originalContent");
    resultDto.content = (String) contextCurrent.get("content");
    resultDto.diff = String.join("\n", diffNoFileNames);

    return resultDto;
  }
}
