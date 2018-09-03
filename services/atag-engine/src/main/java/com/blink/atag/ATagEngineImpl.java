package com.blink.atag;

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

    public ATagEngineImpl(Context context) {
        this.logger = context.getLoggerFactory().getLogger("ATAG");
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

        article.setTags(aTags);
        return article;
    }

    private void processLine(String line, List<ATag> aTags) {
        if (line.startsWith("#"))
            processHeader(line, aTags);
    }

    private void processHeader(String line, List<ATag> aTags) {
        int size = 0;
        String value = null;


    }
}
