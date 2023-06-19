package org.exception;

public class CreateImageForContentException extends RuntimeException {


    private static final long serialVersionUID = -824431201210089334L;

    /**
     * Exception with no message or cause.
     */
    public CreateImageForContentException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     *
     * @param message A descriptive message for this exception.
     */
    public CreateImageForContentException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     *
     * @param cause The original throwable resulting in this exception.
     */
    public CreateImageForContentException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     *
     * @param message A descriptive message for this exception.
     * @param cause   The original throwable resulting in this exception.
     */
    public CreateImageForContentException(String message, Throwable cause) {
        super(message, cause);
    }
}

