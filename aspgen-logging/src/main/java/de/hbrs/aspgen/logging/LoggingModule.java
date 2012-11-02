package de.hbrs.aspgen.logging;

import org.osgi.service.log.LogListener;

import com.google.inject.AbstractModule;

public class LoggingModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(LogListener.class).to(LogFileWriter.class);
    }

}
