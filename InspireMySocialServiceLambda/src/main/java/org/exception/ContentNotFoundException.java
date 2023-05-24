package org.exception;
/**
 * Exception to throw when a given content ID is not found in the database.
 */
public class ContentNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -912326717789387971L;

    /**
     * Exception with no message or cause.
     */
    public ContentNotFoundException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     *
     * @param message A descriptive message for this exception.
     */
    public ContentNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     *
     * @param cause The original throwable resulting in this exception.
     */
    public ContentNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     *
     * @param message A descriptive message for this exception.
     * @param cause   The original throwable resulting in this exception.
     */
    public ContentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

