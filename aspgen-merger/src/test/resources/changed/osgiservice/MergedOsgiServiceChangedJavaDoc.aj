package de.hbrs;

public privileged aspect Person_OsgiService {
    /**
     * Changed
     */
    @Generated(id = 2, name = "Service Init", data = "NameService:name;")
    before(BundleContext context, Person activator) : execution(public void start(BundleContext)) && args(context) && this(activator) {
        activator.name = (NameService) context.getService(context.getServiceReference(NameService.class.getName()));
    }
}