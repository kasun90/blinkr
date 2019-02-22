package com.blink.atag;

import com.blink.atag.tags.OrderedList;
import com.blink.atag.tags.builders.*;
import com.blink.core.log.Logger;
import com.blink.core.service.Context;
import com.blink.shared.article.ATag;
import com.blink.shared.common.Article;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

public class ATagEngineImpl implements AtagEngine {

    private Logger logger;
    private SimpleATagBuilder currentBuilder;
    private ParagraphBuilder paragraphBuilder = new ParagraphBuilder();
    private NoteBuilder noteBuilder = new NoteBuilder();
    private GenericListBuilder<com.blink.atag.tags.List> listBuilder = new GenericListBuilder<>();
    private GenericListBuilder<OrderedList> orderedListBuilder = new GenericListBuilder<>();
    private CodeBuilder codeBuilder = new CodeBuilder();
    private TerminalBuilder terminalBuilder = new TerminalBuilder();

    public ATagEngineImpl(Context context) {
        this.logger = context.getLoggerFactory().getLogger("ATAG");
        currentBuilder =  this.paragraphBuilder;
    }

    @Override
    public Article process(String raw, Article article) throws Exception {
        logger.info("Raw article process request received [key={}]", article.getKey());

        if (raw == null) {
            logger.error("Raw string cannot be null. Nothing to process");
            return article;
        }

        List<ATag> aTags = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(new StringReader(raw))) {
            String line;

            while ((line = br.readLine()) != null) {
                processLine(line, aTags);
            }
        }

        breakTag(aTags);
        article.setTags(aTags);
        return article;
    }

    private void processLine(String line, List<ATag> aTags) {
        if (line.startsWith("#"))
            processSingleLineATag(new HeaderBuilder(), line, aTags);
        else if (line.isEmpty())
            breakTag(aTags);
        else if (line.startsWith("!!"))
            startOrEndTag(aTags, noteBuilder);
        else if (line.startsWith("!("))
            processSingleLineATag(new ImageBuilder(), line, aTags);
        else if (line.startsWith("**"))
            processList(line);
        else if (line.startsWith("*@"))
            processOrderedList(line);
        else if (line.startsWith("```"))
            startOrEndTag(aTags, terminalBuilder);
        else if (line.startsWith("``"))
            startOrEndTag(aTags, codeBuilder);
        else if (line.startsWith("[gist]"))
            processSingleLineATag(new GistBuilder(), line, aTags);
        else
            processGeneralLine(line);
    }

    private void processSingleLineATag(SimpleATagBuilder builder, String line, List<ATag> aTags) {
        builder.addLine(line);
        aTags.add(builder.build());
    }

    private void processList(String line) {
        if (!listBuilder.isBuilding())
            listBuilder.initNew(new com.blink.atag.tags.List());
        listBuilder.addLine(line);
    }

    private void processOrderedList(String line) {
        if (!orderedListBuilder.isBuilding())
            orderedListBuilder.initNew(new OrderedList());
        orderedListBuilder.addLine(line);
    }

    private void breakTag(List<ATag> aTags) {
        if (listBuilder.isBuilding()) {
            aTags.add(listBuilder.build());
            listBuilder.reset();
        } else if (orderedListBuilder.isBuilding()) {
            aTags.add(orderedListBuilder.build());
            orderedListBuilder.reset();
        } else if (paragraphBuilder.isBuilding()) {
            aTags.add(paragraphBuilder.build());
            paragraphBuilder.reset();
        }
    }

    private void startOrEndTag(List<ATag> aTags, SimpleATagBuilder builder) {
        if (builder.isBuilding()){
            aTags.add(builder.build());
            builder.reset();
            currentBuilder = paragraphBuilder;
        } else
            currentBuilder = builder;
    }

    private void processGeneralLine(String line) {
        currentBuilder.addLine(line);
    }
}
