package de.hbrs.aspgen.history;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import de.hbrs.aspgen.api.dir.ObservableProjectWriter;
import de.hbrs.aspgen.history.storage.HistoryStorageQueue;

public class Activator implements BundleActivator {

    @Override
    public void start(final BundleContext context) throws Exception {
        final Hashtable<String, Object> props = new Hashtable<>();
        props.put("osgi.command.scope", "aspgen");
        props.put("osgi.command.function", new String[] { "history" });

        final ObservableProjectWriter observableProjectWriter = (ObservableProjectWriter)
                context.getService(context.getServiceReference(ObservableProjectWriter.class.getName()));
        final HistoryStorageQueue historyStorage = new HistoryStorageQueue();
        observableProjectWriter.register(historyStorage);

        context.registerService(
                HistoryCommand.class.getName(), new HistoryCommand(historyStorage), props);
//        context.registerService(Command.class.getName(),
//            new MyStartCommandImpl(m_context), null);
    }

    @Override
    public void stop(final BundleContext context) throws Exception {
        // TODO Auto-generated method stub

    }

}
