package com.blink.atag;

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
    private ParagraphBuilder paragraphBuilder = new ParagraphBuilder();
    private NoteBuilder noteBuilder = new NoteBuilder();

    public ATagEngineImpl(Context context) {
        this.logger = context.getLoggerFactory().getLogger("ATAG");
        this.paragraphBuilder.setExpect(true);
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

        breakPara(aTags);
        article.setTags(aTags);
        return article;
    }

    private void processLine(String line, List<ATag> aTags) {
        if (line.startsWith("#"))
            processSingleLineATag(new HeaderBuilder(), line, aTags);
        else if (line.isEmpty())
            breakPara(aTags);
        else if (line.startsWith("!!"))
            startOrEndNote(aTags);
        else if (line.startsWith("!("))
            processSingleLineATag(new ImageBuilder(), line, aTags);
        else
            processGeneralLine(line);
    }

    private void processSingleLineATag(SimpleATagBuilder builder, String line, List<ATag> aTags) {
        builder.addLine(line);
        aTags.add(builder.build());
    }

    private void breakPara(List<ATag> aTags) {
        if (paragraphBuilder.isBuilding()) {
            aTags.add(paragraphBuilder.build());
            paragraphBuilder.reset();
        }
    }

    private void startOrEndNote(List<ATag> aTags) {
        if (noteBuilder.isBuilding()) {
            aTags.add(noteBuilder.build());
            noteBuilder.reset();
            noteBuilder.setExpect(false);
        } else
            noteBuilder.setExpect(true);
    }

    private void processGeneralLine(String line) {
        if (noteBuilder.expect()) {
            noteBuilder.addLine(line);
        } else {
            paragraphBuilder.addLine(line);
        }
    }
}
