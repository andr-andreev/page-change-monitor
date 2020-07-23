package net.andreyandreev.pagechangemonitor.system;

import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.html2md.converter.*;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class HtmlToMarkdown {

  public static String convert(String html) {
    MutableDataSet options =
        new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singletonList(HtmlConverterTextExtension.create()));

    String markdown = FlexmarkHtmlConverter.builder(options).build().convert(html);

    return markdown;
  }

  static class CustomLinkResolver implements HtmlLinkResolver {

    public CustomLinkResolver(HtmlNodeConverterContext context) {}

    @Override
    public ResolvedLink resolveLink(
        Node node, HtmlNodeConverterContext context, ResolvedLink link) {
      // convert all links from http:// to https://
      if (link.getUrl().startsWith("http:")) {
        return link.withUrl("https:" + link.getUrl().substring("http:".length()));
      }
      return link;
    }

    static class Factory implements HtmlLinkResolverFactory {

      @Nullable
      @Override
      public Set<Class<?>> getAfterDependents() {
        return null;
      }

      @Nullable
      @Override
      public Set<Class<?>> getBeforeDependents() {
        return null;
      }

      @Override
      public boolean affectsGlobalScope() {
        return false;
      }

      @Override
      public HtmlLinkResolver apply(HtmlNodeConverterContext context) {
        return new CustomLinkResolver(context);
      }
    }
  }

  static class HtmlConverterTextExtension implements FlexmarkHtmlConverter.HtmlConverterExtension {

    public static HtmlConverterTextExtension create() {
      return new HtmlConverterTextExtension();
    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {}

    @Override
    public void extend(FlexmarkHtmlConverter.@NotNull Builder builder) {
      builder.linkResolverFactory(new CustomLinkResolver.Factory());
      builder.htmlNodeRendererFactory(new CustomHtmlNodeConverter.Factory());
    }
  }

  static class CustomHtmlNodeConverter implements HtmlNodeRenderer {

    public CustomHtmlNodeConverter(DataHolder options) {}

    @Override
    public Set<HtmlNodeRendererHandler<?>> getHtmlNodeRendererHandlers() {
      return new HashSet<>(
          Collections.singletonList(
              new HtmlNodeRendererHandler<>("kbd", Element.class, this::processKbd)));
    }

    private void processKbd(
        Element node, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
      out.append("<<");
      context.renderChildren(node, false, null);
      out.append(">>");
    }

    static class Factory implements HtmlNodeRendererFactory {

      @Override
      public HtmlNodeRenderer apply(DataHolder options) {
        return new CustomHtmlNodeConverter(options);
      }
    }
  }
}
