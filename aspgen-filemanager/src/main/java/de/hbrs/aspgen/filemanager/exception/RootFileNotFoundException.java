package de.hbrs.aspgen.filemanager.exception;

public class RootFileNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -1121361583494495541L;

    public RootFileNotFoundException(final String message) {
        super(message);
    }
}
