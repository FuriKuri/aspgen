package de.hbrs.aspgen.api.exception;

public class FileNotExistException extends RuntimeException {
    private static final long serialVersionUID = 2195528149064254448L;

    public FileNotExistException(final String msg, final Throwable t) {
        super(msg, t);
    }
}
