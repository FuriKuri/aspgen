package de.hbrs;

public privileged aspect Person_Osgi {
    @Generated(id = {newid1}, name = "Service Init", data = "LogService:service;")
    before(BundleContext context, Person activator) : execution(public void start(BundleContext)) && args(context) && this(activator) {
        activator.service = (LogService) context.getService(context.getServiceReference(LogService.class.getName()));
    }
    
    @Generated(id = {newid1}, name = "Service Init2", data = "LogService:service;")
    before(BundleContext context, Person activator) : execution(public void start(BundleContext)) && args(context) && this(activator) {
        activator.service = (LogService) context.getService(context.getServiceReference(LogService.class.getName()));
    }
}