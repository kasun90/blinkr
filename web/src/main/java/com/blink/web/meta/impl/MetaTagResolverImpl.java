package com.blink.web.meta.impl;

import com.blink.core.exception.BlinkRuntimeException;
import com.blink.core.service.Context;
import com.blink.web.meta.*;
import com.blink.web.meta.handlers.CommonTagHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MetaTagResolverImpl implements MetaTagResolver {

    private Map<String, MetaPath> paths = new ConcurrentHashMap<>();
    private Context context;
    private String indexFileContent;
    private TagHandler commonHandler;

    public MetaTagResolverImpl(Context context) throws Exception {
        this.context = context;
        this.commonHandler = new CommonTagHandler();
        init();
    }

    @Override
    public void init() throws Exception {
        File indexFile = new File(context.getConfiguration().getValue("clientRoot") + "/index.html");
        if (!indexFile.exists())
            throw new BlinkRuntimeException("Client index file not found");
        try (BufferedReader br = new BufferedReader(new FileReader(indexFile))) {
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null)
                builder.append(line);
            indexFileContent = builder.toString();
        }
    }

    @Override
    public MetaPath register(String path) {
        return paths.computeIfAbsent(path, MetaPathImpl::new);
    }

    @Override
    public String on(String path, String absoluteURI) throws Exception {
        String currentIndexFile = indexFileContent;
        String[] originSegments = MetaPath.doSegment(path);
        TagHandler handler = null;

        final PathParams params = new PathParamsImpl();

        Map<MetaTag, String> tags = new HashMap<>();
        tags.put(MetaTag.URL, absoluteURI);

        for (MetaPath metaPath : paths.values()) {
            params.clear();
            if (metaPath.getSegmentCount() != originSegments.length)
                continue;
            else {
                for (int i = 0; i < metaPath.getSegments().length; i++) {
                    String segment = metaPath.getSegments()[i];
                    if (segment.startsWith(":"))
                        params.put(segment.substring(1), originSegments[i]);
                    else if (segment.equals(originSegments[i]))
                        handler = metaPath.getHandler();
                }
            }

            if (handler != null)
                break;
        }

        if (handler == null)
            handler = new CommonTagHandler();

        tags = handler.handle(params, context, tags);
        return replaceTags(currentIndexFile, tags);
    }

    private String replaceTags(String currentIndexFile, Map<MetaTag, String> tags) {
        for (Map.Entry<MetaTag, String> entry : tags.entrySet()) {
            switch (entry.getKey()) {
                case URL:
                    currentIndexFile = currentIndexFile.replace("_OG_URL_", entry.getValue());
                    break;
                case IMAGE:
                    currentIndexFile = currentIndexFile.replace("_OG_IMAGE_", entry.getValue());
                    break;
                case DESCRIPTION:
                    currentIndexFile = currentIndexFile.replace("_OG_DESC_", entry.getValue());
                    break;
                case TITLE:
                    currentIndexFile = currentIndexFile.replace("_OG_TITLE_", entry.getValue());
                    break;
                default:
                    break;
            }
        }
        return currentIndexFile;
    }
}
