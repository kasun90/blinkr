package com.blink.atag;

import com.blink.shared.common.Article;

public interface AtagEngine {
    Article process(String raw, Article article) throws Exception;
}
