package net.falappa.wwind.layers;

/**
 * Indicates problems in identifying SurfaceShapes by id.
 *
 * @author Alessandro Falappa
 */
public class NoSuchShapeException extends Exception {

    /**
     * Default constructor.
     */
    public NoSuchShapeException() {
    }

    /**
     * Initializing constructor.
     *
     * @param message exception message
     */
    public NoSuchShapeException(String message) {
        super(message);
    }

    /**
     * Initializing constructor.
     *
     * @param message exception message
     * @param cause wrapped exception
     */
    public NoSuchShapeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Initializing constructor.
     *
     * @param cause wrapped exception
     */
    public NoSuchShapeException(Throwable cause) {
        super(cause);
    }

}
