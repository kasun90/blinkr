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

public final class ATagEngineImpl implements AtagEngine {

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

        final List<ATag> aTags = new LinkedList<>();
        final BehaviorController controller = new ATagBehaviorController(new BuilderRegistry(), new BehaviorConfiguration(
                new BehaviorRegistry()
        ));

        try (BufferedReader br = new BufferedReader(new StringReader(raw))) {
            String line;

            while ((line = br.readLine()) != null) {
                Behavior behavior = controller.getBehavior(line);
                behavior.action(line);
                behavior.output().ifPresent(aTags::add);
            }
        }

        controller.conclude().ifPresent(aTags::add);
        article.setTags(aTags);
        return article;
    }
}
