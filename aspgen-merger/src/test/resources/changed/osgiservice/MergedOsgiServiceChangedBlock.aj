package de.hbrs;

public privileged aspect Person_OsgiService {
    /**
     * JavaDoc
     */
    @Generated(id = 2, name = "Service Init", data = "NameService:name;")
    before(BundleContext context, Person activator) : execution(public void start(BundleContext)) && args(context) && this(activator) {
        activator.service = (LogService) context.getService(context.getServiceReference("MyService"));
    }
}