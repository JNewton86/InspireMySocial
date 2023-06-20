package org.exception;

public class InsufficientCreditsException extends RuntimeException {


    private static final long serialVersionUID = -4124885419726656348L;

    /**
     * Exception with no message or cause.
     */
    public InsufficientCreditsException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     *
     * @param message A descriptive message for this exception.
     */
    public InsufficientCreditsException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     *
     * @param cause The original throwable resulting in this exception.
     */
    public InsufficientCreditsException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     *
     * @param message A descriptive message for this exception.
     * @param cause   The original throwable resulting in this exception.
     */
    public InsufficientCreditsException(String message, Throwable cause) {
        super(message, cause);
    }
}

