package de.hbrs.aspgen.testhelper;

import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;

public class DummyLogService implements LogService {

    @Override
    public void log(final int arg0, final String arg1) {

    }

    @Override
    public void log(final int arg0, final String arg1, final Throwable arg2) {

    }

    @Override
    public void log(@SuppressWarnings("rawtypes") final ServiceReference arg0, final int arg1, final String arg2) {

    }

    @Override
    public void log(@SuppressWarnings("rawtypes") final ServiceReference arg0, final int arg1, final String arg2, final Throwable arg3) {

    }
}