package com.blink.core.log;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

import java.net.URI;

public class CustomConfigurationFactory extends ConfigurationFactory {


    protected String[] getSupportedTypes() {
        return new String[]{"*"};
    }

    private static Configuration createConfiguration(final String name, ConfigurationBuilder<BuiltConfiguration> builder) {
        builder.setConfigurationName(name);
        builder.setStatusLevel(Level.INFO);
        //builder.add(builder.newFilter("ThresholdFilter", Filter.Result.ACCEPT, Filter.Result.DENY).addAttribute("level", Level.INFO));

        LayoutComponentBuilder layoutComponentBuilder = builder.newLayout("PatternLayout").
                addAttribute("pattern", "%d [%t] %c{1} %-5level: %msg%n%throwable");


        AppenderComponentBuilder console = builder.newAppender("Stdout", "CONSOLE").
                addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT);
        console.add(layoutComponentBuilder);
        console.add(builder.newFilter("MarkerFilter", Filter.Result.DENY,
                Filter.Result.NEUTRAL).addAttribute("marker", "FLOW"));
        builder.add(console);

        AppenderComponentBuilder file = builder.newAppender("log", "File");
        file.addAttribute("fileName", "log/blink.log");
        file.add(layoutComponentBuilder);
        builder.add(file);


        builder.add(builder.newLogger("com", Level.INFO).
                add(builder.newAppenderRef("Stdout")).
                add(builder.newAppenderRef("log")).
                addAttribute("additivity", false));

        builder.add(builder.newLogger("io.vertx", Level.OFF).addAttribute("additivity", false));

        builder.add(builder.newLogger("io.netty", Level.OFF).addAttribute("additivity", false));

        builder.add(builder.newRootLogger(Level.INFO).add(builder.newAppenderRef("Stdout")).add(builder.newAppenderRef("log")));
        return builder.build();
    }

    @Override
    public Configuration getConfiguration(final LoggerContext loggerContext, final ConfigurationSource source) {
        return getConfiguration(loggerContext, source.toString(), null);
    }

    @Override
    public Configuration getConfiguration(final LoggerContext loggerContext, final String name, final URI configLocation) {
        ConfigurationBuilder<BuiltConfiguration> builder = newConfigurationBuilder();
        return createConfiguration(name, builder);
    }
}
